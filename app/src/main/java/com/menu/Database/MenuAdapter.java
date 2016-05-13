package com.menu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.menu.Model.Product;

import java.util.ArrayList;

/**
 * Created by Tortuvshin on 5/14/2016.
 */
public class MenuAdapter extends MyDBHelper{
    private static final String TAG = "===MenuAdapter===";

    public static final String TABLE_PRODUCT       = "product_main";
    public static final String PRODUCT_ID          = "id";
    public static final String PRODUCT_TITLE       = "title";
    public static final String PRODUCT_DESCRIPTION = "description";
    public static final String PRODUCT_RATING      = "rating";
    public static final String PRODUCT_COST        = "cost";
    public static final String PRODUCT_IMAGE       = "image";
    public static final String PRODUCT_TOTAL_COST  = "totalcost";
    public static final String PRODUCT_TOTAL_ORDER = "totalorder";

    public MenuAdapter(Context context) {
        super(context);
    }

    public long addDataToALLMenu(Product product){

        ContentValues values = new ContentValues();
        values.put(PRODUCT_TITLE, product.getTitle());
        values.put(PRODUCT_DESCRIPTION, product.getDescription());
        values.put(PRODUCT_COST, product.getCost());
        values.put(PRODUCT_IMAGE, product.getImage());
        values.put(PRODUCT_TOTAL_COST, product.getTotalCost());
        values.put(PRODUCT_TOTAL_ORDER, product.getTotalOrder());

        SQLiteDatabase db = this.getWritableDatabase();

        long newRowId;
        newRowId = db.insert(
                TABLE_PRODUCT,
                null,
                values);
        db.close();
        return newRowId;
    }

    public void clearDatabase() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, null, null);
        db.close();
    }

    public ArrayList<Product> provideAllDataInMenu(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Product> data = new ArrayList<Product>();

        String[] projection = {
                PRODUCT_ID,
                PRODUCT_TITLE,
                PRODUCT_DESCRIPTION,
                PRODUCT_COST,
                PRODUCT_IMAGE,
                PRODUCT_TOTAL_COST,
                PRODUCT_TOTAL_ORDER
        };

        String sortOrder =
                PRODUCT_TITLE + " ASC";

        Cursor c = db.query(
                TABLE_PRODUCT,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        while(c.moveToNext()){
            Product product = new Product(c.getString(1),c.getString(2),c.getDouble(3),c.getString(4),c.getDouble(5),c.getInt(6));
            data.add(product);
        };
        c.close();
        db.close();
        return data;
    }



}
