package com.menu;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.menu.Database.MyDBHelper;

/**
 * Created by ToRoo on 11/17/2015.
 */


public class LoginActivity extends ActionBarActivity {

    private static final String TAG = "LoginActivity";

    private static final int REQUEST_SIGNUP = 0;
    public static final String PREFER_NAME = "UserInfo";

    AlertDialogManager alert = new AlertDialogManager();

    private SharedPreferences sharedPreferences;

    Editor editor;
    EditText nameText;
    EditText passwordText;
    Button loginButton;
    TextView signUpLink;
    CheckBox rememberDetail;
    MyDBHelper myDBHelper;
    Animation shake;
    Animation myshake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameText = (EditText)findViewById(R.id.username);
        passwordText = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.login);
        signUpLink = (TextView)findViewById(R.id.signup);
        rememberDetail = (CheckBox)findViewById(R.id.rememberMe);
        shake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        myshake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.myshake);

        myDBHelper = new MyDBHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_top_to_bottom, R.anim.exit_slide_down);

            }
        });
        rememberDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RememberMe();
            }
        });
        SharedPreferences prefs = getSharedPreferences(PREFER_NAME, 0);
        String thisUsername = prefs.getString("Username", "");
        String thisPassword = prefs.getString("Password", "");
        boolean thisRemember = prefs.getBoolean("Remember", false);
        if(thisRemember) {
            nameText.setText(thisUsername);
            passwordText.setText(thisPassword);
            rememberDetail.setChecked(thisRemember);
        }
    }

    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        // TODO: Implement your own authentication logic here.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.exit)
                .setTitle("Ресторан")
                .setMessage("Та програмаас гарах уу?")
                .setPositiveButton("Тийм", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Үгүй", null)
                .show();
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        //Toast.makeText(getBaseContext(), "Хэрэглэгчийн мэдээллээ шинэчлээд дахин оролдно уу !", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }
    private void RememberMe() {
        boolean thisRemember = rememberDetail.isChecked();
        sharedPreferences = getSharedPreferences(PREFER_NAME, 0);
        editor = sharedPreferences.edit();
        editor.putBoolean("Remember", thisRemember);
        editor.commit();
    }
    public boolean validate() {
        boolean valid = true;
        String username = nameText.getText().toString();
        String password = passwordText.getText().toString();

        //password = PasswordEncryption(password);

        //Toast.makeText(getApplicationContext(), "Хэрэглэгч нэвтэрсэн байдал: " + session.isUserLoggedIn(), Toast.LENGTH_LONG).show();
        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        if (username.isEmpty() || password.length() < 3) {
            nameText.startAnimation(shake);
            nameText.setError("хэрэглэгчийн нэр 3-аас багагүй тэмдэгт байна");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 ) {
            passwordText.startAnimation(shake);
            passwordText.setError("нууц үг 4-өөс олон тэмдэгт байна");
            valid = false;
        } else {
            passwordText.setError(null);
        }
        if(username.trim().length() > 0 && password.trim().length() > 0){
            Cursor checkeduser = myDBHelper.checkUser(username,password);
            if(checkeduser != null){
                startManagingCursor(checkeduser);
                if (checkeduser.getCount() > 0){
                    saveLoggedInUser(checkeduser.getLong(checkeduser.getColumnIndex(myDBHelper.USER_ID)), username, password);
                    stopManagingCursor(checkeduser);
                    checkeduser.close();
                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();
                    // TODO: Implement your own authentication logic here.
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    progressDialog.dismiss();
                                }
                            }, 2000);
                    overridePendingTransition(0, R.anim.wave_scale);
                    valid = true;
                }
                else{
                    nameText.startAnimation(myshake);
                    passwordText.startAnimation(myshake);
                    saveLoggedInUser(0,"","");
                    valid = false;
                }
                stopManagingCursor(checkeduser);
                checkeduser.close();

            }else{
                Toast.makeText(getApplicationContext(),
                        "Database query error",
                        Toast.LENGTH_SHORT).show();
                nameText.startAnimation(myshake);
                passwordText.startAnimation(myshake);
                valid = false;
            }
        } else {
            Toast.makeText(LoginActivity.this,"Хэрэглэгчийн нэр нууц үгээ оруулна уу",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }
    private void saveLoggedInUser(long id, String username, String password) {
        SharedPreferences settings = getSharedPreferences(PREFER_NAME, 0);
        editor = settings.edit();
        editor.putLong("UserId", id);
        editor.putString("Username", username);
        editor.putString("Password", password);
        boolean rememberThis = rememberDetail.isChecked();
        editor.putBoolean("rememberThis", rememberThis);
        editor.commit();
    }
    private String PasswordEncryption(String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5"); digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            return s;
        }
    }
}