package com.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class PaymentActivity extends AppCompatActivity {

    TextView subTotalView;
    TextView taxView;
    TextView totalView;
    double subTotal;
    double tax;
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent intent = getIntent();
        String subTotalString = intent.getStringArrayExtra(MenuActivity.TAG)[0];
        subTotal = Double.parseDouble(subTotalString);
        String taxString = intent.getStringArrayExtra(MenuActivity.TAG)[1];
        tax = Double.parseDouble(taxString);
        String totalString = intent.getStringArrayExtra(MenuActivity.TAG)[2];
        total = Double.parseDouble(totalString);
        displayBill();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
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


    public void displayBill() {
        subTotalView = (TextView) findViewById(R.id.sub_total_text_view);
        subTotalView.setText("Дүн: $ " + new DecimalFormat("#.##").format(subTotal));

        taxView = (TextView) findViewById(R.id.tax_text_view);
        taxView.setText("Татвар: $ " + new DecimalFormat("#.##").format(tax));

        totalView = (TextView) findViewById(R.id.total_text_view);
        totalView.setText("Нийт: $ " + new DecimalFormat("#.##").format(total));
    }

    public void makePayment(View view) {
        boolean done = true;
        EditText customerNameView = (EditText) findViewById(R.id.customer_name);
        String customerName = customerNameView.getText().toString().trim();
        EditText customerAddressView = (EditText) findViewById(R.id.customer_address);
        String customerAddress = customerAddressView.getText().toString().trim();
        EditText customerCardNumberView = (EditText) findViewById(R.id.customer_card_number);
        String customerCardNumber = customerCardNumberView.getText().toString().trim();
        double customerCardNumberDouble;
        EditText customerSecurityCodeView = (EditText) findViewById(R.id.customer_security_code);
        String customerSecurityCode = customerSecurityCodeView.getText().toString().trim();
        double customerSecurityCodeDouble;
        try {
            if (customerCardNumber.length() == 8) {
                customerCardNumberDouble = Double.parseDouble(customerCardNumber);
            } else {
                customerCardNumberView.setText("");
                done = false;
            }
        } catch (NumberFormatException e) {
            customerCardNumberView.setText("");
            done = false;
        }
        try {
            if (customerSecurityCode.length() == 3 || customerSecurityCode.length() == 4) {
                customerSecurityCodeDouble = Double.parseDouble(customerSecurityCode);
            } else {
                customerSecurityCodeView.setText("");
                done = false;
            }
        } catch (NumberFormatException e) {
            customerSecurityCodeView.setText("");
            done = false;
        }
        if (done) {
            Intent intent = new Intent(this, CloseActivity.class);
            startActivity(intent);
        }
    }
}
