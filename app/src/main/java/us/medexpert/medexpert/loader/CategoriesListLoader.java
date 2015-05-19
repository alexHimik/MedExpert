package us.medexpert.medexpert.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import us.medexpert.medexpert.db.entity.Category;

/**
 * Created by user on 18.05.15.
 */
public class CategoriesListLoader extends AsyncTaskLoader<List<Category>> {

    private List<Category> lastRecentData;

    public CategoriesListLoader(Context context) {
        super(context);
    }

    @Override
    public List<Category> loadInBackground() {
        if(lastRecentData != null) {
            return lastRecentData;
        } else {
            forceLoad();
        }
        return null;
    }

    @Override
    public void deliverResult(List<Category> data) {
        super.deliverResult(data);
    }
}
