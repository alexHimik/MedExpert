package us.medexpert.medexpert.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.List;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.adapter.FavorAdapter;
import us.medexpert.medexpert.db.entity.Product;
import us.medexpert.medexpert.db.tables.TabHelper;
import us.medexpert.medexpert.tools.FragmentFactory;

public class FavoritesFragment extends BaseFragment  implements ListView.OnItemClickListener{
    public static final String TAG = "FavoritesFragment";
    public static final int FRAGMENT_ID = 5;

    private View parent;
    private ListView lv;
    private Context context;
    private List<Product> listProd;
    private LinearLayout ll;
    private TabHelper tabHelper;
    private FavorAdapter favorAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View customBar = super.getActionBarCustomView(inflater);
        ((MainActivity) getActivity()).getSupportActionBar().setCustomView(customBar);
//        parent = inflater.inflate(R.layout.favorites, container, false);
        context = getActivity().getBaseContext();
        tabHelper = new TabHelper(context);

        listProd = tabHelper.getFavor();
        View v;
        if (listProd.size() == 0) {
            parent = inflater.inflate(R.layout.favorites, container, false);
            LinearLayout ll = (LinearLayout)parent.findViewById(R.id.ll);
            ll.setGravity(Gravity.CENTER_VERTICAL);
            v = ((LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.favor_item_img, null);
            ll.addView(v);
            return parent;
        }
        else {
            lv = (ListView) inflater.inflate(R.layout.favor_list, container, false);
            favorAdapter = new FavorAdapter(this, listProd, true);
            lv.setAdapter(favorAdapter);
            lv.setOnItemClickListener(this);
            return lv;
        }
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

//    public void formFavor(LayoutInflater inflater, ViewGroup container){
//
//        listProd = tabHelper.getFavor();
//        View v;
//        if (listProd.size() == 0) {
//            parent = inflater.inflate(R.layout.favorites, container, false);
//            LinearLayout ll = (LinearLayout)parent.findViewById(R.id.ll);
//            ll.setGravity(Gravity.CENTER_VERTICAL);
//            v = ((LayoutInflater) getActivity().getSystemService(
//                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.favor_item_img, null);
//            ll.addView(v);
//        }
//        else {
//            parent = inflater.inflate(R.layout.favor_list, container, false);
//
//            favorAdapter = new FavorAdapter(this, listProd, true);
//            parent.setAdapter(favorAdapter);
//            parent.setOnItemClickListener(this);
//
//
//            Product pr;
//            int ik = 3;
//            if (ik>listProd.size()) ik = listProd.size();
////            CategoryTableHelper ch = new CategoryTableHelper();
//            for (int i = 0; i<ik; i++){
//                pr = listProd.get(i);
//                v = getActivity().getLayoutInflater().inflate(R.layout.home_item_favor, null);
//                RelativeLayout bl = (RelativeLayout) v.findViewById(R.id.bl_favorits_one);
//                bl.setOnClickListener(onClickFavor);
//                bl.setTag(""+i);
//                String st = pr.getName();
//                int i1 = st.indexOf("(");
//                if (i1>0) {
//                    st = st.substring(0,i1).trim();
//                }
//
//                ((RobotoTextView) v.findViewById(R.id.name)).setText(st);
//
////                ((RobotoTextView) v.findViewById(R.id.gener)).setText(ch.getCategoryName(context, pr.getId_category()));
//                ((RobotoTextView) v.findViewById(R.id.gener)).setText(pr.getNameCat());
//                ((RobotoTextView) v.findViewById(R.id.price)).setText(pr.getPrice());
//                ((ImageView) v.findViewById(R.id.iv2)).setImageDrawable(getResources().
//                        getDrawable(R.drawable.med_ic_pink_heart_checked));
//
//                ll.addView(v);
//            }
//        }
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Product prod = favorAdapter.getItem(position);
//        Bundle bundle = new Bundle();
//        bundle.putString(NewsPageFragment.ARTICLE_KEY, artice.getId());
//        ((MainActivity)getActivity()).handleFragmentSwitching(NewsPageFragment.FRAGMENT_ID,
//                bundle);
    }

//    View.OnClickListener onClickFavor = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
////                Product pr = listProd.get(Integer.valueOf(v.getTag().toString()));
////                Bundle data = new Bundle();
////                data.putString(PillInfoFragment.PRODUCT_NAME_KEY, pr.getName());
////                data.putInt(PillInfoFragment.PRODUCT_ID_KEY, pr.getId());
////                data.putInt(PillInfoFragment.CATEGORY_ID_KEY, pr.getId_category());
////                ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_PILLINFO, data);
//        }
//    };


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
