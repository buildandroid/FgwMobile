package activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.fgwoa.fgwmobile.R;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.io.File;

import Utils.ResourceDownloader;
import holder.Category1Holder;
import holder.Category2Holder;
import holder.Category3Holder;

/**
 * Created by selim_tekinarslan on 10.10.2014.
 */
public class GGXXPdfViewActivity extends AppCompatActivity {
    private static final String TAG = "PdfViewActivity";
    private static final String SAMPLE_FILE = "foxit.pdf";
    private static final String FILE_PATH = "filepath";
    private static final String SEARCH_TEXT = "text";
    private PdfFragment fragment;
    private static Context context;
    private File pdf;
    private FrameLayout categoryFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        context = GGXXPdfViewActivity.this;
//        Intent intent = getIntent();
//        String url = intent.getStringExtra("url");
        String url = getString(R.string.SERVER_URL).replace("gws/", "") + "demo-files/1-1-1.pdf";
        Log.d("公共信息pdf", url);
        downloadPDF(url);
        initActionBar();
    }

    private void downloadPDF(String url) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_custom_progress);
        dialog.show();


        new ResourceDownloader(context, new ResourceDownloader.DownloadCallback() {
            @Override
            public void onProgressUpdated(long curr, long total) {
            }

            @Override
            public void onCompleted(boolean success, File file) {
                if (success) {
                    dialog.dismiss();
                    pdf = file;
                    openPdfWithFragment(file);

                }
            }
        }).start(url);
    }

    public void openPdfWithFragment(File file) {
        fragment = new PdfFragment();
        Bundle args = new Bundle();
        args.putString(FILE_PATH, file.getAbsolutePath());
        fragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pdf != null) {
            pdf.delete();
        }
    }


    private void initCategory() {
        TreeNode root = TreeNode.root();
        TreeNode category1 = new TreeNode(new Category1Holder.Category1("第一部分 概况篇")).setViewHolder(new Category1Holder(this));
        root.addChild(category1);
        TreeNode category1_2 = new TreeNode(new Category2Holder.Category2("二、京津冀概况")).setViewHolder(new Category2Holder(this));
        root.addChild(category1_2);

        TreeNode category1_1 = new TreeNode(new Category2Holder.Category2("一、 北京概况")).setViewHolder(new Category2Holder(this));
        category1.addChild(category1_1);

        TreeNode category1_1_1 = new TreeNode(new Category3Holder.Category3("1、 城市建设", 3)).setViewHolder(new Category3Holder(this));
        category1_1.addChild(category1_1_1);
        TreeNode category1_1_2 = new TreeNode(new Category3Holder.Category3("2、 人口", 5)).setViewHolder(new Category3Holder(this));
        category1_1.addChild(category1_1_2);
        TreeNode category1_1_3 = new TreeNode(new Category3Holder.Category3("3、人民生活、就业和社会保障", 9)).setViewHolder(new Category3Holder(this));
        category1_1.addChild(category1_1_3);
        AndroidTreeView treeView = new AndroidTreeView(this, root);
        treeView.setDefaultAnimation(false);
        treeView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        treeView.setDefaultNodeClickListener(new TreeNode.TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {
                Log.d("TreeNode", node.toString());
                if (value instanceof Category3Holder.Category3) {
                    Category3Holder.Category3 categoryLevel3 = (Category3Holder.Category3) value;
                    fragment.gotoPage(categoryLevel3.page);
                }
            }
        });
        categoryFrameLayout.addView(treeView.getView(),0);
        treeView.setUseAutoToggle(false);
        treeView.expandAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ggxx, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_mulu){
            if(categoryFrameLayout == null){
                categoryFrameLayout = new FrameLayout(this);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.gravity = Gravity.RIGHT;
//                layoutParams.setMargins(,10,0,0);
//                categoryFrameLayout.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                categoryFrameLayout.setBackgroundResource(R.drawable.bg_pishiqingkuang_list);
                categoryFrameLayout.setVisibility(View.INVISIBLE);

                Button button = new Button(context);
                button.setText("收起");
                button.setClickable(true);
                button.setFocusable(true);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("目录", "收起");
                        categoryFrameLayout.setVisibility(View.INVISIBLE);
                    }
                });
                FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams1.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                categoryFrameLayout.addView(button, layoutParams1);

                ((FrameLayout) findViewById(R.id.content_frame)).addView(categoryFrameLayout, layoutParams);
                initCategory();
            }
            if(categoryFrameLayout.getVisibility() != View.VISIBLE){
                categoryFrameLayout.setVisibility(View.VISIBLE);
            }else{
                categoryFrameLayout.setVisibility(View.INVISIBLE);
            }
        }else if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initActionBar(){
       ActionBar actionBar = getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("公共信息");
    }
}
