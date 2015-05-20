package us.medexpert.medexpert.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import us.medexpert.medexpert.db.tables.CategoryDrugsTableHelper;


/**
 * Created by user on 20.05.15.
 */
public class CategoryDrugListLoader extends AsyncTaskLoader<Cursor> {

    public static int ID = 1;

    private Cursor lastRecentData;
    private Context context;
    private int categoryId;

    public CategoryDrugListLoader(Context context, int categoryId) {
        super(context);
        this.context = context;
        this.categoryId = categoryId;
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
    public Cursor loadInBackground() {
        CategoryDrugsTableHelper helper = new CategoryDrugsTableHelper();
        lastRecentData = helper.getAllCategoryDrugs(context, categoryId);
        return lastRecentData;
    }
}
