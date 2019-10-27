package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String myPrefs = "MyPrefs" ;
    public static final String userName = "nameKey";
    public static final String password = "phoneKey";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText user_name = findViewById(R.id.Email); //Usrname
        final EditText Password = findViewById(R.id.Password); //Password
        Button submitBtn = findViewById(R.id.Login);

        sharedpreferences = getSharedPreferences(myPrefs, Context.MODE_PRIVATE);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n  = user_name.getText().toString();
                String p  = Password.getText().toString();

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(userName, n);
                editor.putString(password, p);
                editor.commit();
                Toast.makeText(MainActivity.this,"You have Logged In",Toast.LENGTH_LONG).show();
            }
        });
    }
}
