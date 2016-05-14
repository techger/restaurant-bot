package com.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.menu.Database.ProductAdapter;
import com.menu.Model.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {


    public final static String  TAG = "com.menu";
    final ProductAdapter productAdapter = new ProductAdapter(this);
    TextView subTotalView;
    TextView taxView;
    TextView totalView;
    double subTotal;
    double tax;
    double total;
    ArrayList<Product> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        data = productAdapter.readData();
        displayBill();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order, menu);
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


    public void calculateBill(){
        subTotal = tax = total = 0;
        for(Product dat : data){
            subTotal += dat.getTotalCost();
        }
        tax = subTotal*0.15;
        total = tax + subTotal;
    }

    public void displayBill(){
        calculateBill();
        subTotalView = (TextView) findViewById(R.id.sub_total_text_view);
        subTotalView.setText("Нийт дүн: $ " + new DecimalFormat("#.##").format(subTotal));

        taxView = (TextView) findViewById(R.id.tax_text_view);
        taxView.setText("Татвар: $ " + new DecimalFormat("#.##").format(tax));

        totalView = (TextView) findViewById(R.id.total_text_view);
        totalView.setText("Нийт: $ " + new DecimalFormat("#.##").format(total));
    }

    public void startPayment(View view){
        if (data.size() > 0){
            Intent intent = new Intent(this, PaymentActivity.class);
            String subTotalString = "" + subTotal;
            String taxString = "" + tax;
            String totalString = "" + total;
            String[] transferData = {subTotalString,taxString,totalString};
            intent.putExtra(TAG,transferData);
            startActivity(intent);
        }
    }
}
