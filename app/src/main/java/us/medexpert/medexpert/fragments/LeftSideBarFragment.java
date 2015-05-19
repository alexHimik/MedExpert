package us.medexpert.medexpert.fragments;

//import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.tools.FragmentFactory;

public class LeftSideBarFragment extends Fragment {

    TextView tv;
    LinearLayout sh, sa, sc, ss, sv, sf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.left_side_bar, container);

        sh = (LinearLayout) parent.findViewById(R.id.side_home);
        sh.setOnClickListener(onSideBar);

        sa = (LinearLayout) parent.findViewById(R.id.side_about);
        sa.setOnClickListener(onSideBar);

        sc = (LinearLayout) parent.findViewById(R.id.side_catalog);
        sc.setOnClickListener(onSideBar);

        sf = (LinearLayout) parent.findViewById(R.id.side_favorites);
        sf.setOnClickListener(onSideBar);

        sv = (LinearLayout) parent.findViewById(R.id.side_viewed);
        sv.setOnClickListener(onSideBar);

        ss = (LinearLayout) parent.findViewById(R.id.side_setting);
        ss.setOnClickListener(onSideBar);

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
//                    tv.setText("HOME");
                    break;
                case R.id.side_favorites:
//                    tv.setText("FAVOR");
                    break;
                case R.id.side_viewed:
//                    tv.setText("VIEWED");
                    break;
                case R.id.side_setting:
//                    tv.setText("SETTING");
                    break;
            }
        }
    };
}
