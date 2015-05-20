package us.medexpert.medexpert.db;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by user on 19.05.15.
 */
public class DataBaseHelper extends SQLiteAssetHelper {

    public static final String DRUG_DB_NAME = "med_expert.sqlite";
    public static final int DRUG_DB_VERSION = 1;

    private static DataBaseHelper instance;

    public static DataBaseHelper getInstance(Context context) {
        if(instance == null) {
            instance = new DataBaseHelper(context);
        }
        return instance;
    }

    private DataBaseHelper(Context context) {
        super(context, DRUG_DB_NAME, null, null, DRUG_DB_VERSION);
    }
}
