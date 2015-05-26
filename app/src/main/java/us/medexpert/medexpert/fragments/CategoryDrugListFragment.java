package us.medexpert.medexpert.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.Collections;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.adapter.CategoryListAdapter;
import us.medexpert.medexpert.db.tables.ProductHelper;
import us.medexpert.medexpert.dialog.SortDialog;
import us.medexpert.medexpert.loader.CategoryDrugListLoader;
import us.medexpert.medexpert.tools.FragmentFactory;
import us.medexpert.medexpert.tools.comparator.AscDrugDateComparator;
import us.medexpert.medexpert.tools.comparator.AscDrugNameNameComparator;
import us.medexpert.medexpert.tools.comparator.DescDrugDateComparator;
import us.medexpert.medexpert.tools.comparator.DescDrugNameComparator;

/**
 * Created by user on 20.05.15.
 */
public class CategoryDrugListFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "CategoryDrugListFragment";
    public static final String CATEGORY_NAME_KEY = "category_name";
    public static final String CATEGORY_ID_KEY = "category_id";

    private String categoryName;
    private int categoryId;
    private SwipeMenuListView drugsList;
    private CategoryListAdapter listAdapter;
    private int mSortPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle data = getArguments();
        categoryName = data.getString(CATEGORY_NAME_KEY);
        categoryId = data.getInt(CATEGORY_ID_KEY);
        drugsList = (SwipeMenuListView)inflater.inflate(R.layout.category_drugs_fragment, container, false);
        drugsList.setOnItemClickListener(onItemClickListener);
        drugsList.setOnMenuItemClickListener(onMenuItemClickListener);
        drugsList.setMenuCreator(swipeMenuCreator);
        drugsList.setOnSwipeListener(onSwipeListener);
        View customBar = getActionBarCustomView(inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        getLoaderManager().initLoader(CategoryDrugListLoader.ID, getArguments(), this);
        return drugsList;
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
    public void initActionBarItems() {
        sortBarItem.setVisibility(View.VISIBLE);
        sortBarItem.setOnClickListener(onClickListener);
        rightBarItem.setOnClickListener(onClickListener);
        leftItemTouch.setOnClickListener(onClickListener);
        ((RobotoTextView)centerBatItem).setText(categoryName);
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FragmentFactory.ID_CATEGORY;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        listAdapter = new CategoryListAdapter(this, data, false);
        drugsList.setAdapter(listAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader loader = null;
        if(id == CategoryDrugListLoader.ID) {
            loader = new CategoryDrugListLoader(getActivity(), categoryId);
        }
        return loader;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        listAdapter.changeCursor(null);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor cursor =  listAdapter.getCursor();
            cursor.moveToPosition(position);
            Bundle data = new Bundle();
            data.putString(PillInfoFragment.PRODUCT_NAME_KEY, cursor.getString(
                    cursor.getColumnIndex(ProductHelper.TITLE_COLUMN)));
            data.putInt(PillInfoFragment.PRODUCT_ID_KEY, cursor.getInt(cursor.getColumnIndex(
                    ProductHelper.ID_COLUMN)));
            data.putInt(PillInfoFragment.CATEGORY_ID_KEY, categoryId);
            ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_PILLINFO, data);
        }
    };

    private SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener =
            new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    return false;
                }
            };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.right_drawer_item) {
                ((MainActivity)getActivity()).onClick(v);
            } else if(v.getId() == R.id.left_drawer_item_touch) {
                ((MainActivity)getActivity()).onClick(v);
            } else if(v.getId() == R.id.sort_bar_item) {
                SortDialog sortDialog = new SortDialog((MainActivity) getActivity(), mSortPosition);
                sortDialog.show();
            } else {
                //TODO add handling if there will be additional bar items
            }
        }
    };

    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            SwipeMenuItem item = new SwipeMenuItem(getActivity());
            item.setBackground(R.color.tutorial_pink);
            item.setIcon(R.drawable.med_ic_white_big_heart);
            item.setWidth((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    getResources().getDimension(R.dimen.favorite_item_width),
                    getResources().getDisplayMetrics()));
            menu.addMenuItem(item);
        }
    };

    private SwipeMenuListView.OnSwipeListener onSwipeListener = new SwipeMenuListView.OnSwipeListener() {
        @Override
        public void onSwipeStart(int position) {
            //do nothing here
        }

        @Override
        public void onSwipeEnd(int position) {
            int drugId = (int)listAdapter.getItem(position);
            ProductHelper categoryDrugsTableHelper = ProductHelper.getInstance(getActivity());
            categoryDrugsTableHelper.addDrugToFavorites(drugId);
        }
    };

    private BroadcastReceiver sortReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(SortDialog.SORT_ITEMS_EVENT.equals(intent.getAction())) {
                final ProductHelper helper = ProductHelper.getInstance(getActivity());
                int position = intent.getIntExtra(SortDialog.SORT_TYPE_KEY, -1);
                if (position >= 0) {
                    mSortPosition = position;
                }
                switch (position) {
                    case SortDialog.SORT_BY_NAME_ASC: {
                        sortHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = Message.obtain();
                                msg.obj = helper.getAllCategoryDrugs(categoryId);
                                sortHandler.sendMessage(msg);
                            }
                        });
                        break;
                    }
                    case SortDialog.SORT_BY_NAME_DESC: {
                        sortHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = Message.obtain();
                                msg.obj = helper.getAllCategoryDrugsDesc(categoryId);
                                sortHandler.sendMessage(msg);
                            }
                        });
                        break;
                    }
                    case SortDialog.SORT_BY_POP_ASC: {
                        sortHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = Message.obtain();
                                msg.obj = helper.getAllCategoryDrugsByDate(categoryId);
                                sortHandler.sendMessage(msg);
                            }
                        });
                        break;
                    }
                    case SortDialog.SORT_BY_POP_DESC: {
                        sortHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = Message.obtain();
                                msg.obj = helper.getAllCategoryDrugsByDateDesc(categoryId);
                                sortHandler.sendMessage(msg);
                            }
                        });
                        break;
                    }
                }
            }
        }
    };

    private Handler sortHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Cursor cursor = (Cursor)msg.obj;
            listAdapter = new CategoryListAdapter(
                    CategoryDrugListFragment.this, cursor, false);
            drugsList.setAdapter(listAdapter);
        }
    };
}
