package programmer.smartrestaurant.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import programmer.smartrestaurant.Model.Food;
import programmer.smartrestaurant.Model.User;
import programmer.smartrestaurant.R;

/**
 * Created by Byambaa on 10/25/2015.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    private Context myContext;

    private static final String TAG = "===DatabaseHandler===";
    private static final int    DATABASE_VERSION = 1;
    private static final String DATABASE_NAME    = "restaurant2.db";

    public static final String TABLE_USERS   = "users";
    public static final String USER_ID       = "id";
    public static final String USER_NAME     = "name";
    public static final String USER_EMAIL    = "email";
    public static final String USER_PASSWORD = "password";

    public static final String TABLE_FOODS   = "foods";
    public static final String FOOD_ID       = "id";
    public static final String FOOD_NAME     = "name";
    public static final String FOOD_UNE      = "une";
    public static final String FOOD_TURUL    = "turul";
    public static final String FOOD_HEMJEE   = "hemjee";

    private static final String[] PROJECTIONS_USERS = {USER_ID, USER_NAME, USER_EMAIL,USER_PASSWORD};
    private static final String[] PROJECTIONS_FOODS = {FOOD_ID, FOOD_NAME , FOOD_UNE, FOOD_TURUL, FOOD_HEMJEE};

    private static final int USER_ID_INDEX       = 0;
    private static final int USER_NAME_INDEX     = 1;
    private static final int USER_EMAIL_INDEX    = 2;
    private static final int USER_PASSWORD_INDEX = 3;

    private static final int FOOD_ID_INDEX       = 0;
    private static final int FOOD_NAME_INDEX     = 1;
    private static final int FOOD_UNE_INDEX      = 2;
    private static final int FOOD_TURUL_INDEX    = 3;
    private static final int FOOD_HEMJEE_INDEX   = 4;




    private static final String CREATE_TABLE_USERS = "CREATE TABLE users (" +
            USER_ID       + " INTEGER PRIMARY KEY," +
            USER_NAME     + " TEXT," +
            USER_EMAIL    + " TEXT," +
            USER_PASSWORD + " TEXT)";

    private static final String CREATE_TABLE_FOODS = "CREATE TABLE foods (" +
            FOOD_ID     +" INTEGER PRIMARY KEY,"+
            FOOD_NAME   + " TEXT," +
            FOOD_UNE    + " TEXT," +
            FOOD_TURUL  + " TEXT," +
            FOOD_HEMJEE + " TEXT)";


    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_FOODS);

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
                    String name =   _xml.getAttributeValue(null, FOOD_NAME);
                    String une =    _xml.getAttributeValue(null, FOOD_UNE);
                    String turul =  _xml.getAttributeValue(null, FOOD_TURUL);
                    String hemjee = _xml.getAttributeValue(null, FOOD_HEMJEE);
                    contentValues.put(FOOD_NAME, name);
                    contentValues.put(FOOD_UNE, une);
                    contentValues.put(FOOD_TURUL, turul);
                    contentValues.put(FOOD_HEMJEE, hemjee);
                    db.insert(TABLE_FOODS, null, contentValues);
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
        dropTable(TABLE_USERS);
        dropTable(TABLE_FOODS);
        onCreate(db);
    }
    public void addUser(User user) {
        if (user == null) {
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        if (db == null) {
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME, user.getUserName());
        cv.put(USER_EMAIL, user.getUserEmail());
        cv.put(USER_PASSWORD, user.getUserPassword());
        // Inserting Row
        db.insert(TABLE_USERS, null, cv);
        db.close();
    }


    public void addFood(Food food){
        if (food == null) {
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        if (db == null) {
            return;
        }
        ContentValues cv = new ContentValues();


        cv.put(FOOD_NAME, food.getName());
        cv.put(FOOD_UNE, food.getUne());
        cv.put(FOOD_TURUL, food.getTurul());
        cv.put(FOOD_HEMJEE, food.getHemjee());

        db.insert(TABLE_FOODS, null, cv);
        db.close();
    }

    public User getUser(int id) {
        SQLiteDatabase db = getReadableDatabase();
        if (db == null) {
            return null;
        }
        Cursor cursor = db.query(TABLE_USERS, PROJECTIONS_USERS, USER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        User user = new User(cursor.getInt(USER_ID_INDEX),
                cursor.getString(USER_NAME_INDEX),
                cursor.getString(USER_EMAIL_INDEX),
                cursor.getString(USER_PASSWORD_INDEX));
        cursor.close();
        return user;
    }
    public Food getFood(String name) {
        SQLiteDatabase db = getReadableDatabase();
        if (db == null) {
            return null;
        }
        Cursor cursor = db.query(TABLE_FOODS, PROJECTIONS_FOODS, FOOD_NAME + "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        Food food = new Food(
                cursor.getString(FOOD_NAME_INDEX),
                cursor.getString(FOOD_UNE_INDEX),
                cursor.getString(FOOD_TURUL_INDEX),
                cursor.getString(FOOD_HEMJEE_INDEX));
        cursor.close();
        return food;
    }

    public Cursor checkUser(String username,String password){

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD},
                USER_NAME + "='" + username + "' AND " +
                        USER_PASSWORD + "='" + password + "'", null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor checkFood(String name){

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_FOODS, new String[]{FOOD_ID, FOOD_NAME, FOOD_UNE, FOOD_TURUL, FOOD_HEMJEE},
                FOOD_NAME + "='" + name +  "'", null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(USER_ID_INDEX);
                String name = cursor.getString(USER_NAME_INDEX);
                String email = cursor.getString(USER_EMAIL_INDEX);
                String password = cursor.getString(USER_PASSWORD_INDEX);
                User user = new User(id, name, email, password);
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    public List<Food> getAllFoods(){
        List<Food> foods = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FOODS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(FOOD_NAME_INDEX);
                String une = cursor.getString(FOOD_UNE_INDEX);
                String turul = cursor.getString(FOOD_TURUL_INDEX);
                String hemjee = cursor.getString(FOOD_HEMJEE_INDEX);
                Food food = new Food(name,une,turul,hemjee);
                foods.add(food);
            } while (cursor.moveToNext());
        }

        Log.d(TAG,""+foods);
        cursor.close();
        return foods;

    }
    public int updateUser(User user) {
        if (user == null) {
            return -1;
        }
        SQLiteDatabase db = getWritableDatabase();
        if (db == null) {
            return -1;
        }
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME, user.getUserName());
        cv.put(USER_EMAIL, user.getUserEmail());
        cv.put(USER_PASSWORD,user.getUserPassword());
        // Upating the row
        int rowCount = db.update(TABLE_USERS, cv, USER_ID + "=?",
                new String[]{String.valueOf(user.getUserId())});
        db.close();
        return rowCount;
    }

    public void deleteUser(User user) {
        if (user == null) {
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        if (db == null) {
            return;
        }
        db.delete(TABLE_USERS, USER_ID + "=?", new String[]{String.valueOf(user.getUserId())});
        db.close();
    }

    public int getUserCount() {
        String query = "SELECT * FROM  " + TABLE_USERS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public void dropTable(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        if (db == null || TextUtils.isEmpty(tableName)) {
            return;
        }
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    }
}
