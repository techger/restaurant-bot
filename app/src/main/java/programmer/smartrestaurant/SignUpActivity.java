package programmer.smartrestaurant;

/**
 * Created by ToRoo on 11/17/2015.
 */
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import programmer.smartrestaurant.Database.MyDBHelper;
import programmer.smartrestaurant.Model.User;


public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    AlertDialogManager alert = new AlertDialogManager();
    MyDBHelper myDBHelper;
    public SharedPreferences sharedPreferences;
    Editor editor;
    EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    Button signUpButton;
    TextView loginLink;
    Animation waveAnimation,shakeAnimation,myShakeAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        myDBHelper = new MyDBHelper(this);

        nameEditText = (EditText)findViewById(R.id.sName);
        emailEditText = (EditText)findViewById(R.id.sEmail);
        passwordEditText = (EditText)findViewById(R.id.sPassword);
        confirmPasswordEditText = (EditText)findViewById(R.id.sComfirmPassword);
        signUpButton = (Button)findViewById(R.id.btnSignUp);
        loginLink = (TextView)findViewById(R.id.linkLogin);

        waveAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.wave_scale);
        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        myShakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.myshake);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();

            }
        });
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        if (!validate()) {
            onSignupFailed();
            return;
        }
        signUpButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Бүртгэл үүсгэж байна...");
        progressDialog.show();

        // TODO: Implement your own signup logic here.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupSuccess() {
        signUpButton.setEnabled(true);
        setResult(RESULT_OK, null);
        alert.showAlertDialog(SignUpActivity.this, "    Амжилттай бүртгэгдлээ.", "Таны бүртгэл амжилттай боллоо...", true);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        finish();
                    }
                }, 3000);
        Log.d(TAG, "Бүртгэл амжилттай боллоо...");
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        myDBHelper.addUser(new User(name, email, password));


        List<User> userInfo = myDBHelper.getAllUsers();

        for (User users : userInfo){
            String log = "ID:"+users.getUserId() + "\nNAME:"+users.getUserName()+
                    "\nEMAIL:"+users.getUserEmail()+"\nPASSWORD:"+users.getUserPassword();
            Log.d(TAG,log);
        }

    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Бүртгүүлэхэд алдаа гарлаа", Toast.LENGTH_LONG).show();
        signUpButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        sharedPreferences = getSharedPreferences(LoginActivity.PREFER_NAME, 0);
        editor = sharedPreferences.edit();
        editor.putLong("UserId",0);
        editor.commit();

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameEditText.startAnimation(shakeAnimation);
            nameEditText.setError("хамгийн багадаа 3 тэмдэгт");
            valid = false;
        } else {
            nameEditText.setError(null);
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.startAnimation(shakeAnimation);
            emailEditText.setError("И-мэйл хаягаа зөв оруулна уу");
            valid = false;
        } else {
            emailEditText.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 17) {
            passwordEditText.startAnimation(shakeAnimation);
            passwordEditText.setError("нууц үг 4-өөс 16 тэмдэгт");
            passwordEditText.setText("");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }
        if (!password.equals(confirmPassword)){
            confirmPasswordEditText.startAnimation(shakeAnimation);
            confirmPasswordEditText.setError("Нууц үг таарахгүй байна");
            valid = false;
        }
        else{
            confirmPasswordEditText.setError(null);
        }

        //password = PasswordHashes(password);

        Cursor user = myDBHelper.checkUser(name,password);

        if(user == null){
            alert.showAlertDialog(SignUpActivity.this,"Error","Database query error",false);
            valid = false;
        }
        else{
            startManagingCursor(user);

            if (user.getCount() > 0){
                alert.showAlertDialog(SignUpActivity.this,"Алдаа","Бүртгэлтэй хэрэглэгч байна",false);
                stopManagingCursor(user);
                user.close();
                valid = false;
            }
        }


        return valid;
    }
    private void saveLoggedInUser(long id, String username, String password) {
        sharedPreferences = getSharedPreferences(LoginActivity.PREFER_NAME, 0);
        editor = sharedPreferences.edit();
        editor.putLong("UserId", id);
        editor.putString("Username", username);
        editor.putString("Password", password);
        editor.commit();
    }
    /**
     * Hashes the password with MD5.
     * @param s
     * @return
     */
    private String PasswordHashes(String s) {
        try {

            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            return s;
        }
    }
}