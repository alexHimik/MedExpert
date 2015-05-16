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
//        EventBus.getDefault().register(this);
        View parent = inflater.inflate(R.layout.left_side_bar, container);
        tv = (TextView) parent.findViewById(R.id.tv);
        sh = (LinearLayout) parent.findViewById(R.id.side_home);
//        sh.setClickable(true);
        sh.setOnClickListener(onSideBar);

        sa = (LinearLayout) parent.findViewById(R.id.side_about);
//        sa.setClickable(true);
        sa.setOnClickListener(onSideBar);

        sc = (LinearLayout) parent.findViewById(R.id.side_catalog);
//        sc.setClickable(true);
        sc.setOnClickListener(onSideBar);

        sf = (LinearLayout) parent.findViewById(R.id.side_favorites);
//        sf.setClickable(true);
        sf.setOnClickListener(onSideBar);

        sv = (LinearLayout) parent.findViewById(R.id.side_viewed);
//        sv.setClickable(true);
        sv.setOnClickListener(onSideBar);

        ss = (LinearLayout) parent.findViewById(R.id.side_setting);
//        ss.setClickable(true);
        ss.setOnClickListener(onSideBar);

        return parent;
    }

    OnClickListener onSideBar = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.side_about:
                    ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_ABOUT, null);
//                    tv.setText("ABOUT");
                    break;
                case R.id.side_catalog:
                    tv.setText("CATALOG");
                    break;
                case R.id.side_home:
                    tv.setText("HOME");
                    break;
                case R.id.side_favorites:
                    tv.setText("FAVOR");
                    break;
                case R.id.side_viewed:
                    tv.setText("VIEWED");
                    break;
                case R.id.side_setting:
                    tv.setText("SETTING");
                    break;
            }
        }
    };
}
