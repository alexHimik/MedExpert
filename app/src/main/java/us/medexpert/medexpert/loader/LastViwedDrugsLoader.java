package us.medexpert.medexpert.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import us.medexpert.medexpert.db.entity.Product;
import us.medexpert.medexpert.db.tables.ProductHelper;

/**
 * Created by user on 23.05.15.
 */
public class LastViwedDrugsLoader extends AsyncTaskLoader<List<Product>> {

    public static final int ID = 2;

    private Context context;

    public LastViwedDrugsLoader(Context context) {
        super(context);
        this.context = context;
    }

    private List<Product> lastData;

    @Override
    protected void onStartLoading() {
        if(lastData == null || lastData.size() == 0) {
            forceLoad();
        } else {
            deliverResult(lastData);
        }
    }

    @Override
    public List<Product> loadInBackground() {
        ProductHelper productHelper = ProductHelper.getInstance(context);
        return productHelper.getLastViewedDrugs();
    }
}
