package com.menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.menu.Database.FoodAdapter;
import com.menu.Database.MyDBHelper;
import com.menu.Model.Food;


@Deprecated
public class FoodMenuActivity extends AppCompatActivity {

    ListView foodList;
    FoodAdapter foodAdapter;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);
        init();
    }

    public void init(){
        foodAdapter = new FoodAdapter(this);
        sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.PREFER_NAME, 0);
        editor = sharedPreferences.edit();
        foodList = (ListView)findViewById(R.id.foodListView);
        final ArrayList<String> foodListItems = new ArrayList<String>();
        final ArrayAdapter<String> myArrayAdapter;
        myArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,foodListItems);
        foodList.setAdapter(myArrayAdapter);

        List<Food> foods = foodAdapter.getAllFoods();

        try {
            Log.d("===FOODS===", "Inserting foods...");
            for (Food food : foods){
                String foodAdd = food.getName();
                Log.d("===FOOD VIEW===",foodAdd);
                foodListItems.add(0, foodAdd);
            }
        }catch (NullPointerException npe){
            Log.d("", "Алдаа : " + npe);
        }catch (Exception e){
            Log.d("","Алдаа : "+e);
        }
        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = parent.getItemAtPosition(position);
                final String foodName = o.toString();
                Log.d("", "Дарагдсан лист дээрх үг : " + foodName);
                editor.putString("SelectedFoodName", foodName);
                editor.commit();
                Intent intent = new Intent(FoodMenuActivity.this, FoodDetailActivity.class);
                startActivity(intent);
            }
        });
        myArrayAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_menu, menu);
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
