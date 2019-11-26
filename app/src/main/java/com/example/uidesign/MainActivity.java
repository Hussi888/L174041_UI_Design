package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button addButton;
    Button deleteButton;
    Button getContactsButton;
    Button showContactsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addBtn);
        deleteButton = findViewById(R.id.deleteBtn);
        getContactsButton = findViewById(R.id.transferBtn);
        showContactsButton = findViewById(R.id.displayBtn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                startActivity(intent);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteContact.class);
                startActivity(intent);
            }
        });

        getContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });

        showContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void requestPermission(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS}, 1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        HashMap<String, String> emails = getEmails();
        HashMap<String, String> phones = getPhones();
        String[] PROJECTION = new String[] {
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null,
                null, null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " ASC");


        List<Contact> dataSet = new ArrayList<Contact>();
        assert cursor != null;
        cursor.moveToNext();
        while (cursor.moveToNext()) {
            int col =  cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI);
            String imageUri = cursor.getString(col);
            col = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String name = cursor.getString(col);
            String phone = phones.get(name);
            String email = emails.get(name);
            if(email == null)
                email = "Not Available";

            dataSet.add(new Contact(name, phone, email, imageUri));
        }
        ContactsSet.getInstance().setData(dataSet);
        saveToRoom(dataSet);
    }

    private void saveToRoom(List<Contact> dataSet) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "contact").allowMainThreadQueries().build();

        db.clearAllTables();
        Contact[] contacts = new Contact[dataSet.size()];
        dataSet.toArray(contacts);
        db.contactDao().insertAll(contacts);

    }

    private HashMap<String, String> getEmails() {

        HashMap<String, String> emails = new HashMap<>();

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null, null, null, null);
        assert cursor != null;
        while(cursor.moveToNext()){
            int col =  cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
            String email = cursor.getString(col);
            col = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String name = cursor.getString(col);
            emails.put(name, email);
        }

        return emails;
    }
    private HashMap<String, String> getPhones() {

        HashMap<String, String> phones = new HashMap<>();

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        assert cursor != null;
        while(cursor.moveToNext()){
            int col =  cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
            String phone = cursor.getString(col);
            col = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String name = cursor.getString(col);
            phones.put(name, phone);
        }

        return phones;
    }
}
