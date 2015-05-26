package us.medexpert.medexpert.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.db.entity.*;
import us.medexpert.medexpert.db.entity.Package;
import us.medexpert.medexpert.db.tables.CategoryTableHelper;
import us.medexpert.medexpert.db.tables.ProductHelper;
import us.medexpert.medexpert.dialog.WarningDialog;
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
    private String[] title = new String[] {" INFO ","PACKAGE"};
    private int product_id;
    private String product_name;
    private int category_id;
    private String category_name;
    private Context context;
    public LayoutParams lp_W_W;
    private String currentDrugLink;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle data = getArguments();
        product_name = data.getString(PRODUCT_NAME_KEY);
        product_id = data.getInt(PRODUCT_ID_KEY);
        category_id = data.getInt(CATEGORY_ID_KEY);
        context = getActivity().getBaseContext();
        CategoryTableHelper ch = new CategoryTableHelper();
        category_name = ch.getCategoryName(getActivity(), category_id);
        View customBar = getActionBarCustomView(inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
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
        viewPager = (ViewPager)view.findViewById(R.id.screen_pager);
        viewPager.setAdapter(new SamplePagerAdapter());
        tabLayout = (SlidingTabLayout)view.findViewById(R.id.screen_tabs);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.med_blue));
        tabLayout.setViewPager(viewPager);

        ProductHelper productHelper = ProductHelper.getInstance(getActivity());
        productHelper.updateDrugViewedDate(product_id);
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
            switch (position) {
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
        ProductHelper ph = ProductHelper.getInstance(getActivity());
        Product pr = ph.getProduct(product_id);
        currentDrugLink = pr.getLinc();
        View v = getActivity().getLayoutInflater().inflate(R.layout.pill_info_tab, container, false);
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

        RobotoTextView btnFindSellers = (RobotoTextView) v.findViewById(R.id.btn_sellers);
        if (btnFindSellers != null) {
            btnFindSellers.setOnClickListener(btnFindSellersListener);
        }

        ImageView iv = (ImageView) v.findViewById(R.id.iv);
        BitmapPool pool = Glide.get(context).getBitmapPool();
        Glide.with(context).load(context.getResources().getString(R.string.app_site_base_url) + pr.getImg()).bitmapTransform(new CropCircleTransformation(pool)).into(iv);

       if (pr.getLiked() > 0) ((ImageView) v.findViewById(R.id.iv2)).
                setImageDrawable(getResources().getDrawable(R.drawable.med_ic_pink_heart_checked));
        return v;
    }

    public View setPackage(ViewGroup container){
        lp_W_W = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        View v_grid;
        View v = getActivity().getLayoutInflater().inflate(R.layout.pill_info_package,container, false);
        LinearLayout ll = (LinearLayout) v.findViewById(R.id.layout_package);
        ProductHelper ph = ProductHelper.getInstance(getActivity());
        List<Package> listPack = ph.getPackage(product_id);
        Package pc = new Package();
        String st, typePack,dosa, dosa1;
        dosa1="****";
        int im = listPack.size();
        int iline= 0;
        int pad32 = (int) getResources().getDimension(R.dimen.med_pad_32);
        int pad24 = (int) getResources().getDimension(R.dimen.med_pad_24);
        LinearLayout lineCount = null;
        View rect;
        RobotoTextView rv_count;
        RobotoTextView rv_type_pack;
        for (int i = 0; i<im; i++){
            pc = listPack.get(i);
            st = pc.getTitle();
            int j1 = st.indexOf(" ");
            int j2 = st.indexOf("x");
            typePack = st.substring(j1,j2).trim();
            dosa = st.substring(j2+1).trim();
            if (!dosa1.equals(dosa)) {
                RobotoTextView rv = newRobTV(lp_W_W, dosa, R.style.home_30, 0);
                rv.setPadding(0,pad32,0,pad24);
                ll.addView(rv);
                dosa1 = dosa;
                lineCount = newLayout(context, lp_W_W);
                ll.addView(lineCount);
                iline = 0;
            }
            if (iline>3){
                lineCount = newLayout(context, lp_W_W);
                ll.addView(lineCount);
                iline = 0;
            }
            rect = getActivity().getLayoutInflater().inflate(R.layout.pill_grid_item, null);
            rv_count = (RobotoTextView) rect.findViewById(R.id.count);
            rv_type_pack = (RobotoTextView) rect.findViewById(R.id.type_pack);
            rv_count.setText(""+pc.getCount());
            rv_type_pack.setText(typePack);
            lineCount.addView(rect);
            iline++;
        }

//        GridLayout gl = new GridLayout();
        return v;
    }

    public LinearLayout newLayout(Context context, LayoutParams lp){
        LinearLayout line1 = new LinearLayout(context);
        line1.setLayoutParams(lp);
        line1.setOrientation(LinearLayout.HORIZONTAL);
        return line1;
    }



    public RobotoTextView newRobTV(LayoutParams lp, String txt, int style, int bg){
        RobotoTextView tv = new RobotoTextView(getActivity());
        tv.setLayoutParams(lp);
        tv.setText(txt);
        if (style >0) tv.setTextAppearance(getActivity(), style);
        if (bg>0) tv.setBackgroundResource(bg);
        return tv;
    }

    private View.OnClickListener barClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).onClick(v);
        }
    };

    private View.OnClickListener btnFindSellersListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            WarningDialog warningDialog = new WarningDialog(getActivity(), currentDrugLink);
            warningDialog.show();
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
