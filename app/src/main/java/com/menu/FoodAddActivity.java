package com.menu;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.menu.Database.FoodAdapter;
import com.menu.Database.MyDBHelper;
import com.menu.Model.Food;

public class FoodAddActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1002;

    AlertDialogManager alert = new AlertDialogManager();
    EditText name;
    EditText une;
    Spinner turul;
    EditText hemjee;
    Button save;
    FoodAdapter foodAdapter;
    Button pictureButton;
    ImageView picture;
    Animation waveAnimation,shakeAnimation,myShakeAnimation;
    boolean hasBitmap = false;
    ArrayAdapter<CharSequence> adapter;
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
        turul = (Spinner)findViewById(R.id.turulEditText);
        adapter =ArrayAdapter.createFromResource(this,R.array.turul_names,android.R.layout.simple_expandable_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        turul.setAdapter(adapter);
        hemjee = (EditText)findViewById(R.id.hemjeeEditText);
        pictureButton = (Button) this.findViewById(R.id.addButtonSetPicture);
        picture = (ImageView) this.findViewById(R.id.addImage);

        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

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
                if (!validate()) {
                    return;
                }

                String nametext = name.getText().toString();
                String unetext = une.getText().toString();
                String turultext = turul.toString();
                String hemjeetext = hemjee.getText().toString();

//                Cursor foods = myDBHelper.checkFood(nametext);

                foodAdapter.addFood(new Food(nametext, unetext, turultext, hemjeetext, hemjeetext));
                Log.d("===ADD===", "Амжилттай нэмлээ");
                alert.showAlertDialog(FoodAddActivity.this, "    Амжилттай нэмэгдлээ ", "Хоол нэмэгдлээ...", true);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                finish();
                            }
                        }, 3000);

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

    public static boolean isNumericArray(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

    public boolean validate(){
        boolean valid = true;
        String nametext = name.getText().toString();
        String unetext = une.getText().toString();
        String turultext = turul.toString();
        String hemjeetext = hemjee.getText().toString();

        if(nametext.isEmpty()){
            name.startAnimation(shakeAnimation);
            name.setError("Хоосон байна !!");
            valid = false;
        }
        if(unetext.isEmpty() &&  isNumericArray(unetext)==false){
            une.startAnimation(shakeAnimation);
            une.setError("Буруу утга оруулсан байна !!");
            valid = false;
        }
        if(hemjeetext.isEmpty()&& isNumericArray(hemjeetext)==false){
            hemjee.startAnimation(shakeAnimation);
            hemjee.setError("Буруу утга оруулсан байна !!");
            valid = false;
        }


        return valid;
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
