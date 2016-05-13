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
    TextView sub_total_view;
    TextView tax_view;
    TextView total_view;
    double sub_total;
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
        sub_total = tax = total = 0;
        for(Product dat : data){
            sub_total += dat.getTotalCost();
        }
        tax = sub_total*0.15;
        total = tax + sub_total;
    }

    public void displayBill(){
        calculateBill();
        sub_total_view = (TextView) findViewById(R.id.sub_total_text_view);
        sub_total_view.setText("Нийт дүн: $ " + new DecimalFormat("#.##").format(sub_total));

        tax_view = (TextView) findViewById(R.id.tax_text_view);
        tax_view.setText("Дүн: $ " + new DecimalFormat("#.##").format(tax));

        total_view = (TextView) findViewById(R.id.total_text_view);
        total_view.setText("Нийт: $ " + new DecimalFormat("#.##").format(total));
    }

    public void startPayment(View view){
        if (data.size() > 0){
            Intent intent = new Intent(this, PaymentActivity.class);
            String subTotal_string = "" + sub_total;
            String tax_string = "" + tax;
            String total_string = "" + total;
            String[] transfer_data = {subTotal_string,tax_string,total_string};
            intent.putExtra(TAG,transfer_data);
            startActivity(intent);
        }
    }
}
