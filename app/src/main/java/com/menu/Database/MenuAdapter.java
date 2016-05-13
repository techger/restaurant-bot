package com.menu.Database;

import android.content.Context;

/**
 * Created by Programmer on 5/14/2016.
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

    private static final String[] PROJECTIONS_PRODUCT = {
            PRODUCT_ID,
            PRODUCT_TITLE,
            PRODUCT_DESCRIPTION,
            PRODUCT_RATING,
            PRODUCT_COST,
            PRODUCT_IMAGE,
            PRODUCT_TOTAL_COST,
            PRODUCT_TOTAL_ORDER
    };

    public static final int PRODUCT_ID_INDEX          = 0;
    public static final int PRODUCT_TITLE_INDEX       = 1;
    public static final int PRODUCT_DESCRIPTION_INDEX = 2;
    public static final int PRODUCT_RATING_INDEX      = 3;
    public static final int PRODUCT_COST_INDEX        = 4;
    public static final int PRODUCT_IMAGE_INDEX       = 5;
    public static final int PRODUCT_TOTAL_COST_INDEX  = 6;
    public static final int PRODUCT_TOTAL_ORDER_INDEX = 7;

    public MenuAdapter(Context context) {
        super(context);
    }
}
