package us.medexpert.medexpert.db.tables;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import us.medexpert.medexpert.adapter.CatalogFragmentListAdapter;
import us.medexpert.medexpert.db.DataBaseHelper;
import us.medexpert.medexpert.db.entity.Category;

/**
 * Created by user on 20.05.15.
 */
public class CategoryTableHelper {

    public static final String TABLE_NAME = "app_category";
    public static final String ID_CLOUMN = "_id";
    public static final String TITLE_COLUMN = "title";
    public static final String LINK_COLUMN = "link";
    public static final String VIEW_COUNT_COLUMN = "view_count";

    public List<Category> getAllCategories(Context context) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        List<Category> data = new ArrayList<>();
        Cursor cursor = helper.getWritableDatabase().query(TABLE_NAME,
                new String[] {ID_CLOUMN, TITLE_COLUMN, LINK_COLUMN},
                null, null, TITLE_COLUMN, null, TITLE_COLUMN);

        if(cursor.moveToFirst()) {
            Category allOne = new Category(cursor.getInt(cursor.getColumnIndex(CategoryTableHelper.ID_CLOUMN)),
                    cursor.getString(cursor.getColumnIndex(CategoryTableHelper.TITLE_COLUMN)),
                    CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM);
            data.add(allOne);
            while (cursor.moveToNext()) {
                Category all = new Category(cursor.getInt(cursor.getColumnIndex(CategoryTableHelper.ID_CLOUMN)),
                        cursor.getString(cursor.getColumnIndex(CategoryTableHelper.TITLE_COLUMN)),
                        CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM);
                data.add(all);
            }
        }
        return data;
    }

    public List<Category> getPopularCategories(Context context) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        List<Category> data = new ArrayList<>();
        Cursor cursor = helper.getWritableDatabase().query(TABLE_NAME,
                new String[] {ID_CLOUMN, TITLE_COLUMN, LINK_COLUMN},
                VIEW_COUNT_COLUMN + ">=?", new String[]{"1"}, TITLE_COLUMN, null, TITLE_COLUMN);

        if(cursor.moveToFirst()) {
            Category allOne = new Category(cursor.getInt(cursor.getColumnIndex(CategoryTableHelper.ID_CLOUMN)),
                    cursor.getString(cursor.getColumnIndex(CategoryTableHelper.TITLE_COLUMN)),
                    CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM);
            data.add(allOne);
            while (cursor.moveToNext()) {
                Category all = new Category(cursor.getInt(cursor.getColumnIndex(CategoryTableHelper.ID_CLOUMN)),
                        cursor.getString(cursor.getColumnIndex(CategoryTableHelper.TITLE_COLUMN)),
                        CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM);
                data.add(all);
            }
        }
        return data;
    }

    public void updateCategoryViewedCount(Context context, int categoryId) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        Cursor cursor = helper.getWritableDatabase().query(TABLE_NAME, new String[] {VIEW_COUNT_COLUMN},
                ID_CLOUMN + "=?", new String[] {String.valueOf(categoryId)}, null, null, null);
        cursor.moveToFirst();
        StringBuilder builder = new StringBuilder();
        builder.append("update ");
        builder.append(TABLE_NAME);
        builder.append(" set ");
        builder.append(VIEW_COUNT_COLUMN);
        builder.append("='");
        builder.append(cursor.getInt(cursor.getColumnIndex(VIEW_COUNT_COLUMN)) + 1);
        builder.append("' where ");
        builder.append(ID_CLOUMN);
        builder.append("=");
        builder.append(categoryId);
        builder.append(";");
        helper.getWritableDatabase().execSQL(builder.toString());
    }
}
