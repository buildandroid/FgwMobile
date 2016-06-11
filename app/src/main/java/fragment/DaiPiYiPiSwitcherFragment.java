package fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fgwoa.fgwmobile.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DaiPiYiPiSwitcherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DaiPiYiPiSwitcherFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private int mSwitch = 0;
    private Button mDaipiButton;
    private Button mYipiButton;

    public DaiPiYiPiSwitcherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dai_pi_yi_pi_switcher, container, false);
        mDaipiButton = (Button)rootView.findViewById(R.id.gwqp_daipi);
        mYipiButton = (Button)rootView.findViewById(R.id.gwqp_yipi);
        initDaipiButton();
        initYipiButton();
        return rootView;
    }

    private void initDaipiButton(){
        mDaipiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwitch = 0;
                updateUI();
            }
        });
    }

    private void initYipiButton(){
        mYipiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwitch = 1;
                updateUI();
            }
        });
    }

    private void updateUI(){
        if(mSwitch == 0){
            mDaipiButton.setSelected(true);
            mDaipiButton.setClickable(false);
            mYipiButton.setSelected(false);
            mYipiButton.setClickable(true);
        }else{
            mDaipiButton.setSelected(false);
            mDaipiButton.setClickable(true);
            mYipiButton.setSelected(true);
            mYipiButton.setClickable(false);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
