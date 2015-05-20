package us.medexpert.medexpert.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import us.medexpert.medexpert.adapter.CatalogFragmentListAdapter;
import us.medexpert.medexpert.db.entity.Category;
import us.medexpert.medexpert.db.tables.CategoryTableHelper;

/**
 * Created by user on 18.05.15.
 */
public class CategoriesListLoader extends AsyncTaskLoader<List<Category>> {

    public static int ID = 0;

    private List<Category> lastRecentData;
    private Context context;

    public CategoriesListLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        if(lastRecentData != null) {
            deliverResult(lastRecentData);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Category> loadInBackground() {
        CategoryTableHelper categoryTableHelper = new CategoryTableHelper();
        List<Category> topData = categoryTableHelper.getPopularCategories(context);
        List<Category> allData = categoryTableHelper.getAllCategories(context);
        lastRecentData = CatalogFragmentListAdapter.makeResultList(context, topData, allData);
        return lastRecentData;
    }
}
