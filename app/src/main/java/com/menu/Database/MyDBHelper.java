package com.menu.Database;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.menu.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Byambaa on 10/25/2015.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    private Context myContext;

    private static final String TAG = "===DatabaseHandler===";
    private static final int    DATABASE_VERSION = 1;
    private static final String DATABASE_NAME    = "restaurant.db";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE users (" +
            UserAdapter.USER_ID       + " INTEGER PRIMARY KEY," +
            UserAdapter.USER_NAME     + " TEXT," +
            UserAdapter.USER_EMAIL    + " TEXT," +
            UserAdapter.USER_PASSWORD + " TEXT)";

    private static final String CREATE_TABLE_FOODS = "CREATE TABLE foods (" +
            FoodAdapter.FOOD_ID     +" INTEGER PRIMARY KEY,"+
            FoodAdapter.FOOD_NAME   + " TEXT," +
            FoodAdapter.FOOD_UNE    + " TEXT," +
            FoodAdapter.FOOD_TURUL  + " TEXT," +
            FoodAdapter.FOOD_HEMJEE + " TEXT," +
            FoodAdapter.FOOD_IMAGE  + " TEXT)";

    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE "+ProductAdapter.TABLE_PRODUCT +" ("+
            ProductAdapter.PRODUCT_ID       + " INTEGER PRIMARY KEY," +
            ProductAdapter.PRODUCT_TITLE    + " TEXT,"+
            ProductAdapter.PRODUCT_DESCRIPTION    + " TEXT,"+
            ProductAdapter.PRODUCT_RATING    + " TEXT,"+
            ProductAdapter.PRODUCT_COST    + " TEXT,"+
            ProductAdapter.PRODUCT_IMAGE    + " TEXT,"+
            ProductAdapter.PRODUCT_TOTAL_COST    + " TEXT,"+
            ProductAdapter.PRODUCT_TOTAL_ORDER    + " TEXT)";

    private static final String CREATE_TABLE_PRODUCT_MAIN = "CREATE TABLE "+MenuAdapter.TABLE_PRODUCT +" ("+
            MenuAdapter.PRODUCT_ID       + " INTEGER PRIMARY KEY," +
            MenuAdapter.PRODUCT_TITLE    + " TEXT,"+
            MenuAdapter.PRODUCT_DESCRIPTION    + " TEXT,"+
            MenuAdapter.PRODUCT_RATING    + " TEXT,"+
            MenuAdapter.PRODUCT_COST    + " TEXT,"+
            MenuAdapter.PRODUCT_IMAGE    + " TEXT,"+
            MenuAdapter.PRODUCT_TOTAL_COST    + " TEXT,"+
            MenuAdapter.PRODUCT_TOTAL_ORDER    + " TEXT)";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_FOODS);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_PRODUCT_MAIN);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(UserAdapter.TABLE_USERS);
        dropTable(FoodAdapter.TABLE_FOODS);
        dropTable(ProductAdapter.TABLE_PRODUCT);
        onCreate(db);
    }
    public void dropTable(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        if (db == null || TextUtils.isEmpty(tableName)) {
            return;
        }
        db.execSQL("DROP TABLE IF EXISTS " + UserAdapter.TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + FoodAdapter.TABLE_FOODS);
        db.execSQL("DROP TABLE IF EXISTS " + ProductAdapter.TABLE_PRODUCT);
    }

}
