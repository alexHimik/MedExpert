package us.medexpert.medexpert.fragments;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
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

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.adapter.CategoryListAdapter;
import us.medexpert.medexpert.db.tables.CategoryDrugsTableHelper;
import us.medexpert.medexpert.loader.CategoryDrugListLoader;
import us.medexpert.medexpert.tools.FragmentFactory;

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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle data = getArguments();
        categoryName = data.getString(CATEGORY_NAME_KEY);
        categoryId = data.getInt(CATEGORY_ID_KEY);
        drugsList = (SwipeMenuListView)inflater.inflate(R.layout.category_drugs_fragment, container, false);
        drugsList.setOnItemClickListener(onItemClickListener);
        drugsList.setOnMenuItemClickListener(onMenuItemClickListener);
        drugsList.setMenuCreator(swipeMenuCreator);
        drugsList.setOnSwipeListener(onSwipeListener);

        getLoaderManager().initLoader(CategoryDrugListLoader.ID, getArguments(), this);
        return drugsList;
    }

    @Override
    public void initActionBarItems() {
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
            int drugId = (int)listAdapter.getItem(position);
            CategoryDrugsTableHelper categoryDrugsTableHelper = new CategoryDrugsTableHelper();
            categoryDrugsTableHelper.addDrugToFavorites(getActivity(), drugId);
        }

        @Override
        public void onSwipeEnd(int position) {
            //do nothing here
        }
    };
}
