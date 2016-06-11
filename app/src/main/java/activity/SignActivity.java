package activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ebensz.eink.api.PennableLayout;
import com.example.fgwoa.fgwmobile.R;
import com.example.fgwoa.fgwmobile.RestApi;
import com.example.fgwoa.fgwmobile.RetrofitFactory;

import java.io.File;
import java.io.FileOutputStream;

import config.Result;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignActivity extends Activity {
    private PennableLayout mPennable;
    private SharedPreferences sharedPreferences;
    private String token;
    private String barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign);
        mPennable = (PennableLayout) findViewById(R.id.ink);

        mPennable.setStrokeColor(Color.BLACK);
        mPennable.setStrokeWidth(9.0f);
        mPennable.setSideKeyEnable(true);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = sharedPreferences.getString("token", "");
        barcode = getIntent().getStringExtra("barcode");

        findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bmp = exportBitmap();
                String file = uploadBitmap(bmp);

                if (file != null) {
                    String url = getString(R.string.SERVER_URL);
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), new File(file));

                    RequestBody t1 =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), token);
                    RequestBody t2 =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), barcode);

                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("signFile", new File(file).getName(), requestFile);
                    Call<Result> call = RetrofitFactory.getRetorfit().create(RestApi.class).gwSign(url + "gwSign",
                            t1, t2, body);
                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if (response.isSuccessful()) {
                                String ok = response.body().ret;
                                if (ok.equals("ok")) {
                                    finish();
                                    setResult(RESULT_OK);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private Bitmap exportBitmap() {
        Rect rect = mPennable.computeBounds(new Rect());
        Log.d("ComputeBounds", "bounds:" + rect.toString());
        return mPennable.export(rect, rect.width(), rect.height());
    }

    private String uploadBitmap(Bitmap bitmap) {
        try {
            Bitmap dest = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(dest);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bitmap, 0, 0, null);
            final String pngName = System.currentTimeMillis() + ".png";
            File file = new File(getCacheDir(), pngName);
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            dest.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // 调用系统像册查看图片
//        Uri uriFile = Uri.fromFile(file);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(uriFile, "image/*");
//        startActivity(intent);
    }
}
