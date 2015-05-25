package us.medexpert.medexpert.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.support.v4.content.LocalBroadcastManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.Collections;
import java.util.List;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.adapter.FavorAdapter;
import us.medexpert.medexpert.db.entity.Product;
import us.medexpert.medexpert.db.tables.ProductHelper;
import us.medexpert.medexpert.dialog.SortDialog;
import us.medexpert.medexpert.tools.FragmentFactory;
import us.medexpert.medexpert.tools.comparator.AscDrugDateComparator;
import us.medexpert.medexpert.tools.comparator.AscDrugNameNameComparator;
import us.medexpert.medexpert.tools.comparator.DescDrugDateComparator;
import us.medexpert.medexpert.tools.comparator.DescDrugNameComparator;

public class FavoritesFragment extends BaseFragment implements ListView.OnItemClickListener {
    public static final String TAG = "FavoritesFragment";
    public static final int FRAGMENT_ID = 5;

    private View parent;
    private SwipeMenuListView lv;
    private List<Product> listProd;
    private FavorAdapter favorAdapter;
    // Karelov - START
    private int mSortPosition;
    // Karelov - END

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customBar = super.getActionBarCustomView(inflater);
        ((MainActivity) getActivity()).getSupportActionBar().setCustomView(customBar);
        ProductHelper ph = ProductHelper.getInstance(getActivity());
        listProd = ph.getProductFavor();
        View v;
        if (listProd.size() == 0) {
            parent = inflater.inflate(R.layout.favorites, container, false);
            LinearLayout ll = (LinearLayout) parent.findViewById(R.id.ll);
            ll.setGravity(Gravity.CENTER_VERTICAL);
            v = ((LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.favor_item_img, null);
            ll.addView(v);
            return parent;
        } else {
            lv = (SwipeMenuListView) inflater.inflate(R.layout.favor_list, container, false);
            favorAdapter = new FavorAdapter(this, listProd);
            lv.setAdapter(favorAdapter);
            lv.setOnItemClickListener(this);
            lv.setOnMenuItemClickListener(onMenuItemClickListener);
            lv.setMenuCreator(menuCreator);
            lv.setOnSwipeListener(onSwipeListener);
            return lv;
        }
    }

    @Override
    public void initActionBarItems() {
        sortBarItem.setVisibility(View.VISIBLE);
        sortBarItem.setOnClickListener(barClickListener);
        rightBarItem.setVisibility(View.VISIBLE);
        rightBarItem.setOnClickListener(barClickListener);
        leftItemTouch.setOnClickListener(barClickListener);
        leftbarItem.setBackgroundResource(R.drawable.med_ic_white_hamburger);
        ((RobotoTextView) centerBatItem).setText(getString(R.string.favorites_string));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(sortReceiver,
                new IntentFilter(SortDialog.SORT_ITEMS_EVENT));
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(sortReceiver);
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Product pr = favorAdapter.getItem(position);
        Bundle data = new Bundle();
        data.putString(PillInfoFragment.PRODUCT_NAME_KEY, pr.getName());
        data.putInt(PillInfoFragment.PRODUCT_ID_KEY, pr.getId());
        data.putInt(PillInfoFragment.CATEGORY_ID_KEY, pr.getId_category());
        ((MainActivity) getActivity()).handleFragmentSwitching(FragmentFactory.ID_PILLINFO, data);
    }

    private View.OnClickListener barClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_drawer_item_touch:
                    ((MainActivity) getActivity()).onClick(v);
                    break;
                case R.id.right_drawer_item:
                    ((MainActivity) getActivity()).onClick(v);
                    break;
                case R.id.sort_bar_item:
                    SortDialog sortDialog = new SortDialog((MainActivity) getActivity(), mSortPosition);
                    sortDialog.show();
                    break;
            }
        }
    };

    private SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener = new SwipeMenuListView.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
            return false;
        }
    };

    private SwipeMenuCreator menuCreator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            SwipeMenuItem item = new SwipeMenuItem(getActivity());
            item.setBackground(R.color.color_light_grey);
            item.setIcon(R.drawable.med_ic_white_big_heart_broken);
            item.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    getResources().getDimension(R.dimen.favorite_item_width),
                    getResources().getDisplayMetrics()));
            menu.addMenuItem(item);
        }
    };

    private SwipeMenuListView.OnSwipeListener onSwipeListener = new SwipeMenuListView.OnSwipeListener() {
        @Override
        public void onSwipeStart(int position) {

        }

        @Override
        public void onSwipeEnd(int position) {
            if (position != -1) {
                ProductHelper categoryDrugsTableHelper = ProductHelper.getInstance(getActivity());
                categoryDrugsTableHelper.removeDrugFromFavorites(
                        favorAdapter.getItem(position).getId());
            }
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

    private BroadcastReceiver sortReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SortDialog.SORT_ITEMS_EVENT.equals(intent.getAction())) {
                int position = intent.getIntExtra(SortDialog.SORT_TYPE_KEY, -1);
                if (position >= 0) {
                    mSortPosition = position;
                }
                switch (position) {
                    case SortDialog.SORT_BY_NAME_ASC: {
                        Collections.sort(favorAdapter.getItems(), new AscDrugNameNameComparator());
                        favorAdapter.notifyDataSetChanged();
                        break;
                    }
                    case SortDialog.SORT_BY_NAME_DESC: {
                        Collections.sort(favorAdapter.getItems(), new DescDrugNameComparator());
                        favorAdapter.notifyDataSetChanged();
                        break;
                    }
                    case SortDialog.SORT_BY_POP_ASC: {
                        Collections.sort(favorAdapter.getItems(), new AscDrugDateComparator());
                        favorAdapter.notifyDataSetChanged();
                        break;
                    }
                    case SortDialog.SORT_BY_POP_DESC: {
                        Collections.sort(favorAdapter.getItems(), new DescDrugDateComparator());
                        favorAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    };
}
