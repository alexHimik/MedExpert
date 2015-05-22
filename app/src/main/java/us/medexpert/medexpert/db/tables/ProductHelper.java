package us.medexpert.medexpert.db.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import us.medexpert.medexpert.adapter.CatalogFragmentListAdapter;
import us.medexpert.medexpert.db.DataBaseHelper;
import us.medexpert.medexpert.db.entity.Category;
import us.medexpert.medexpert.db.entity.Product;

public class ProductHelper {
    public static final String TABLE_NAME = "app_product";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String LINK = "link";
    public static final String LIKED = "liked";
    public static final String ID_CATEGORY = "category_id";
    public static final String PRICE = "priceCol";
    public static final String IMG = "image";
    public static final String DESCR = "description";
    public static final String F_DESCR = "fullDescription";
    public static final String V_D = "view_date";
    public static final String V_C = "view_count";

    public void getProductPlus(Context context) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        DataBaseHelper helper1 = DataBaseHelper.getInstance(context);
        DataBaseHelper helperW = DataBaseHelper.getInstance(context);
        Product prod = null;
        String query, st;
        Cursor cursor;
//        String query = "select T1.product_package_id, T1.title, T1.priceCol, T1.pricePerPill, T2.product_id, T2.title as product_title "+
//                "from app_package T1, app_package_product T2 where T1.product_package_id=T2._id and T2.product_id='" + idProduct + "'";
//        Cursor cursor = helper.getWritableDatabase().rawQuery(query, null);
//
//        float pr = 1000000001f;
//        float r;
//        String st;
//        if(cursor.moveToFirst()) {
//            do {
//                r = Float.valueOf(cursor.getString(cursor.getColumnIndex("priceCol")).substring(1));
//                if (r<pr) pr = r;
//            } while (cursor.moveToNext());
//        }
        query = "select * from " + TABLE_NAME ;
        cursor = helper.getWritableDatabase().rawQuery(query, null);
        int i;
        if(cursor.moveToFirst()) {
            do {
                i = cursor.getInt(cursor.getColumnIndex(ID));
                String price = getPrise(helper1, i);

                ContentValues values = new ContentValues();
                values.put("price", price);

                helper.getWritableDatabase().update(TABLE_NAME, values, ID + "=?", new String[] {String.valueOf(i)});

//                Log.d("QWERT","T="+cursor.getString(cursor.getColumnIndex(TITLE))+" II="+cursor.getInt(cursor.getColumnIndex(ID)));
//                i = cursor.getInt(cursor.getColumnIndex(ID));
//                st = "INSERT INTO product (_id, category_id, title, description, link, image, price, fullDescription, liked) " +
//                        "values(" + i +", " +
//                        cursor.getInt(cursor.getColumnIndex(ID_CATEGORY)) + ", '" +
//                        cursor.getString(cursor.getColumnIndex(TITLE)) + "', '" +
//                        cursor.getString(cursor.getColumnIndex(DESCR)) + "', '" +
//                        cursor.getString(cursor.getColumnIndex(LINK)) + "', '" +
//                        cursor.getString(cursor.getColumnIndex(IMG)) + "', '" +
//                        getPrise(helper1, i) + "', '" +
//                        cursor.getString(cursor.getColumnIndex(F_DESCR)) + "', " +
//                        cursor.getInt(cursor.getColumnIndex(LIKED)) + ")";
//                Log.d("QWERT","Q="+st);
//                helperW.getWritableDatabase().rawQuery(st, null);
            } while (cursor.moveToNext());
         }

    }

    public String getPrise(DataBaseHelper helper, int idProduct){
        Log.d("QWERT","ID="+idProduct);
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
        Log.d("QWERT","P="+st);
        return st;
    }

    public Product getProduct(Context context, int idProduct) {
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
        query = "select * from " + TABLE_NAME + " where " + ID + "='" + idProduct + "'";
        cursor = helper.getWritableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()) {
            prod = new Product();
            prod.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            prod.setName(cursor.getString(cursor.getColumnIndex(TITLE)));
            prod.setLinc(cursor.getString(cursor.getColumnIndex(LINK)));
            prod.setImg(cursor.getString(cursor.getColumnIndex(IMG)));
            prod.setLiked(cursor.getInt(cursor.getColumnIndex(LIKED)));
            prod.setDescr(cursor.getString(cursor.getColumnIndex(DESCR)));
            if (pr < 1000000000f) prod.setPrice("$"+Float.toString(pr));
            else prod.setPrice("$");
        }

        return prod;
    }

    public List<Product> getProductFavor(Context context) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        List<Product> data = new ArrayList<>();
        String query = "select * from " + TABLE_NAME + " where " + LIKED + ">'0' order by " + TITLE + " asc";
        Cursor cursor = helper.getWritableDatabase().rawQuery(query, null);
        Product prod;
        if(cursor.moveToFirst()) {
            do {
                prod = new Product();
                prod.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                prod.setId_category(cursor.getInt(cursor.getColumnIndex(ID_CATEGORY)));
                prod.setName(cursor.getString(cursor.getColumnIndex(TITLE)));
                prod.setLinc(cursor.getString(cursor.getColumnIndex(LINK)));
                prod.setImg(cursor.getString(cursor.getColumnIndex(IMG)));
                prod.setLiked(cursor.getInt(cursor.getColumnIndex(LIKED)));
                prod.setDescr(cursor.getString(cursor.getColumnIndex(DESCR)));
                prod.setPrice(cursor.getString(cursor.getColumnIndex(PRICE)));
                data.add(prod);
            }
            while (cursor.moveToNext());
        }

        return data;
    }
}
