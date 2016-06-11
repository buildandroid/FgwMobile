package fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fgwoa.fgwmobile.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GwqpTabFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GwqpTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GwqpTabFragment extends Fragment implements GwqpTabButtonFragment.OnTabSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String CATEGORY = "CATEGORY";

    private int mCategory;
    private OnTabSelectedListener mListener;

    public GwqpTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment GongWenChaXunTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GwqpTabFragment newInstance(String param1) {
        GwqpTabFragment fragment = new GwqpTabFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = getArguments().getInt(CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_gwqp_tab, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle argument = getArguments();
        if(argument != null){
            mCategory = argument.getInt(CATEGORY);
        }
        if (context instanceof OnTabSelectedListener) {
            mListener = (OnTabSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
        void onTabSelected(int index);
    }

//    private void initTabButtonFragment(){
//        FragmentManager fragmentManager = getFragmentManager();
//        List<Fragment> fragments = fragmentManager.getFragments();
//        for(Fragment fragment:fragments){
//            if(fragment instanceof GwqpTabButtonFragment){
//                ((GwqpTabButtonFragment)fragment).setOnTabSelectedListener(this);
//            }
//        }
//    }

    @Override
    public void onTabSelected(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        final List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment frg: fragments){
            if(frg instanceof GwqpTabButtonFragment){
                ((GwqpTabButtonFragment)frg).setSelected(frg.equals(fragment));
            }
        }
        if(mListener != null){
            mListener.onTabSelected(fragments.indexOf(fragment));
        }
    }

}
