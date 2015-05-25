package us.medexpert.medexpert.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.tools.FragmentFactory;

public class LeftSideBarFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.left_side_bar, container);
        ((LinearLayout) parent.findViewById(R.id.side_home)).setOnClickListener(onSideBar);
        ((LinearLayout) parent.findViewById(R.id.side_about)).setOnClickListener(onSideBar);
        ((LinearLayout) parent.findViewById(R.id.side_catalog)).setOnClickListener(onSideBar);
        ((LinearLayout) parent.findViewById(R.id.side_favorites)).setOnClickListener(onSideBar);
        ((LinearLayout) parent.findViewById(R.id.side_viewed)).setOnClickListener(onSideBar);

        return parent;
    }

    OnClickListener onSideBar = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).togleLeftDrawer();
            ((MainActivity)getActivity()).clearBackStack();
            switch (v.getId()) {
                case R.id.side_about:
                    ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_ABOUT, null);
                    break;
                case R.id.side_catalog:
                    ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_CATALOG, null);
                    break;
                case R.id.side_home:
                    ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_HOME, null);
                    break;
                case R.id.side_favorites:
                    ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_FAVORITES, null);
                    break;
                case R.id.side_viewed:
                    ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_RECENTLY, null);
                    break;
            }
        }
    };
}
