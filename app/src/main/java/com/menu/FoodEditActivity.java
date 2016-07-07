package com.menu;

import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.menu.Database.FoodAdapter;
import com.menu.Database.MyDBHelper;
import com.menu.Model.Food;
@Deprecated
public class FoodEditActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    EditText name;
    EditText une;
    EditText turul;
    EditText hemjee;
    Button save;
    Button delete;

    FoodAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);
        init();
    }

    public void init(){

        foodAdapter = new FoodAdapter(this);

        sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.PREFER_NAME, 0);
        editor = sharedPreferences.edit();

        name   = (EditText)findViewById(R.id.foodNameEdit);
        une    = (EditText)findViewById(R.id.uneEdit);
        turul  = (EditText)findViewById(R.id.turulEdit);
        hemjee = (EditText)findViewById(R.id.hemjeeEdit);

        String nametext = sharedPreferences.getString("name", "");
        String unetext = sharedPreferences.getString("une", "");
        String turultext = sharedPreferences.getString("turul", "");
        String hemjeetext = sharedPreferences.getString("hemjee", "");


        Log.d("==============", ""+ nametext +"  "+unetext);
        name.setText(nametext);
        une.setText(unetext);
        turul.setText(turultext);
        hemjee.setText(hemjeetext);

        save = (Button)findViewById(R.id.editSaveButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nametext = name.getText().toString();
                String unetext = une.getText().toString();
                String turultext = turul.getText().toString();
                String hemjeetext = hemjee.getText().toString();

                foodAdapter.updateFood(new Food(nametext,unetext,turultext,hemjeetext,hemjeetext));
                Snackbar.make(v, "Үг амжилттай засагдлаа...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = name.getText().toString();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_edit, menu);
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
