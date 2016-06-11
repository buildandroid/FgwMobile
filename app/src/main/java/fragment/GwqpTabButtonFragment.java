package fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fgwoa.fgwmobile.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnTabSelectedListener} interface
 * to handle interaction events.
 * Use the {@link GwqpTabButtonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GwqpTabButtonFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ICON_RES_ID = "ICON_RES_ID";
    public static final String TXT_RES_ID = "TXT_RES_ID";
    public static final String IS_SELECTED = "IS_SELECTED";


    // TODO: Rename and change types of parameters
    private int mIconResId;
    private int mTxtResId;
    private boolean mIsSelected;

    private OnTabSelectedListener mListener;
    private FrameLayout mGwqpTabBtn;
    private ImageView mGwqpTabBtnIcon;
    private TextView mGwqpTabBtnTxt;
    private ImageView mGwqpBtnIndicator;

    public GwqpTabButtonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GongWenChaXunTabButtonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GwqpTabButtonFragment newInstance(String param1, String param2) {
        GwqpTabButtonFragment fragment = new GwqpTabButtonFragment();
//        Bundle args = new Bundle();
//        args.putString(ICON_RES_ID, param1);
//        args.putString(IS_SELECTED, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateArgument();
    }

    private void updateArgument() {
        if (getArguments() != null) {
            mIconResId = getArguments().getInt(ICON_RES_ID, R.drawable.all);
            mTxtResId = getArguments().getInt(TXT_RES_ID, R.string.prompt_all);
            mIsSelected = getArguments().getBoolean(IS_SELECTED, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_gwqp_tab_button, container, false);
        mGwqpTabBtn = (FrameLayout)rootView.findViewById(R.id.gwqp_button);
        initGwqpTabButton();
        mGwqpTabBtnIcon = (ImageView)rootView.findViewById(R.id.gwqp_btn_icon);
        mGwqpTabBtnIcon.setImageResource(mIconResId);
        mGwqpTabBtnTxt = (TextView)rootView.findViewById(R.id.gwqp_btn_txt);
        mGwqpTabBtnTxt.setText(mTxtResId);
        mGwqpBtnIndicator = (ImageView)rootView.findViewById(R.id.gwqp_btn_indicator);
        updateUIBySelectedState();
        return rootView;
    }

    private void initGwqpTabButton() {
        mGwqpTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mIsSelected = !mIsSelected;
//                updateUIBySelectedState();
                if (mListener != null) {
                    mListener.onTabSelected(GwqpTabButtonFragment.this);
                }
            }
        });
    }

    private void updateUIBySelectedState(){
        mGwqpTabBtn.setClickable(!mIsSelected);
        mGwqpTabBtn.setSelected(mIsSelected);
        mGwqpTabBtnIcon.setSelected(mIsSelected);
        mGwqpTabBtnTxt.setSelected(mIsSelected);
        mGwqpBtnIndicator.setSelected(mIsSelected);
        changeGwqpTabBtnIconColor();
//        changeGwqpTabBtnTxtColor();
        changeGwqpTabBtnIndicatorVisiblity();
    }

    private void changeGwqpTabBtnIconColor(){
        if(mGwqpTabBtnIcon.isSelected()){
            mGwqpTabBtnIcon.setColorFilter(getResources().getColor(R.color.colorPrimary));
        }else{
            mGwqpTabBtnIcon.setColorFilter(getResources().getColor(R.color.colorWhite));
        }
    }

    private void changeGwqpTabBtnTxtColor(){
        if(mGwqpTabBtnTxt.isSelected()){
            mGwqpTabBtnTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
        }else{
            mGwqpTabBtnTxt.setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    private void changeGwqpTabBtnIndicatorVisiblity(){
        if(mGwqpBtnIndicator.isSelected()){
            mGwqpBtnIndicator.setVisibility(View.VISIBLE);
        }else{
            mGwqpBtnIndicator.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment parentFragment = getParentFragment();
        if(parentFragment instanceof OnTabSelectedListener){
            mListener = (OnTabSelectedListener)parentFragment;
        }
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTabSelectedListener {
        // TODO: Update argument type and name
        void onTabSelected(Fragment fragment);
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GwrpTabButtonFragment);
        mIconResId = a.getResourceId(R.styleable.GwrpTabButtonFragment_gwrp_icon, R.drawable.all);
        mTxtResId = a.getResourceId(R.styleable.GwrpTabButtonFragment_gwrp_text, R.string.prompt_all);
        mIsSelected = a.getBoolean(R.styleable.GwrpTabButtonFragment_gwrp_selected, false);
        a.recycle();
    }

    public void setOnTabSelectedListener(OnTabSelectedListener listener){
        mListener = listener;
    }

    public void setSelected(boolean isSelected){
        mIsSelected = isSelected;
        updateUIBySelectedState();
    }
}
