package us.medexpert.medexpert.fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import us.medexpert.medexpert.R;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.List;

import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.db.entity.Category;
import us.medexpert.medexpert.db.entity.Product;
import us.medexpert.medexpert.db.tables.CategoryTableHelper;
import us.medexpert.medexpert.db.tables.ProductHelper;
import us.medexpert.medexpert.tools.FragmentFactory;

public class HomeFragment extends BaseFragment {
    public static final String TAG = "HomeFragment";
    public static final int FRAGMENT_ID = 0;

    private View parent;
    private Context context;
    private List<Category> listCatal;
    private List<Product> listProd;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customBar = super.getActionBarCustomView(inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        context = getActivity().getBaseContext();
        parent = inflater.inflate(R.layout.home, container, false);
        formHome();
        return parent;
    }

    public void formHome(){
        formCatalog();
        formFavorites();
        formRecently();
    }

    private void formCatalog(){
        View v;
        parent.findViewById(R.id.bl_catalog).setOnClickListener(onClick);
        LinearLayout ll = (LinearLayout) parent.findViewById(R.id.block_catalog);

        CategoryTableHelper ch = new CategoryTableHelper();
        listCatal = ch.getPopularCategories(context);
        Category cat;
        for (int i = 0; i<3; i++){
            cat = listCatal.get(i);
            v = getActivity().getLayoutInflater().inflate(R.layout.home_item_category, null);
            LinearLayout bl = (LinearLayout) v.findViewById(R.id.bl_catalog_one);
            bl.setOnClickListener(onClickCatal);
            bl.setTag(""+i);
            ((RobotoTextView) v.findViewById(R.id.one)).setText(cat.getCatName());
            ll.addView(v);
        }
    }

    private void formFavorites(){
        parent.findViewById(R.id.bl_favorites).setOnClickListener(onClick);
        LinearLayout ll = (LinearLayout)parent.findViewById(R.id.block_favorites);
        ProductHelper ph = new ProductHelper();
        listProd = ph.getProductFavor(context);
        View v;
        if (listProd.size() == 0) {
            ll.setGravity(Gravity.CENTER_VERTICAL);
            v = ((LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.favor_item_img, null);
            ll.addView(v);
        }
        else {
            Product pr;
            int ik = 3;
            if (ik > listProd.size()) ik = listProd.size();
            CategoryTableHelper ch = new CategoryTableHelper();

            for (int i = 0; i<ik; i++){
                pr = listProd.get(i);
                v = getActivity().getLayoutInflater().inflate(R.layout.home_item_favor, null);
                RelativeLayout bl = (RelativeLayout) v.findViewById(R.id.bl_favorits_one);
                bl.setOnClickListener(onClickFavor);
                bl.setTag(""+i);
                String st = pr.getName();
                int i1 = st.indexOf("(");
                if (i1>0) {
                    st = st.substring(0,i1).trim();
                }

                ((RobotoTextView) v.findViewById(R.id.name)).setText(st);

                ((RobotoTextView) v.findViewById(R.id.gener)).setText(ch.getCategoryName(context, pr.getId_category()));
                ((RobotoTextView) v.findViewById(R.id.price)).setText(pr.getPrice());
                ((ImageView) v.findViewById(R.id.iv2)).setImageDrawable(getResources().
                        getDrawable(R.drawable.med_ic_pink_heart_checked));

                ll.addView(v);
            }
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
            Category cat = listCatal.get(Integer.valueOf(v.getTag().toString()));
            Bundle data = new Bundle();
            data.putString(CategoryDrugListFragment.CATEGORY_NAME_KEY, cat.getCatName());
            data.putInt(CategoryDrugListFragment.CATEGORY_ID_KEY, cat.getId());
            ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_CATEGORY, data);
        }
    };

    OnClickListener onClickFavor = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Product pr = listProd.get(Integer.valueOf(v.getTag().toString()));
            Bundle data = new Bundle();
            data.putString(PillInfoFragment.PRODUCT_NAME_KEY, pr.getName());
            data.putInt(PillInfoFragment.PRODUCT_ID_KEY, pr.getId());
            data.putInt(PillInfoFragment.CATEGORY_ID_KEY, pr.getId_category());
            ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_PILLINFO, data);
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
        return FragmentFactory.ID_HOME;
    }
}
