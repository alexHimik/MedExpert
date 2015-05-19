package us.medexpert.medexpert.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import us.medexpert.medexpert.R;

import com.devspark.robototextview.widget.RobotoTextView;

import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.tools.FragmentFactory;

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
        formHome();
        return parent;
    }

    public void formHome(){
        formCatalog();
        formFavorites();
        formRecently();
    }

    private void formCatalog(){
        ((RelativeLayout) parent.findViewById(R.id.bl_catalog)).setOnClickListener(onClick);
        ((LinearLayout) parent.findViewById(R.id.bl_catalog_one)).setOnClickListener(onClickCatal);
        ((LinearLayout) parent.findViewById(R.id.bl_catalog_two)).setOnClickListener(onClickCatal);
        ((LinearLayout) parent.findViewById(R.id.bl_catalog_three)).setOnClickListener(onClickCatal);



    }

    private void formFavorites(){
        ((RelativeLayout) parent.findViewById(R.id.bl_favorites)).setOnClickListener(onClick);
        if (true) {
            LinearLayout ll = (LinearLayout)parent.findViewById(R.id.block_favorites);
            ll.setGravity(Gravity.CENTER_VERTICAL);
            View v = ((LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.favor_item_img, null);
            ll.addView(v);
        }
        else {
            LinearLayout block_favor = (LinearLayout) parent.findViewById(R.id.block_favorites);
            View view = ((LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.home_item_favor, null);
        }
    }

    private void formRecently(){
        ((RelativeLayout) parent.findViewById(R.id.bl_recently)).setOnClickListener(onClick);
        if (true) {
            LinearLayout ll = (LinearLayout)parent.findViewById(R.id.block_recently);
            ll.setGravity(Gravity.CENTER_VERTICAL);
            View v = ((LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.recently_item_img, null);
            ll.addView(v);
        }
        else {
            LinearLayout block_recent = (LinearLayout) parent.findViewById(R.id.block_recently);
//            View view = ((LayoutInflater) getActivity().getSystemService(
//                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.home_item_v, null);
        }
    }

    OnClickListener onClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bl_catalog:
                    ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_CATALOG, null);
                    break;
                case R.id.bl_favorites:
                    ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_FAVORITES, null);
                    break;
                case R.id.bl_recently:
                    ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_RECENTLY, null);
                    break;
            }
        }
    };

    OnClickListener onClickCatal = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(FragmentFactory.data, v.getTag().toString());
            ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_CATEGORY,
                    bundle);

 /*           if (st.equals(getResources().getString(R.string.catal_one))) {
                Bundle bundle = new Bundle();
                RobotoTextView rv = (RobotoTextView)parent.findViewById(R.id.one);
                bundle.putSerializable(FragmentFactory.data, rv.getText().toString());
                ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_CATEGORY,
                        bundle);
                return;
            }
            if (st.equals(getResources().getString(R.string.catal_two))) {
                Bundle bundle = new Bundle();
                RobotoTextView rv = (RobotoTextView)parent.findViewById(R.id.two);
                bundle.putSerializable(FragmentFactory.data, rv.getText().toString());
                ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_CATEGORY,
                        bundle);
                return;
            }
            if (st.equals(getResources().getString(R.string.catal_three))) {
                Bundle bundle = new Bundle();
                RobotoTextView rv = (RobotoTextView)parent.findViewById(R.id.three);
                bundle.putSerializable(FragmentFactory.data, rv.getText().toString());
                ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_CATEGORY,
                        bundle);
                return;
            }*/
        }
    };


    @Override
    public void initActionBarItems() {
        rightBarItem.setVisibility(View.VISIBLE);
        rightBarItem.setOnClickListener(barClickListener);
        leftItemTouch.setOnClickListener(barClickListener);
        leftbarItem.setBackgroundResource(R.drawable.med_ic_white_hamburger);
    }

    private OnClickListener barClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).onClick(v);
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
