package programmer.smartrestaurant;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import programmer.smartrestaurant.Database.MyDBHelper;
import programmer.smartrestaurant.Model.Food;

public class FoodAddActivity extends AppCompatActivity {

    EditText name;
    EditText une;
    EditText turul;
    EditText hemjee;
    Button save;
    MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_add);
        init();
    }

    public void init(){
        myDBHelper = new MyDBHelper(this);
        name = (EditText)findViewById(R.id.foodNameEditText);
        une = (EditText)findViewById(R.id.uneEditText);
        turul = (EditText)findViewById(R.id.turulEditText);
        hemjee = (EditText)findViewById(R.id.hemjeeEditText);
        save = (Button)findViewById(R.id.addFoodButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nametext = name.getText().toString();
                String unetext = une.getText().toString();
                String turultext = turul.getText().toString();
                String hemjeetext = hemjee.getText().toString();

//                Cursor foods = myDBHelper.checkFood(nametext);

                myDBHelper.addFood(new Food(nametext, unetext, turultext, hemjeetext));
                Log.d("===ADD===","Амжилттай нэмлээ");

//                if (foods == null){
//                    Log.d("====ADD===","Өгөгдлийн сангийн query алдаатай байна.");
//                } else {
//                    startManagingCursor(foods);
//                    if (foods.getCount() > 0){
//                        Log.d("==ADD==","Бүртгэлтэй үг байна");
//                        stopManagingCursor(foods);
//                        foods.close();
//                    } else {
//                        myDBHelper.addFood(new Food(nametext, unetext, turultext, hemjeetext));
//                        Log.d("===ADD===","Амжилттай нэмлээ");
//                    }
//                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_add, menu);
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
