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

    TextView sub_total_view;
    TextView tax_view;
    TextView total_view;
    double subTotal;
    double tax;
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent intent = getIntent();
        String subTotal_string = intent.getStringArrayExtra(MenuActivity.TAG)[0];
        subTotal = Double.parseDouble(subTotal_string);
        String tax_string = intent.getStringArrayExtra(MenuActivity.TAG)[1];
        tax = Double.parseDouble(tax_string);
        String total_string = intent.getStringArrayExtra(MenuActivity.TAG)[2];
        total = Double.parseDouble(total_string);
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
        sub_total_view = (TextView) findViewById(R.id.sub_total_text_view);
        sub_total_view.setText("Дүн: $ " + new DecimalFormat("#.##").format(subTotal));

        tax_view = (TextView) findViewById(R.id.tax_text_view);
        tax_view.setText("Татвар: $ " + new DecimalFormat("#.##").format(tax));

        total_view = (TextView) findViewById(R.id.total_text_view);
        total_view.setText("Нийт: $ " + new DecimalFormat("#.##").format(total));
    }

    public void makePayment(View view) {
        boolean done = true;
        EditText customer_name_view = (EditText) findViewById(R.id.customer_name);
        String customer_name = customer_name_view.getText().toString().trim();
        EditText customer_address_view = (EditText) findViewById(R.id.customer_address);
        String customer_address = customer_address_view.getText().toString().trim();
        EditText customer_card_number_view = (EditText) findViewById(R.id.customer_card_number);
        String customer_card_number = customer_card_number_view.getText().toString().trim();
        double customer_card_number_double;
        EditText customer_security_code_view = (EditText) findViewById(R.id.customer_security_code);
        String customer_security_code = customer_security_code_view.getText().toString().trim();
        double customer_security_code_double;
        try {
            if (customer_card_number.length() == 8) {
                customer_card_number_double = Double.parseDouble(customer_card_number);
            } else {
                customer_card_number_view.setText("");
                done = false;
            }
        } catch (NumberFormatException e) {
            customer_card_number_view.setText("");
            done = false;
        }
        try {
            if (customer_security_code.length() == 3 || customer_security_code.length() == 4) {
                customer_security_code_double = Double.parseDouble(customer_security_code);
            } else {
                customer_security_code_view.setText("");
                done = false;
            }
        } catch (NumberFormatException e) {
            customer_security_code_view.setText("");
            done = false;
        }
        if (done) {
            Intent intent = new Intent(this, CloseActivity.class);
            startActivity(intent);
        }
    }
}
