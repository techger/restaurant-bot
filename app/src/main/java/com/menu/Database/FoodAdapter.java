package com.menu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.menu.Model.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tortuvshin on 5/11/2016.
 */
public class FoodAdapter extends MyDBHelper{

    private static final String TAG = "===FoodAdapter===";

    public static final String TABLE_FOODS   = "foods";
    public static final String FOOD_ID       = "id";
    public static final String FOOD_NAME     = "name";
    public static final String FOOD_UNE      = "une";
    public static final String FOOD_TURUL    = "turul";
    public static final String FOOD_HEMJEE   = "hemjee";
    public static final String FOOD_IMAGE    = "image";

    private static final String[] PROJECTIONS_FOODS = {FOOD_ID, FOOD_NAME , FOOD_UNE, FOOD_TURUL, FOOD_HEMJEE, FOOD_IMAGE};

    private static final int FOOD_ID_INDEX       = 0;
    private static final int FOOD_NAME_INDEX     = 1;
    private static final int FOOD_UNE_INDEX      = 2;
    private static final int FOOD_TURUL_INDEX    = 3;
    private static final int FOOD_HEMJEE_INDEX   = 4;
    private static final int FOOD_IMAGE_INDEX    = 5;

    public FoodAdapter(Context context) {
        super(context);
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
                cursor.getString(FOOD_HEMJEE_INDEX),
                cursor.getString(FOOD_IMAGE_INDEX));
        cursor.close();
        return food;
    }

    public Cursor checkFood(String name){

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_FOODS, new String[]{FOOD_ID, FOOD_NAME, FOOD_UNE, FOOD_TURUL, FOOD_HEMJEE},
                FOOD_NAME + "='" + name + "'", null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
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
                String image = cursor.getString(FOOD_IMAGE_INDEX);
                Food food = new Food(name,une,turul,hemjee, image);
                foods.add(food);
            } while (cursor.moveToNext());
        }

        Log.d(TAG, "" + foods);
        cursor.close();
        return foods;
    }
    public int updateFood(Food food) {
        if (food == null) {
            return -1;
        }
        SQLiteDatabase db = getWritableDatabase();
        if (db == null) {
            return -1;
        }
        ContentValues cv = new ContentValues();
        cv.put(FOOD_NAME, food.getName());
        cv.put(FOOD_UNE, food.getUne());
        cv.put(FOOD_TURUL,food.getTurul());
        cv.put(FOOD_HEMJEE, food.getHemjee());
        // Upating the row
        int rowCount = db.update(TABLE_FOODS, cv, FOOD_NAME + "=?",
                new String[]{String.valueOf(food.getName())});
        db.close();
        return rowCount;
    }
    public void deleteFood(Food food) {
        if (food == null) {
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        if (db == null) {
            return;
        }
        db.delete(TABLE_FOODS, FOOD_NAME + "=?", new String[]{String.valueOf(food.getName())});
        db.close();
    }

}
