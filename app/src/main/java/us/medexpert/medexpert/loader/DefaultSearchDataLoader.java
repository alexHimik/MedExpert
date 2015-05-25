package us.medexpert.medexpert.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import us.medexpert.medexpert.adapter.SearchListAdapter;
import us.medexpert.medexpert.db.entity.Category;
import us.medexpert.medexpert.db.entity.Product;
import us.medexpert.medexpert.db.entity.SearchListEntity;
import us.medexpert.medexpert.db.tables.CategoryTableHelper;
import us.medexpert.medexpert.db.tables.ProductHelper;

/**
 * Created by user on 23.05.15.
 */
public class DefaultSearchDataLoader extends AsyncTaskLoader<List<SearchListEntity>> {

    public static int ID = 3;

    private Context context;
    private List<SearchListEntity> data;

    public DefaultSearchDataLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        if(data == null || data.size() == 0) {
            forceLoad();
        } else {
            deliverResult(data);
        }
    }

    @Override
    public List<SearchListEntity> loadInBackground() {
        ProductHelper helper = ProductHelper.getInstance(context);
        List<Product> lastDrugs = helper.getLastViewedDrugs();
        CategoryTableHelper categoryTableHelper = new CategoryTableHelper();
        List<Category> popCats = categoryTableHelper.getPopularCategories(context);

        List<SearchListEntity> data = new ArrayList<>();

        SearchListEntity catHeader = new SearchListEntity();
        catHeader.setId(-1);
        catHeader.setName("Category");
        catHeader.setType(SearchListAdapter.ITEM_TYPE_HEADR);
        data.add(catHeader);

        for(Category c : popCats) {
            SearchListEntity entity = new SearchListEntity();
            entity.setId(c.getId());
            entity.setName(c.getCatName());
            entity.setType(SearchListAdapter.ITEM_TYPE_CATEGORY);
            data.add(entity);
        }

        for(Product p : lastDrugs) {
            SearchListEntity entity = new SearchListEntity();
            entity.setId(p.getId());
            String name = p.getName();

            if(name.indexOf("(") != -1) {
                String justName = name.substring(0, name.indexOf("(") - 1);
                entity.setName(justName);
            } else {
                entity.setName(name);
            }

            entity.setDescription(p.getDescr());
            entity.setPrice(p.getPrice());
            entity.setImage(p.getImg());
            entity.setCategoryId(p.getId_category());

            if(name.indexOf("(") != -1) {
                entity.setGeneric(name.substring(name.indexOf("(") + 1, name.indexOf(")")));
            } else {
                entity.setGeneric("");
            }

            entity.setFavorite(p.getLiked() > 0);
            entity.setType(SearchListAdapter.ITEM_TYPE_DRUG);
            data.add(entity);
        }

        return data;
    }
}
