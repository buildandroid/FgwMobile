package activity;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artifex.mupdfdemo.MuPDFCore;
import com.artifex.mupdfdemo.MuPDFPageAdapter;
import com.artifex.mupdfdemo.MuPDFReaderView;
import com.artifex.mupdfdemo.SearchTask;
import com.artifex.mupdfdemo.SearchTaskResult;
import com.example.fgwoa.fgwmobile.R;

public class PdfFragment extends Fragment {
    private RelativeLayout mainLayout;
    private MuPDFCore core;
    private MuPDFReaderView mDocView;
    private Context mContext;
    private String mFilePath;
    Bundle args = new Bundle();
    private static final String TAG = "PdfFragment";
    private SearchTask mSearchTask;

    public PdfFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        args = this.getArguments();
        mFilePath = args.getString("filepath");
        final View rootView = inflater.inflate(R.layout.fragment_pdf, container, false);
        mainLayout = (RelativeLayout) rootView.findViewById(R.id.pdflayout);

        core = openFile(Uri.decode(mFilePath));
//        OutlineItem[] outlineItems = core.getOutline();

        if (core != null && core.countPages() == 0) {
            core = null;
        }
        if (core == null || core.countPages() == 0 || core.countPages() == -1) {
            Log.e(TAG, "Document Not Opening");
        }
        if (core != null) {
            mDocView = new MuPDFReaderView(getActivity()) {
                @Override
                protected void onMoveToChild(int i) {
                    if (core == null)
                        return;
                    super.onMoveToChild(i);
                    updatePageNumber(i);
//                    ActionBar actionBar = getActivity().getActionBar();
//                    actionBar.setTitle(String.valueOf(i+1) + "/" + getAdapter().getCount());
//                    Toast.makeText(getContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
                }

//                @Override
//                public void onScaleEnd(ScaleGestureDetector detector) {
//                    super.onScaleEnd(detector);
//                    if(getScale() > 1){
//                        rootView.findViewById(R.id.up).setVisibility(VISIBLE);
//                        rootView.findViewById(R.id.down).setVisibility(VISIBLE);
//                    }else{
//                        rootView.findViewById(R.id.up).setVisibility(INVISIBLE);
//                        rootView.findViewById(R.id.down).setVisibility(INVISIBLE);
//                    }
//                }

            };


            mDocView.setAdapter(new MuPDFPageAdapter(mContext, core));

//            ActionBar actionBar = getActivity().getActionBar();
//            actionBar.setTitle(String.valueOf(1) + "/" + mDocView.getAdapter().getCount());

            mainLayout.addView(mDocView, 0);
            initPageNumber(rootView);
            showSlidePageHint();
        }

        mSearchTask = new SearchTask(mContext, core) {

            @Override
            protected void onTextFound(SearchTaskResult result) {
                SearchTaskResult.set(result);
                mDocView.setDisplayedViewIndex(result.pageNumber);
                mDocView.resetupChildren();
            }
        };

        return rootView;
    }

    public void search(int direction, String text) {
        int displayPage = mDocView.getDisplayedViewIndex();
        SearchTaskResult r = SearchTaskResult.get();
        int searchPage = r != null ? r.pageNumber : -1;
        mSearchTask.go(text, direction, displayPage, searchPage);
    }


    private MuPDFCore openBuffer(byte[] buffer) {
        System.out.println("Trying to open byte buffer");
        try {
            core = new MuPDFCore(mContext, buffer);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
        return core;
    }

    private MuPDFCore openFile(String path) {
        int lastSlashPos = path.lastIndexOf('/');
        mFilePath = new String(lastSlashPos == -1
                ? path
                : path.substring(lastSlashPos + 1));
        try {
            core = new MuPDFCore(mContext, path);
            // New file: drop the old outline data
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
        return core;
    }

    public void onDestroy() {
        if (core != null)
            core.onDestroy();
        core = null;
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSearchTask != null)
            mSearchTask.stop();
    }

    private void updatePageNumber(int current){
        TextView pageNumberTextView = (TextView)getView().findViewById(R.id.pageNumber);
        pageNumberTextView.setText(String.format("%d / %d", current + 1,
                core.countPages()));
    }

    private void initPageNumber(View rootView){
        TextView pageNumberTextView = (TextView)rootView.findViewById(R.id.pageNumber);
        pageNumberTextView.setText(String.format("%d / %d", 1,
                core.countPages()));
    }

    private void showSlidePageHint(){
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_slide_page_hint);
        dialog.setCancelable(true);
        dialog.show();
    }

    public void gotoPage(int index){
        mDocView.setDisplayedViewIndex(index);
    }
}


