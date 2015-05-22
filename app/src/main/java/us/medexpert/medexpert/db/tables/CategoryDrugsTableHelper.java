package us.medexpert.medexpert.db.tables;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import us.medexpert.medexpert.adapter.SearchListAdapter;
import us.medexpert.medexpert.db.DataBaseHelper;
import us.medexpert.medexpert.db.entity.SearchListEntity;

/**
 * Created by user on 20.05.15.
 */
public class CategoryDrugsTableHelper {

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

    public Cursor getAllCategoryDrugs(Context context, int categoryId) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        Cursor cursor = helper.getWritableDatabase().query(TABLE_NAME,
                new String[] {ID_COLUMN, CATEGORY_ID_COLUMN, IMAGE_COLUMN,
                        TITLE_COLUMN, LIKED_COLUMN, DESCRIPTION_COLUMN, DRUG_PRICE_COLUMN},
                CATEGORY_ID_COLUMN + "=?", new String[]{String.valueOf(categoryId)},
                TITLE_COLUMN, null, TITLE_COLUMN);
        return cursor;
    }

    public void addDrugToFavorites(Context context, int drugId) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        Cursor cursor = helper.getWritableDatabase().query(TABLE_NAME, new String[] {LIKED_COLUMN},
                ID_COLUMN + "=?", new String[] {String.valueOf(drugId)}, null, null, null);
        cursor.moveToFirst();
        StringBuilder builder = new StringBuilder();
        builder.append("update ");
        builder.append(TABLE_NAME);
        builder.append(" set ");
        builder.append(LIKED_COLUMN);
        builder.append("='");
        builder.append(1);
        builder.append("' where ");
        builder.append(ID_COLUMN);
        builder.append("=");
        builder.append(drugId);
        builder.append(";");
        helper.getWritableDatabase().execSQL(builder.toString());
    }

    public void removeDrugFromFavorites(Context context, int drugId) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        Cursor cursor = helper.getWritableDatabase().query(TABLE_NAME, new String[] {LIKED_COLUMN},
                ID_COLUMN + "=?", new String[] {String.valueOf(drugId)}, null, null, null);
        cursor.moveToFirst();
        StringBuilder builder = new StringBuilder();
        builder.append("update ");
        builder.append(TABLE_NAME);
        builder.append(" set ");
        builder.append(LIKED_COLUMN);
        builder.append("='");
        builder.append(0);
        builder.append("' where ");
        builder.append(ID_COLUMN);
        builder.append("=");
        builder.append(drugId);
        builder.append(";");
        helper.getWritableDatabase().execSQL(builder.toString());
    }

    public List<SearchListEntity> getDrugsForSearch(Context context, String query) {
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
                entity.setPrice(cursor.getString(cursor.getColumnIndex(CategoryDrugsTableHelper.DRUG_PRICE_COLUMN)));
                entity.setImage(cursor.getString(cursor.getColumnIndex(LINK_COLUMN)));
                entity.setGeneric(name.substring(name.indexOf("(") + 1, name.indexOf(")")));
                entity.setFavorite(cursor.getInt(cursor.getColumnIndex(LIKED_COLUMN)) > 0);
                entity.setType(SearchListAdapter.ITEM_TYPE_DRUG);
                data.add(entity);
            } while (cursor.moveToNext());
        }
        return data;
    }
}
