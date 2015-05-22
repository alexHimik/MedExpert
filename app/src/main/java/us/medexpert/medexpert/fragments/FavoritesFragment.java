package us.medexpert.medexpert.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.devspark.robototextview.widget.RobotoTextView;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;

public class FavoritesFragment extends BaseFragment {
    public static final String TAG = "FavoritesFragment";
    public static final int FRAGMENT_ID = 5;

    private View parent;
    private LinearLayout ll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customBar = super.getActionBarCustomView(inflater);
        ((MainActivity) getActivity()).getSupportActionBar().setCustomView(customBar);

        parent = inflater.inflate(R.layout.favorites, container, false);
        ll = (LinearLayout) parent.findViewById(R.id.ll);
        ll.setGravity(Gravity.CENTER_VERTICAL);
        View v = ((LayoutInflater) getActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.favor_item_img, null);
        ll.addView(v);
        return parent;
    }

    @Override
    public void initActionBarItems() {
        // Karelov - START
        sortBarItem.setVisibility(View.VISIBLE);
        sortBarItem.setOnClickListener(barClickListener);
        // Karelov - END
        rightBarItem.setVisibility(View.VISIBLE);
        rightBarItem.setOnClickListener(barClickListener);
        leftItemTouch.setOnClickListener(barClickListener);
        leftbarItem.setBackgroundResource(R.drawable.med_ic_white_hamburger);
        ((RobotoTextView) centerBatItem).setText(getString(R.string.favorites_string));
    }


    private View.OnClickListener barClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity) getActivity()).onClick(v);
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
