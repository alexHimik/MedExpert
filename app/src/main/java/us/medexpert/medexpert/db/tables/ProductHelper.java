package us.medexpert.medexpert.db.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import us.medexpert.medexpert.adapter.SearchListAdapter;
import us.medexpert.medexpert.db.DataBaseHelper;
import us.medexpert.medexpert.db.entity.Product;
import us.medexpert.medexpert.db.entity.SearchListEntity;

public class ProductHelper {

    public static final String TABLE_NAME = "app_product";
    public static final String ID_COLUMN = "_id";
    public static final String CATEGORY_ID_COLUMN = "category_id";
    public static final String LINK_COLUMN = "link";
    public static final String FULL_DESCR_COLUMN = "fullDescription";
    public static final String IMAGE_COLUMN = "image";
    public static final String TITLE_COLUMN = "title";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String LIKED_COLUMN = "liked";
    public static final String VIEW_COUNT_COLUMN = "view_count";
    public static final String DRUG_PRICE_COLUMN = "price";
    public static final String VIEW_DATE_COLUMN = "date_view";

    private Context context;
    private static ProductHelper instance;

    private ProductHelper(Context context) {
        this.context = context;
    }

    public static ProductHelper getInstance(Context context) {
        if(instance == null) {
            instance = new ProductHelper(context);
        }
        return instance;
    }


    public String getPrise(DataBaseHelper helper, int idProduct) {
        String st = "$";
        String query = "select T1.product_package_id, T1.title, T1.priceCol, T1.pricePerPill, T2.product_id, T2.title as product_title "+
                "from app_package T1, app_package_product T2 where T1.product_package_id=T2._id and T2.product_id='" + idProduct + "'";

        Cursor cursor = helper.getWritableDatabase().rawQuery(query, null);

        float pr = 1000000001f;
        float r;
        if(cursor.moveToFirst()) {
            do {
                r = Float.valueOf(cursor.getString(cursor.getColumnIndex("priceCol")).substring(1));
                if (r<pr) {
                    pr = r;
                    st = cursor.getString(cursor.getColumnIndex("priceCol"));
                }
            } while (cursor.moveToNext());
        }
        return st;
    }

    public Product getProduct(int idProduct) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        Product prod = null;
        String query = "select T1.product_package_id, T1.title, T1.priceCol, T1.pricePerPill, T2.product_id, T2.title as product_title "+
                "from app_package T1, app_package_product T2 where T1.product_package_id=T2._id and T2.product_id='" + idProduct + "'";
        Cursor cursor = helper.getWritableDatabase().rawQuery(query, null);

        float pr = 1000000001f;
        float r;
        String st;
        if(cursor.moveToFirst()) {
            do {
                r = Float.valueOf(cursor.getString(cursor.getColumnIndex("priceCol")).substring(1));
                if (r<pr) pr = r;
            } while (cursor.moveToNext());
        }
        query = "select * from " + TABLE_NAME + " where " + ID_COLUMN + "='" + idProduct + "'";
        cursor = helper.getWritableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()) {
            prod = new Product();
            prod.setId(cursor.getInt(cursor.getColumnIndex(ID_COLUMN)));
            prod.setName(cursor.getString(cursor.getColumnIndex(TITLE_COLUMN)));
            prod.setLinc(cursor.getString(cursor.getColumnIndex(LINK_COLUMN)));
            prod.setImg(cursor.getString(cursor.getColumnIndex(IMAGE_COLUMN)));
            prod.setLiked(cursor.getInt(cursor.getColumnIndex(LIKED_COLUMN)));
            prod.setDescr(cursor.getString(cursor.getColumnIndex(DESCRIPTION_COLUMN)));
            if (pr < 1000000000f) prod.setPrice("$" + Float.toString(pr));
            else prod.setPrice("$");
        }

        return prod;
    }

    public List<Product> getProductFavor() {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        List<Product> data = new ArrayList<>();
        String query = "SELECT T1._id, T1.title as prodt, T1.link, T1.liked, T1.category_id, T1.image, T1.description, " +
                "T1.date_view, T1.view_count, T1.price, T2.title as catt " +
                "FROM app_product T1, app_category T2 WHERE T1.liked>'0' AND T1.category_id = T2._id ORDER BY T1.title asc";
        Cursor cursor = helper.getWritableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                data.add(setProduct(cursor));
            } while (cursor.moveToNext());
        }
        return data;
    }

    public Cursor getAllCategoryDrugs(int categoryId) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        Cursor cursor = helper.getWritableDatabase().query(TABLE_NAME,
                new String[] {ID_COLUMN, CATEGORY_ID_COLUMN, IMAGE_COLUMN,
                        TITLE_COLUMN, LIKED_COLUMN, DESCRIPTION_COLUMN, DRUG_PRICE_COLUMN},
                CATEGORY_ID_COLUMN + "=?", new String[]{String.valueOf(categoryId)},
                TITLE_COLUMN, null, TITLE_COLUMN);
        return cursor;
    }

    public void addDrugToFavorites(int drugId) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        Cursor cursor = helper.getWritableDatabase().query(TABLE_NAME, new String[] {LIKED_COLUMN},
                ID_COLUMN + "=?", new String[] {String.valueOf(drugId)}, null, null, null);
        cursor.moveToFirst();

        ContentValues values = new ContentValues();
        values.put(LIKED_COLUMN, 1);
        helper.getWritableDatabase().update(TABLE_NAME, values, ID_COLUMN + "=?",
                new String[] {String.valueOf(drugId)});
    }

    public void removeDrugFromFavorites(int drugId) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        Cursor cursor = helper.getWritableDatabase().query(TABLE_NAME, new String[] {LIKED_COLUMN},
                ID_COLUMN + "=?", new String[] {String.valueOf(drugId)}, null, null, null);
        cursor.moveToFirst();

        ContentValues values = new ContentValues();
        values.put(LIKED_COLUMN, 0);
        helper.getWritableDatabase().update(TABLE_NAME, values, ID_COLUMN + "=?",
                new String[]{String.valueOf(drugId)});
    }

    public List<SearchListEntity> getDrugsForSearch(String query) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        List<SearchListEntity> data = new ArrayList<>();
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select * from app_product where title like '" + query + "%';", null);
        if(cursor.moveToFirst()) {
            do {
                SearchListEntity entity = new SearchListEntity();
                entity.setId(cursor.getInt(cursor.getColumnIndex(ID_COLUMN)));
                String name = cursor.getString(cursor.getColumnIndex(TITLE_COLUMN));
                String justName = name.substring(0, name.indexOf("(") - 1);
                entity.setName(justName);
                entity.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION_COLUMN)));
                entity.setPrice(cursor.getString(cursor.getColumnIndex(DRUG_PRICE_COLUMN)));
                entity.setImage(cursor.getString(cursor.getColumnIndex(LINK_COLUMN)));
                entity.setGeneric(name.substring(name.indexOf("(") + 1, name.indexOf(")")));
                entity.setFavorite(cursor.getInt(cursor.getColumnIndex(LIKED_COLUMN)) > 0);
                entity.setType(SearchListAdapter.ITEM_TYPE_DRUG);
                data.add(entity);
            } while (cursor.moveToNext());
        }
        return data;
    }

    public void updateDrugViewedDate(int drugId) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        Cursor cursor = helper.getReadableDatabase().rawQuery("select " + VIEW_COUNT_COLUMN +
                " from " + TABLE_NAME + " where " + ID_COLUMN + "=?",
                new String[] {String.valueOf(drugId)});
        cursor.moveToFirst();
        int viewCount = cursor.getInt(cursor.getColumnIndex(VIEW_COUNT_COLUMN));

        ContentValues values = new ContentValues();
        values.put(VIEW_DATE_COLUMN, System.currentTimeMillis());
        values.put(VIEW_COUNT_COLUMN, viewCount + 1);

        helper.getWritableDatabase().update(TABLE_NAME, values, ID_COLUMN + "=?",
                new String[] {String.valueOf(drugId)});
    }

    public List<Product> getLastViewedDrugs() {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        String query = "SELECT T1._id, T1.title as prodt, T1.link, T1.liked, T1.category_id, T1.image, T1.description, " +
                "T1.date_view, T1.view_count, T1.price, T2.title as catt " +
                "FROM app_product T1, app_category T2 WHERE T1.category_id = T2._id ORDER BY T1.date_view desc";

        Cursor cursor = helper.getReadableDatabase().rawQuery(query, null);
        List<Product> data = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                data.add(setProduct(cursor));
            } while (cursor.moveToNext());
        }

        return data;
    }

    private Product setProduct(Cursor cursor) {
        Product prod = new Product();
        prod.setId(cursor.getInt(cursor.getColumnIndex(ID_COLUMN)));
        prod.setId_category(cursor.getInt(cursor.getColumnIndex(CATEGORY_ID_COLUMN)));
        prod.setName(cursor.getString(cursor.getColumnIndex("prodt")));
        prod.setLinc(cursor.getString(cursor.getColumnIndex(LINK_COLUMN)));
        prod.setImg(cursor.getString(cursor.getColumnIndex(IMAGE_COLUMN)));
        prod.setLiked(cursor.getInt(cursor.getColumnIndex(LIKED_COLUMN)));
        prod.setDescr(cursor.getString(cursor.getColumnIndex(DESCRIPTION_COLUMN)));
        prod.setPrice(cursor.getString(cursor.getColumnIndex(DRUG_PRICE_COLUMN)));
        prod.setNameCat(cursor.getString(cursor.getColumnIndex("catt")));
        return prod;
    }
}
