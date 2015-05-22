package us.medexpert.medexpert.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import us.medexpert.medexpert.R;

//public abstract class BaseFragment extends Fragment implements BackStackDataDescriber {
public abstract class BaseFragment extends Fragment {

    protected View leftbarItem;
    protected View centerBatItem;
    protected View rightBarItem;
    protected View leftItemTouch;
    // Karelov - START
    protected View sortBarItem;
    // Karelov - END

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActionBarCustomView(inflater);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public View getActionBarCustomView(LayoutInflater inflater) {
        RelativeLayout barLayout = (RelativeLayout)inflater.inflate(R.layout.action_bar_header,
                null);
        leftbarItem = barLayout.findViewById(R.id.left_drawer_item);
        rightBarItem = barLayout.findViewById(R.id.right_drawer_item);
        centerBatItem = barLayout.findViewById(R.id.action_bar_title);
        leftItemTouch = barLayout.findViewById(R.id.left_drawer_item_touch);
        sortBarItem = barLayout.findViewById(R.id.sortBarItem);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.med_blue)));
        initActionBarItems();
        return barLayout;
    }

    public abstract void initActionBarItems();

    public abstract String getFragmentTag();
    public abstract int getFragmentId();
}
