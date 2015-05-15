package us.medexpert.medexpert.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import us.medexpert.medexpert.R;

import com.devspark.robototextview.widget.RobotoTextView;

import us.medexpert.medexpert.activity.MainActivity;

public class HomeFragment extends BaseFragment {
    public static final String TAG = "HomeFragment";
    public static final int FRAGMENT_ID = 0;

    private View parent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customBar = super.getActionBarCustomView(inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);

        parent = inflater.inflate(R.layout.home, container, false);
//        rtw = (RobotoTextView) inflater.inflate(R.layout.newsrobot, container, false);
//        actv = getActivity();
//        ll = (LinearLayout) parent.findViewById(R.id.lineLayout_1);

        return parent;
    }



    @Override
    public void initActionBarItems() {
        rightBarItem.setVisibility(View.INVISIBLE);
        leftItemTouch.setOnClickListener(barClickListener);
        leftbarItem.setBackgroundResource(R.drawable.med_ic_white_hamburger);
    }

    private View.OnClickListener barClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).onBackPressed();
        }
    };

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }
}
