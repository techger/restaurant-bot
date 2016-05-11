package com.menu;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.menu.Database.FoodAdapter;
import com.menu.Database.MyDBHelper;
import com.menu.Model.Food;

public class FoodAddActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1002;

    EditText name;
    EditText une;
    EditText turul;
    EditText hemjee;
    Button save;
    FoodAdapter foodAdapter;
    Button pictureButton;
    ImageView picture;
    boolean hasBitmap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_add);
        init();
    }

    public void init(){
        foodAdapter = new FoodAdapter(this);
        name = (EditText)findViewById(R.id.foodNameEditText);
        une = (EditText)findViewById(R.id.uneEditText);
        turul = (EditText)findViewById(R.id.turulEditText);
        hemjee = (EditText)findViewById(R.id.hemjeeEditText);
        pictureButton = (Button) this.findViewById(R.id.addButtonSetPicture);
        picture = (ImageView) this.findViewById(R.id.addImage);

        pictureButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                     startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                 }
             }
         }
        );
        save = (Button)findViewById(R.id.addFoodButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nametext = name.getText().toString();
                String unetext = une.getText().toString();
                String turultext = turul.getText().toString();
                String hemjeetext = hemjee.getText().toString();

//                Cursor foods = myDBHelper.checkFood(nametext);

                foodAdapter.addFood(new Food(nametext, unetext, turultext, hemjeetext , hemjeetext));
                Log.d("===ADD===","Амжилттай нэмлээ");

//                if (foods == null){
//                    Log.d("====ADD===","Өгөгдлийн сангийн query алдаатай байна.");
//                } else {
//                    startManagingCursor(foods);
//                    if (foods.getCount() > 0){
//                        Log.d("==ADD==","Бүртгэлтэй хоол байна");
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
