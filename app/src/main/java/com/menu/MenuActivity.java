package com.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.menu.Database.MenuAdapter;
import com.menu.Database.ProductAdapter;
import com.menu.Model.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    public final static String TAG = "com.menu";

    ProductAdapter productAdapter = new ProductAdapter(this);
    MenuAdapter menuAdapter = new MenuAdapter(this);
    ArrayList<Product> data;

    int currentCount = SplashScreen.currentCount;
    int currentLength;
    TextView menu_title;
    String title;
    TextView menu_cost;
    double cost;
    ImageView menu_image;
    String image_title;
    String description;
    int totalOrder;
    double totalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        boolean mTwoPane = false;
        data = menuAdapter.provideAllDataInMenu();
        currentLength = data.size();
        if (findViewById(R.id.description_text_view) != null) {
            mTwoPane = true;
        }else{
        }
        displayUpdate();
    }


    public void nextItem(View view) {
        ++currentCount;
        displayUpdate();
    }

    public void previousItem(View view) {
        currentCount = (--currentCount < 0) ? (currentLength - 1) : currentCount;
        displayUpdate();
    }

    public void displayUpdate() {
        menu_title = (TextView) findViewById(R.id.item_title);
        ;
        title = data.get(currentCount % currentLength).getTitle();
        menu_title.setText(title);

        menu_cost = menu_title = (TextView) findViewById(R.id.cost_text_view_menu);
        ;
        cost = data.get(currentCount % currentLength).getCost();
        menu_cost.setText("$ " + new DecimalFormat("#.##").format(cost));

        menu_image = (ImageView) findViewById(R.id.item_image);
        image_title = data.get(currentCount % currentLength).getImage();
        int resID = getResources().getIdentifier(image_title, "drawable", "com.menu");
        menu_image.setImageResource(resID);

        description = data.get(currentCount % currentLength).getDescription();
        totalCost = data.get(currentCount % currentLength).getTotalCost();
        totalOrder = data.get(currentCount % currentLength).getTotalOrder();
        SplashScreen.currentCount = currentCount;

    }

    public void selectItem(View view) {
        Intent intent = new Intent(this, MenuDetailActivity.class);
        String cost_string = "" + cost;
        String totalCost_string = "" + totalCost;
        String totalOrder_string = "" + totalOrder;
        String[] transfer_data = {title, description, cost_string, totalCost_string, totalOrder_string, image_title};
        intent.putExtra(TAG, transfer_data);
        startActivity(intent);
    }

    public void showListItem(View view) {
        if (productAdapter.readData().size() > 0) {
            Intent intent;
            intent = new Intent(this, ItemOrdered.class);
            startActivity(intent);
            productAdapter.close();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
