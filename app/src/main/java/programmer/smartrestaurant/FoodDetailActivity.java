package programmer.smartrestaurant;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import programmer.smartrestaurant.Database.MyDBHelper;
import programmer.smartrestaurant.Model.Food;

public class FoodDetailActivity extends AppCompatActivity {

    TextView name;
    TextView une;
    TextView turul;
    TextView hemjee;

    String nameText   = "";
    String uneText    = "";
    String turulText  = "";
    String hemjeeText = "";

    MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        init();
    }

    public void init(){
        myDBHelper = new MyDBHelper(this);

        name = (TextView)findViewById(R.id.nameDetail);
        une = (TextView)findViewById(R.id.uneDetail);
        turul = (TextView)findViewById(R.id.turulDetail);
        hemjee = (TextView)findViewById(R.id.hemjeeDetail);

        SharedPreferences prefs = getSharedPreferences(FoodMenuActivity.PREFER_NAME, 0);
        String selectedFood = prefs.getString("SelectedFoodName", "");
        Log.d("","================================================================================"+selectedFood);
        Food food = myDBHelper.getFood(selectedFood);
        nameText = food.getName();
        uneText = food.getUne();
        turulText = food.getTurul();
        hemjeeText = food.getHemjee();

        name.setText(nameText);
        une.setText(uneText);
        turul.setText(turulText);
        hemjee.setText(hemjeeText);
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
