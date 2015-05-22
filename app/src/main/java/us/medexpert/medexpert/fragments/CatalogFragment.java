package us.medexpert.medexpert.fragments;

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

import java.util.ArrayList;
import java.util.List;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.adapter.CatalogFragmentListAdapter;
import us.medexpert.medexpert.db.entity.Category;
import us.medexpert.medexpert.db.tables.CategoryTableHelper;
import us.medexpert.medexpert.loader.CategoriesListLoader;
import us.medexpert.medexpert.tools.FragmentFactory;

public class CatalogFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<List<Category>> {

    public static final String TAG = "CatalogFragment";

    private ListView categoriesList;
    private CatalogFragmentListAdapter adapter;
    private List<Category> elements = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View customBar = getActionBarCustomView(inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        categoriesList = (ListView)inflater.inflate(R.layout.catalog_fragment, container, false);
        categoriesList.setOnItemClickListener(onItemClickListener);
        adapter = new CatalogFragmentListAdapter(getActivity(), elements);
        categoriesList.setAdapter(adapter);
        getLoaderManager().initLoader(CategoriesListLoader.ID, null, this);
        return categoriesList;
    }

    @Override
    public void initActionBarItems() {
        leftItemTouch.setOnClickListener(clickListener);
        rightBarItem.setOnClickListener(clickListener);
        ((RobotoTextView)centerBatItem).setText(getString(R.string.catalog_string));
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FragmentFactory.ID_CATALOG;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).onClick(v);
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Category cat =  ((Category)adapter.getItem(position));
            Bundle data = new Bundle();
            data.putString(CategoryDrugListFragment.CATEGORY_NAME_KEY, cat.getCatName());
            data.putInt(CategoryDrugListFragment.CATEGORY_ID_KEY, cat.getId());
            ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_CATEGORY ,data);
            CategoryTableHelper categoryTableHelper = new CategoryTableHelper();
            categoryTableHelper.updateCategoryViewedCount(getActivity(), cat.getId());
        }
    };

    @Override
    public Loader<List<Category>> onCreateLoader(int id, Bundle args) {
        Loader<List<Category>> loader = null;
        if(id == CategoriesListLoader.ID) {
            loader = new CategoriesListLoader(getActivity());
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Category>> loader, List<Category> data) {
        elements.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Category>> loader) {
        elements.clear();
        adapter.notifyDataSetChanged();
    }
}
