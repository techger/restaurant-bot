package com.menu.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tortuvshin on 5/10/2016.
 */
public class FileUtil {


    public static void saveBitmap(Bitmap bitmap, String fileName)
    {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        File file = new File (myDir, fileName);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Bitmap getBitmap(String fileName)
    {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        Bitmap bitmap=null;

        File file = new File (myDir, fileName);

        if (file.exists ())
        {
            try {
                FileInputStream in = new FileInputStream(file);
                bitmap  = BitmapFactory.decodeStream(in);
                in.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }


    public static Drawable getImage(Context context, String imageName)
    {
        try {

            if(!imageName.startsWith("EXT_")) {
                InputStream ims = context.getAssets().open("images/" + imageName);
                Drawable d = Drawable.createFromStream(ims, null);
                return d;
            }
            else
            {
                Bitmap bm = getBitmap(imageName);
                Drawable d = new BitmapDrawable(context.getResources(), bm);
                return d;

            }

        }
        catch(IOException ex) {
            return null;
        }
    }

}
