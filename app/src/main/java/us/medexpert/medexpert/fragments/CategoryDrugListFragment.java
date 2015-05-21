package us.medexpert.medexpert.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.devspark.robototextview.widget.RobotoTextView;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.adapter.CategoryListAdapter;
import us.medexpert.medexpert.loader.CategoriesListLoader;
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
    private ListView drugsList;
    private CategoryListAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle data = getArguments();
        categoryName = data.getString(CATEGORY_NAME_KEY);
        categoryId = data.getInt(CATEGORY_ID_KEY);
        drugsList = (ListView)inflater.inflate(R.layout.category_drugs_fragment, container, false);
        drugsList.setOnItemClickListener(onItemClickListener);

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
}
