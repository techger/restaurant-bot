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
    private static final String DATABASE_NAME    = "restaurant2.db";

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


    public static final String PRODUCT_TITLE       = "title";
    public static final String PRODUCT_DESCRIPTION = "description";
    public static final String PRODUCT_RATING      = "rating";
    public static final String PRODUCT_COST        = "cost";
    public static final String PRODUCT_IMAGE       = "image";
    public static final String PRODUCT_TOTAL_COST  = "totalcost";
    public static final String PRODUCT_TOTAL_ORDER = "totalorder";

    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE "+ProductAdapter.TABLE_PRODUCT +" ("+
            ProductAdapter.PRODUCT_ID       + " INTEGER PRIMARY KEY," +
            ProductAdapter.PRODUCT_TITLE    + " TEXT,"+
            ProductAdapter.PRODUCT_DESCRIPTION    + " TEXT,"+
            ProductAdapter.PRODUCT_RATING    + " TEXT,"+
            ProductAdapter.PRODUCT_COST    + " TEXT,"+
            ProductAdapter.PRODUCT_IMAGE    + " TEXT,"+
            ProductAdapter.PRODUCT_TOTAL_COST    + " TEXT,"+
            ProductAdapter.PRODUCT_TOTAL_ORDER    + " TEXT,";

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

        ContentValues contentValues = new ContentValues();
        Resources resources = myContext.getResources();

        XmlResourceParser _xml = resources.getXml(R.xml.food);
        try
        {
            //Check for end of document
            int eventType = _xml.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //Search for record tags
                if ((eventType == XmlPullParser.START_TAG) &&(_xml.getName().equals("food"))){
                    //Record tag found, now get values and insert record
                    String name =   _xml.getAttributeValue(null, FoodAdapter.FOOD_NAME);
                    String une =    _xml.getAttributeValue(null, FoodAdapter.FOOD_UNE);
                    String turul =  _xml.getAttributeValue(null, FoodAdapter.FOOD_TURUL);
                    String hemjee = _xml.getAttributeValue(null, FoodAdapter.FOOD_HEMJEE);
                    contentValues.put(FoodAdapter.FOOD_NAME, name);
                    contentValues.put(FoodAdapter.FOOD_UNE, une);
                    contentValues.put(FoodAdapter.FOOD_TURUL, turul);
                    contentValues.put(FoodAdapter.FOOD_HEMJEE, hemjee);
                    db.insert(FoodAdapter.TABLE_FOODS, null, contentValues);
                    Log.d(TAG,"XML-ээс амжилттай уншлаа...");
                }
                eventType = _xml.next();
            }
        }
        //Catch errors
        catch (XmlPullParserException e)
        {
            Log.d(TAG, e.getMessage(), e);
        }
        catch (IOException e)
        {
            Log.d(TAG, e.getMessage(), e);

        }
        finally
        {
            //Close the xml file
            _xml.close();
        }

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
