package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uidesign.R;

public class LoginActivity extends AppCompatActivity {

    EditText user, pass;
    Button login;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Boolean saveLogin = false;
    CheckBox saveLoginChkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Shared Preference check
        checkPreferenceState();

        //View Initialised
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.Username);
        pass = (EditText) findViewById(R.id.Password);
        login = (Button) findViewById(R.id.Login);
        saveLoginChkBox = (CheckBox) findViewById(R.id.chkbox);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        //Set shared preference
        saveLogin = sharedpreferences.getBoolean("savelogin", true);
        if (saveLogin) {
            user.setText(sharedpreferences.getString("username", null));
            pass.setText(sharedpreferences.getString("password", null));
        }
    }
    public void login() {
        String username = user.getText().toString();
        String password = pass.getText().toString();
        if (validateLogin(username, password)) {
            if (saveLoginChkBox.isChecked()) {
                editor.putBoolean("savelogin", true);
                editor.putString("username", username);
                editor.putString("password", password);
                editor.apply();
                Toast.makeText(this,username + " is remembered.", Toast.LENGTH_LONG).show();
            }
            else {
                editor.putBoolean("savelogin", false);
                editor.apply();
            }
            moveToHome();
        }
    }
    public boolean validateLogin(String username, String password) {
        if (username.length()>=5 && password.length()>=5) {
            return true;
        }
        return false;
    }
    public void moveToHome() {
        Intent home = new Intent(getApplicationContext() , MainActivity.class);
        startActivity(home);
    }
    public void checkPreferenceState() {
        sharedpreferences = getSharedPreferences("loginref", MODE_PRIVATE);
        editor = sharedpreferences.edit();
        saveLogin = sharedpreferences.getBoolean("savelogin", true);
        if (saveLogin) {
            moveToHome();
        }
    }
}
