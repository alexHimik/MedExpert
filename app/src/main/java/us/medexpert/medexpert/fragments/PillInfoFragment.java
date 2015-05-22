package us.medexpert.medexpert.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devspark.robototextview.widget.RobotoTextView;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.db.entity.Product;
import us.medexpert.medexpert.db.tables.CategoryTableHelper;
import us.medexpert.medexpert.db.tables.ProductHelper;
import us.medexpert.medexpert.view.SlidingTabLayout;


public class PillInfoFragment extends BaseFragment {
    public static final String TAG = "PillInfoFragment";
    public static final int PILLINFO_ID = 8;
    public static String PRODUCT_NAME_KEY = "product_name";
    public static String PRODUCT_ID_KEY = "product_id";
    public static String CATEGORY_ID_KEY = "category_ID";

    private View parent;
    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;
    private String[] title = new String[] {"INFO","PACKAGE"};
    private int product_id;
    private String product_name;
    private int category_id;
    private String category_name;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customBar = super.getActionBarCustomView(inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        context = getActivity().getBaseContext();
        Bundle data = getArguments();
        product_name = data.getString(product_name);
        product_id = data.getInt(PRODUCT_ID_KEY);
        category_id = data.getInt(CATEGORY_ID_KEY);
        CategoryTableHelper ch = new CategoryTableHelper();
        category_name = ch.getCategoryName(context, category_id);
//        product_name = "QWERT YUI";
//        product_id = 472;
//        category_name = "Name Category";

        parent = inflater.inflate(R.layout.pill_info, container, false);

        return parent;
    }

    @Override
    public void initActionBarItems() {
        rightBarItem.setVisibility(View.VISIBLE);
        rightBarItem.setOnClickListener(barClickListener);
        leftItemTouch.setOnClickListener(barClickListener);
        leftbarItem.setBackgroundResource(R.drawable.med_ic_white_hamburger);
        ((RobotoTextView)centerBatItem).setText(product_name);
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
                case 0:
                    view = setInfo(container);
                    break;
                case 1:
                    view = setPackage(container);
            }
            container.addView(view);
            return view;
        }

         @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public View setInfo(ViewGroup container){
        ProductHelper ph = new ProductHelper();
        Product pr = ph.getProduct(context, product_id);
        View v = getActivity().getLayoutInflater().inflate(R.layout.pill_info_tab, container, false);
        RobotoTextView name = (RobotoTextView) v.findViewById(R.id.name);
        String st = pr.getName();
        String nam = st;
        int i1 = st.indexOf("(");
        if (i1>0) {
            st = st.substring(0,i1).trim();
        }
        ((RobotoTextView) v.findViewById(R.id.name)).setText(st);
        ((RobotoTextView) v.findViewById(R.id.gener)).setText(category_name);
        ((RobotoTextView) v.findViewById(R.id.name1)).setText(nam);
        ((RobotoTextView) v.findViewById(R.id.categor)).setText(category_name);
        ((RobotoTextView) v.findViewById(R.id.descr)).setText(pr.getDescr());
        ((RobotoTextView) v.findViewById(R.id.price)).setText(pr.getPrice());
        if (pr.getLiked()>0) ((ImageView) v.findViewById(R.id.iv2)).
                setImageDrawable(getResources().getDrawable(R.drawable.med_ic_pink_heart_checked));
        return v;
    }

    public View setPackage(ViewGroup container){
        View v = getActivity().getLayoutInflater().inflate(R.layout.pill_info_package,container, false);

        return v;
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
