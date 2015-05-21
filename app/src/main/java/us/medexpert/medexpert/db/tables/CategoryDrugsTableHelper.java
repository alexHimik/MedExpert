package us.medexpert.medexpert.db.tables;

import android.content.Context;
import android.database.Cursor;

import us.medexpert.medexpert.db.DataBaseHelper;

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

    public Cursor getAllCategoryDrugs(Context context, int categoryId) {
        DataBaseHelper helper = DataBaseHelper.getInstance(context);
        Cursor cursor = helper.getWritableDatabase().query(TABLE_NAME,
                new String[] {ID_COLUMN, CATEGORY_ID_COLUMN, IMAGE_COLUMN,
                        TITLE_COLUMN, LIKED_COLUMN, DESCRIPTION_COLUMN},
                CATEGORY_ID_COLUMN + "=?", new String[]{String.valueOf(categoryId)},
                TITLE_COLUMN, null, TITLE_COLUMN);
        return cursor;
    }
}
