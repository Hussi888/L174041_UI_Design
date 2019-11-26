package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContact extends AppCompatActivity {

    EditText name;
    EditText phone;
    EditText email;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        name = findViewById(R.id.add_name);
        phone = findViewById(R.id.add_phone);
        email = findViewById(R.id.add_email);
        submit = findViewById(R.id.addContactBtn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact();
            }
        });
    }

    public void saveContact(){

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "contact").allowMainThreadQueries().build();

        Contact c = new Contact(name.getText().toString(), phone.getText().toString(),
                email.getText().toString(), null);
        db.contactDao().insertAll(c);
        finish();
    }
}
