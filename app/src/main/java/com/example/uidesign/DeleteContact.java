package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DeleteContact extends AppCompatActivity {

    EditText name;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_contact);

        name = findViewById(R.id.deleteName);
        submit = findViewById(R.id.delBtn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact();
            }
        });
    }

    private void deleteContact() {

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "contact").allowMainThreadQueries().build();
        db.contactDao().deleteByName(name.getText().toString());
    }
}
