package us.medexpert.medexpert.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devspark.robototextview.widget.RobotoTextView;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.view.SlidingTabLayout;


public class PillInfoFragment extends BaseFragment {
    public static final String TAG = "PillInfoFragment";
    public static final int PILLINFO_ID = 8;
    public static String PILL_KEY = "pill";

    private View parent;
    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;
    private String[] title = new String[] {"INFO","PACKAGE"};
    private String pill;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customBar = super.getActionBarCustomView(inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
//        Bundle data = getArguments();
//        pill = data.getString(PILL_KEY);
        parent = inflater.inflate(R.layout.pill_info, container, false);

        return parent;
    }

    @Override
    public void initActionBarItems() {
        rightBarItem.setVisibility(View.VISIBLE);
        rightBarItem.setOnClickListener(barClickListener);
        leftItemTouch.setOnClickListener(barClickListener);
        leftbarItem.setBackgroundResource(R.drawable.med_ic_white_hamburger);
        ((RobotoTextView)centerBatItem).setText(pill);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager)view.findViewById(R.id.screen_pager);
        viewPager.setAdapter(new SamplePagerAdapter());
        tabLayout = (SlidingTabLayout)view.findViewById(R.id.screen_tabs);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.med_blue));
//        tabLayout.setTextColor(0x000000,0x999999);
        tabLayout.setViewPager(viewPager);
    }

    // Adapter
    class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = null;
            // Inflate a new layout from our resources
            switch (position){
                case 0: view = getActivity().getLayoutInflater().inflate(R.layout.pill_info_tab,
                        container, false);
                    break;
                case 1: view = getActivity().getLayoutInflater().inflate(R.layout.pill_info_package,
                        container, false);
            }
            container.addView(view);
            return view;
        }

         @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    private View.OnClickListener barClickListener = new View.OnClickListener() {
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
        return PILLINFO_ID;
    }
}
