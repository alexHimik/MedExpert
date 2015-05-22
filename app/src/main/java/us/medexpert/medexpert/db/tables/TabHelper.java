package us.medexpert.medexpert.db.tables;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import us.medexpert.medexpert.adapter.CatalogFragmentListAdapter;
import us.medexpert.medexpert.adapter.SearchListAdapter;
import us.medexpert.medexpert.db.DataBaseHelper;
import us.medexpert.medexpert.db.entity.Category;
import us.medexpert.medexpert.db.entity.Product;
import us.medexpert.medexpert.db.entity.SearchListEntity;

public class TabHelper {
    private Context context;
    private DataBaseHelper helper;

    public static final String TABLE_PRODUCT = "app_product";
    public static final String TABLE_CATEGORY = "app_category";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String LINK = "link";
    public static final String LIKED = "liked";
    public static final String ID_CATEGORY = "category_id";
    public static final String IMG = "image";
    public static final String DESCR = "description";
    public static final String V_D = "view_date";
    public static final String V_C = "view_count";
    public static final String PRICE = "price";

//    public static final String qAllCategory = "SELECT " + ID + ", " + TITLE + ", " + LINK +;

    public TabHelper(Context context){
        this.context = context;
        helper = DataBaseHelper.getInstance(context);
    }

    public List<Category> getAllCategories() {
        List<Category> data = new ArrayList<>();
        Cursor cursor = helper.getWritableDatabase().query(TABLE_CATEGORY,
                new String[] {ID, TITLE, LINK}, null, null, TITLE, null, TITLE);

        if(cursor.moveToFirst()) {
            do {
                Category allOne = new Category(cursor.getInt(cursor.getColumnIndex(CategoryTableHelper.ID_CLOUMN)),
                        cursor.getString(cursor.getColumnIndex(CategoryTableHelper.TITLE_COLUMN)),
                        CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM);
                data.add(allOne);
            } while (cursor.moveToNext());
        }
        return data;
    }

    public List<Category> getPopularCategories() {
        List<Category> data = new ArrayList<>();
        String query = "select * from app_category order by view_count desc, title asc";
        Cursor cursor = helper.getWritableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                Category allOne = new Category(cursor.getInt(cursor.getColumnIndex(CategoryTableHelper.ID_CLOUMN)),
                        cursor.getString(cursor.getColumnIndex(CategoryTableHelper.TITLE_COLUMN)),
                        CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM);
                data.add(allOne);
            } while (cursor.moveToNext());
        }
        return data;
    }

    public List<SearchListEntity> getCategoriesForSearch(String catName) {
        List<SearchListEntity> data = new ArrayList<>();
        String query = "select _id, title from app_category where title like '"+ catName + "%';";
        String drugQuery = "select _id, title from app_product where title like '" + catName + "%' and category_id=?;";
        Cursor categories = helper.getReadableDatabase().rawQuery(query, null);
        if(categories.moveToFirst()) {
            do {
                int catId = categories.getInt(categories.getColumnIndex(ID));
                Cursor drugCursor = helper.getReadableDatabase().rawQuery(drugQuery.replace("?",
                        String.valueOf(catId)), null);
                SearchListEntity entity = new SearchListEntity();
                entity.setId(catId);
                entity.setName(categories.getString(categories.getColumnIndex(TITLE)));
                int amount = drugCursor.getCount();
                entity.setAmount("");

                if(amount > 0) {
                    entity.setAmount(String.valueOf(amount));
                }

                entity.setType(SearchListAdapter.ITEM_TYPE_CATEGORY);
                data.add(entity);
            } while (categories.moveToNext());
        }
        return data;
    }

    public String getCategoryName(int id_categor) {
        String query = "select * from app_category where " + ID + "='" + id_categor + "'";
        Cursor cursor = helper.getWritableDatabase().rawQuery(query, null);
        String result = "";
        if(cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(TITLE));
        }
        return result;
    }

    public void updateCategoryViewedCount(int categoryId) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        Cursor cursor = helper.getWritableDatabase().query(TABLE_CATEGORY, new String[] {V_C},
                ID + "=?", new String[] {String.valueOf(categoryId)}, null, null, null);
        cursor.moveToFirst();
        StringBuilder builder = new StringBuilder();
        builder.append("update ");
        builder.append(TABLE_CATEGORY);
        builder.append(" set ");
        builder.append(V_C);
        builder.append("='");
        builder.append(cursor.getInt(cursor.getColumnIndex(V_C)) + 1);
        builder.append("' where ");
        builder.append(ID);
        builder.append("=");
        builder.append(categoryId);
        builder.append(";");
        helper.getWritableDatabase().execSQL(builder.toString());
    }

    public Product getProduct(int idProduct) {
        Product prod = null;
        String query = "select * from " + TABLE_PRODUCT + " where " + ID + "='" + idProduct + "'";
        Cursor cursor = helper.getWritableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()) {
            prod = setProduct(cursor);
        }
        return prod;
    }

    public List<Product> getProductFavor() {
        List<Product> data = new ArrayList<>();
        String query = "select * from " + TABLE_PRODUCT + " where " + LIKED + ">'0' order by " + TITLE + " asc";
        Cursor cursor = helper.getWritableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                data.add(setProduct(cursor));
            } while (cursor.moveToNext());
        }
        return data;
    }

    public Product setProduct(Cursor cursor) {
        Product prod = new Product();
        prod.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        prod.setId_category(cursor.getInt(cursor.getColumnIndex(ID_CATEGORY)));
        prod.setName(cursor.getString(cursor.getColumnIndex(TITLE)));
        prod.setLinc(cursor.getString(cursor.getColumnIndex(LINK)));
        prod.setImg(cursor.getString(cursor.getColumnIndex(IMG)));
        prod.setLiked(cursor.getInt(cursor.getColumnIndex(LIKED)));
        prod.setDescr(cursor.getString(cursor.getColumnIndex(DESCR)));
        prod.setPrice(cursor.getString(cursor.getColumnIndex(PRICE)));
        return prod;
    }
}
