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

import com.menu.Database.MenuAdapter;
import com.menu.Database.ProductAdapter;
import com.menu.Model.Product;

import java.text.DecimalFormat;

public class MenuEditActivity extends AppCompatActivity {

    TextView menuTitleView;
    TextView descriptionView;
    TextView menuCostView;
    TextView totalOrderView;
    TextView totalCostView;
    String menuTitleString;
    String menuDescriptionString;
    String menuCostString;
    String totalCostString;
    String totalOrderString;
    double menuCostDouble;
    double totalCostDouble;
    int totalOrder;
    String imageTitleString;
    String ratingString;
    double rating;
    RatingBar ratingBar;
    MenuAdapter menuAdapter;
    ProductAdapter productAdapter;
    private android.support.v7.widget.ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_edit);
        Intent intent = getIntent();
        menuTitleString = intent.getStringArrayExtra(OrderFragment.TAG)[0];
        menuDescriptionString = intent.getStringArrayExtra(OrderFragment.TAG)[1];
        menuCostString =  intent.getStringArrayExtra(OrderFragment.TAG)[2];
        menuCostDouble = totalCostDouble = Double.parseDouble(menuCostString);
        totalCostString =  intent.getStringArrayExtra(OrderFragment.TAG)[3];
        totalCostDouble = Double.parseDouble(totalCostString);
        totalOrderString =  intent.getStringArrayExtra(OrderFragment.TAG)[4];
        totalOrder = Integer.parseInt(totalOrderString);
        imageTitleString = intent.getStringArrayExtra(OrderFragment.TAG)[5];
        ratingString = intent.getStringArrayExtra(OrderFragment.TAG)[6];
        rating = Double.parseDouble(ratingString);
        displayData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_edit, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (android.support.v7.widget.ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
        if (menuDescriptionString != null && menuCostString != null && menuTitleString !=null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }
        return true;
    }
    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        String inentMsg = "Мэдээлэл: "+ menuTitleString + "\n" + menuDescriptionString +".\n" + "Үнэ: $" + menuCostString;
        shareIntent.putExtra(Intent.EXTRA_TEXT, inentMsg);
        return shareIntent;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item cli   cks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void increaseTotalCost(View view){
        totalOrder++;
        totalCostDouble += menuCostDouble;
        displayUpdate();
    }

    public void decreaseTotalCost(View view){
        totalOrder = (--totalOrder < 1) ? 1 : totalOrder;
        totalCostDouble -= menuCostDouble;
        totalCostDouble = (totalCostDouble < menuCostDouble) ? menuCostDouble : totalCostDouble;
        displayUpdate();
    }

    public void displayData(){
        menuTitleView = (TextView) findViewById(R.id.menu_title);;
        menuTitleView.setText(menuTitleString);

        menuCostView = (TextView) findViewById(R.id.cost_text_view);
        menuCostView.setText("Үнэ: $ " + menuCostString);

        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        ratingBar.setRating((float)rating);

        displayUpdate();
    }

    public void displayUpdate(){
        totalCostView = (TextView) findViewById(R.id.total_cost_text_view);
        totalCostView.setText("Нийт үнэ: $ " + new DecimalFormat("#.##").format(totalCostDouble));

        totalOrderView = (TextView) findViewById(R.id.total_item_number);
        totalOrderView.setText("Тоо: " + totalOrder);

        descriptionView = (TextView) findViewById(R.id.description_text_view);;
        descriptionView.setText(menuDescriptionString);
    }

    public void addToList(View view){

        ProductAdapter productAdapter = new ProductAdapter(this);
        Product item = new Product(menuTitleString, menuDescriptionString, menuCostDouble, imageTitleString, totalCostDouble, totalOrder);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        rating = ratingBar.getRating();
        item.setRatinng(rating);
        productAdapter.addProduct(item);
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    public void deleteFromList(View view){
        TextView menuTitle = (TextView) findViewById(R.id.menu_title);
        String title = (String) menuTitle.getText();
        productAdapter = new ProductAdapter(this);
        productAdapter.deleteProduct(title);
        if (productAdapter.readData().size() >0){
            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }

    }

}
