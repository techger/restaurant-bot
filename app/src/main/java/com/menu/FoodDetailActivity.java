package com.menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.menu.Database.FoodAdapter;
import com.menu.Database.MyDBHelper;
import com.menu.Model.Food;
@Deprecated
public class FoodDetailActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TextView name;
    TextView une;
    TextView turul;
    TextView hemjee;
    Button edit;

    String nameText   = "";
    String uneText    = "";
    String turulText  = "";
    String hemjeeText = "";

    FoodAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        init();
    }

    public void init(){
        foodAdapter = new FoodAdapter(this);

        name = (TextView)findViewById(R.id.nameDetail);
        une = (TextView)findViewById(R.id.uneDetail);
        turul = (TextView)findViewById(R.id.turulDetail);
        hemjee = (TextView)findViewById(R.id.hemjeeDetail);
        edit = (Button)findViewById(R.id.editButton);

        sharedPreferences = getSharedPreferences(MainActivity.PREFER_NAME, 0);
        String selectedFood = sharedPreferences.getString("SelectedFoodName", "");
        editor = sharedPreferences.edit();
        Log.d("","================================================================================"+selectedFood);
        Food food = foodAdapter.getFood(selectedFood);
        nameText = food.getName();
        uneText = food.getUne();
        turulText = food.getTurul();
        hemjeeText = food.getHemjee();

        name.setText(nameText);
        une.setText(uneText);
        turul.setText(turulText);
        hemjee.setText(hemjeeText);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("name", name.getText().toString());
                editor.putString("une", une.getText().toString());
                editor.putString("turul", turul.getText().toString());
                editor.putString("hemjee", hemjee.getText().toString());
                editor.commit();
                Intent intent = new Intent(FoodDetailActivity.this, FoodEditActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_detail, menu);
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
