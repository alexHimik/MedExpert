package us.medexpert.medexpert.fragments;



import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import us.medexpert.medexpert.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
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

    private View parent;
    private Context context;
    private List<Category> listCatal;
    private List<Product> listProd_F;
    private List<Product> listProd_R;
    ImageView[] star = new ImageView [5];
    //    private Fragment fragment;
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

        ProductHelper ph = ProductHelper.getInstance(getActivity());
        listProd_F = ph.getProductFavor();

        View v;
        if (listProd_F.size() == 0) {
            ll.setGravity(Gravity.CENTER_VERTICAL);
            v = ((LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.favor_item_img, null);
            ll.addView(v);
        }
        else {
            Product pr;
            int ik = 3;
            if (ik>listProd_F.size()) ik = listProd_F.size();
//            CategoryTableHelper ch = new CategoryTableHelper();
//            ImageView[] star = new ImageView [5];
            for (int i = 0; i<ik; i++){
                pr = listProd_F.get(i);
                v = getActivity().getLayoutInflater().inflate(R.layout.home_item_favor, null);
                RelativeLayout bl = (RelativeLayout) v.findViewById(R.id.bl_favorits_one);
                bl.setClickable(true);
                bl.setOnClickListener(onClickFavor);
                bl.setTag(""+i);
                String st = pr.getName();
                int i1 = st.indexOf("(");
                if (i1>0) {
                    st = st.substring(0,i1).trim();
                }
                ((RobotoTextView) v.findViewById(R.id.name)).setText(st);
                ((RobotoTextView) v.findViewById(R.id.gener)).setText(pr.getNameCat());
                ((RobotoTextView) v.findViewById(R.id.price)).setText(pr.getPrice());
                ((ImageView) v.findViewById(R.id.iv2)).setImageDrawable(getResources().
                        getDrawable(R.drawable.med_ic_pink_card_heart));
//                ((RatingBar)v.findViewById(R.id.drug_rating)).setRating(pr.getDrugRate());

                ImageView iv = (ImageView) v.findViewById(R.id.iv1);
                BitmapPool pool = Glide.get(context).getBitmapPool();
                Glide.with(context).load(context.getResources().getString(R.string.app_site_base_url) + pr.getImg()).bitmapTransform(new CropCircleTransformation(pool)).into(iv);
                setRating(v, star, Math.round(pr.getDrugRate()));

                ll.addView(v);
            }
        }
    }



    private void formRecently(){
        parent.findViewById(R.id.bl_recently).setOnClickListener(onClick);
        LinearLayout ll = (LinearLayout)parent.findViewById(R.id.block_recently);

        ProductHelper ph = ProductHelper.getInstance(getActivity());
        listProd_R = ph.getLastViewedDrugs();
        View v;
        if (listProd_R.size() == 0) {
            ll.setGravity(Gravity.CENTER_VERTICAL);
            v = ((LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.recently_item_img, null);
            ll.addView(v);
        }
        else {
            Product pr;
            int ik = listProd_R.size();
            if (ik>2) ik = 2;
//            CategoryTableHelper ch = new CategoryTableHelper();
            for (int i = 0; i<ik; i++){
                pr = listProd_R.get(i);
                v = getActivity().getLayoutInflater().inflate(R.layout.home_item_favor, null);
                RelativeLayout bl = (RelativeLayout) v.findViewById(R.id.bl_favorits_one);
                bl.setOnClickListener(onClickRecently);
                bl.setTag("" + i);
                String st = pr.getName();
                int i1 = st.indexOf("(");
                if (i1>0) {
                    st = st.substring(0,i1).trim();
                }
                ((RobotoTextView) v.findViewById(R.id.name)).setText(st);
                ((RobotoTextView) v.findViewById(R.id.gener)).setText(pr.getNameCat());
                ((RobotoTextView) v.findViewById(R.id.price)).setText(pr.getPrice());
                if (pr.getLiked()>0) ((ImageView) v.findViewById(R.id.iv2)).setImageDrawable(getResources().
                        getDrawable(R.drawable.med_ic_pink_card_heart));
                else ((ImageView) v.findViewById(R.id.iv2)).setImageDrawable(getResources().
                        getDrawable(R.drawable.med_ic_grey_heart_unchecked));
                ImageView iv = (ImageView) v.findViewById(R.id.iv1);
                BitmapPool pool = Glide.get(context).getBitmapPool();
                Glide.with(context).load(context.getResources().getString(R.string.app_site_base_url) + pr.getImg()).bitmapTransform(new CropCircleTransformation(pool)).into(iv);
                setRating(v, star,Math.round(pr.getDrugRate()));

                ll.addView(v);
            }
        }
    }

    private void setRating(View v, ImageView[] star, int rat){
        star[0] = (ImageView) v.findViewById(R.id.star1);
        star[1] = (ImageView) v.findViewById(R.id.star2);
        star[2] = (ImageView) v.findViewById(R.id.star3);
        star[3] = (ImageView) v.findViewById(R.id.star4);
        star[4] = (ImageView) v.findViewById(R.id.star5);
        for (int s=0; s<5;s++){
            if (s<rat) star[s].setImageDrawable(getResources().getDrawable(R.drawable.med_ic_yellow_star));
            else star[s].setImageDrawable(getResources().getDrawable(R.drawable.med_ic_grey_star));
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
            RelativeLayout bl = (RelativeLayout) v.findViewById(R.id.bl_favorits_one);
            Product pr = listProd_F.get(Integer.valueOf(bl.getTag().toString()));
            Bundle data = new Bundle();
            data.putString(PillInfoFragment.PRODUCT_NAME_KEY, pr.getName());
            data.putInt(PillInfoFragment.PRODUCT_ID_KEY, pr.getId());
            data.putInt(PillInfoFragment.CATEGORY_ID_KEY, pr.getId_category());
            ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_PILLINFO, data);
        }
    };

    OnClickListener onClickRecently = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Product pr = listProd_R.get(Integer.valueOf(v.getTag().toString()));
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
