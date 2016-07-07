package com.menu;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
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
import com.menu.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Deprecated
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
                if (!validate()){
                    return;
                }
                String nametext = name.getText().toString();
                String unetext = une.getText().toString();
                String turultext = turul.getText().toString();
                String hemjeetext = hemjee.getText().toString();

//                Cursor foods = myDBHelper.checkFood(nametext);

                String fileName = "EXT_Image" + toString();

                foodAdapter.addFood(new Food(nametext, unetext, turultext, hemjeetext, fileName));

                setResult(Activity.RESULT_OK, null);
                BitmapDrawable drawable = (BitmapDrawable) picture.getDrawable();
                FileUtil.saveBitmap(drawable.getBitmap(), fileName);

                Log.d("===ADD===", "Амжилттай нэмлээ");
                Log.e("added","" + nametext +" "+  unetext +" "+ turultext +" "+ hemjeetext +" "+  fileName);

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

    boolean validate (){
        String nametext = name.getText().toString();
        String unetext = une.getText().toString();
        String turultext = turul.getText().toString();
        String hemjeetext = hemjee.getText().toString();

        if (nametext.isEmpty() && unetext.isEmpty() && turultext.isEmpty() && hemjeetext.isEmpty() && hasBitmap){
            return false;
        }
        return true;
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            picture.setImageBitmap(imageBitmap);
//            hasBitmap=true;
//
//        }
//    }
//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");

            String partFilename = currentDateFormat();
            storeCameraPhotoInSDCard(bitmap, partFilename);

            // display the image from SD Card to ImageView Control
            String storeFilename = "photo_" + partFilename + ".jpg";
            Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
            picture.setImageBitmap(mBitmap);
        }
    }

    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private void storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate){
        File outputFile = new File(Environment.getExternalStorageDirectory(), "photo_" + currentDate + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Bitmap getImageFileFromSDCard(String filename){
        Bitmap bitmap = null;
        File imageFile = new File(Environment.getExternalStorageDirectory() + filename);
        try {
            FileInputStream fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
