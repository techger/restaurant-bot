package com.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.menu.Database.ProductAdapter;
import com.menu.Model.Product;

import java.text.DecimalFormat;
import java.util.Random;

public class MenuDetailActivity extends AppCompatActivity {
    TextView menu_title_view;
    TextView description_view;
    TextView menu_cost_view;
    TextView total_order_view;
    TextView total_cost_view;
    String menu_title_string;
    String menu_description_string;
    String menu_cost_string;
    String totalCost_string;
    String totalOrder_string;
    double menu_cost_double;
    double toal_cost_double;
    int total_order;
    String image_title_string;
    private android.support.v7.widget.ShareActionProvider mShareActionProvider;
    RatingBar rating;
    double ratingValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        Intent intent = getIntent();

        menu_title_string = intent.getStringArrayExtra(MenuActivity.TAG)[0];
        menu_description_string = intent.getStringArrayExtra(MenuActivity.TAG)[1];
        menu_cost_string = intent.getStringArrayExtra(MenuActivity.TAG)[2];
        menu_cost_double = toal_cost_double = Double.parseDouble(menu_cost_string);
        totalCost_string = intent.getStringArrayExtra(MenuActivity.TAG)[3];
        toal_cost_double = Double.parseDouble(totalCost_string);
        totalOrder_string = intent.getStringArrayExtra(MenuActivity.TAG)[4];
        total_order = Integer.parseInt(totalOrder_string);
        image_title_string = intent.getStringArrayExtra(MenuActivity.TAG)[5];
        rating =(RatingBar)findViewById(R.id.ratingBar);
        displayRating();
        displayData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (android.support.v7.widget.ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
        if (menu_description_string != null && menu_cost_string != null && menu_title_string!=null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }
        return true;
    }


    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        String inentMsg = "Хэе, ?\n" + menu_title_string + "\n" + menu_description_string +".\n" + "Үнэ: $" + menu_cost_string;
        shareIntent.putExtra(Intent.EXTRA_TEXT, inentMsg);
        return shareIntent;
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


    public void increaseTotalCost(View view) {
        total_order++;
        toal_cost_double += menu_cost_double;
        displayUpdate();
    }

    public void decreaseTotalCost(View view) {
        total_order = (--total_order < 1) ? 1 : total_order;
        toal_cost_double -= menu_cost_double;
        toal_cost_double = (toal_cost_double < menu_cost_double) ? menu_cost_double : toal_cost_double;
        displayUpdate();
    }

    public void displayData() {
        menu_title_view = (TextView) findViewById(R.id.menu_title);

        menu_title_view.setText(menu_title_string);

        menu_cost_view = (TextView) findViewById(R.id.cost_text_view);
        menu_cost_view.setText("Үнэ: $ " + menu_cost_string);

        displayUpdate();
    }

    public void displayRating(){
        Random r = new Random();
        ratingValue = (r.nextDouble()*5.0);
        rating.setRating((float)ratingValue);
    }

    public void displayUpdate() {
        total_cost_view = (TextView) findViewById(R.id.total_cost_text_view);
        total_cost_view.setText("Нийт дүн: $ " + new DecimalFormat("#.##").format(toal_cost_double));

        total_order_view = (TextView) findViewById(R.id.total_item_number);
        total_order_view.setText("Нийт тоо: " + total_order);

        description_view = (TextView) findViewById(R.id.description_text_view);
        description_view.setText(menu_description_string);

    }

    public void addToList(View view) {
        ProductAdapter productAdapter = new ProductAdapter(this);;
        ratingValue = rating.getRating();
        Product item = new Product(menu_title_string, menu_description_string, menu_cost_double, image_title_string, toal_cost_double, total_order);
        item.setRatinng(ratingValue);
        productAdapter.addProduct(item);
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
