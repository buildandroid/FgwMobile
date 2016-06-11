package fragment;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class SignFragment extends DialogFragment {
    private PennableLayout mPennable;
    private SharedPreferences sharedPreferences;
    private String token;
    private String barcode;
    private OnSignListener mListener;

    public static final String BARCODE = "BARCODE";

    public SignFragment() {
        // Required empty public constructor
    }


    public static SignFragment newInstance(String param1) {
        SignFragment fragment = new SignFragment();
        Bundle args = new Bundle();
        args.putString(BARCODE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign, container, false);

        mPennable = (PennableLayout) rootView.findViewById(R.id.ink);

        mPennable.setStrokeColor(Color.BLACK);
        mPennable.setStrokeWidth(9.0f);
        mPennable.setSideKeyEnable(true);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferences.getString("token", "");
//        barcode = getIntent().getStringExtra("barcode");
        barcode = getArguments().getString(BARCODE);

        rootView.findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
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
                                    dismiss();
                                    if(mListener != null){
                                        mListener.onSign(response.body().fileId);
                                    }

//                                    finish();
//                                    setResult(RESULT_OK);
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

        return rootView;
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
            File file = new File(getActivity().getCacheDir(), pngName);
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnSignListener){
            mListener = (OnSignListener)activity;
        }
    }

    public interface OnSignListener{
        void onSign(String fileId);
    }
}
