package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    ImageView image;
    TextView name;
    TextView phone;
    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        image = findViewById(R.id.contact_image);
        name = findViewById(R.id.contact_name);
        phone = findViewById(R.id.contact_phone);
        email = findViewById(R.id.contact_mail);
        Intent intent = getIntent();
        String imageuri = intent.getStringExtra("imageuri");
        if(imageuri == null){
            Resources resources = getResources();
            imageuri = ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + resources.getResourcePackageName(R.drawable.default_profile)
                    + '/' + resources.getResourceTypeName(R.drawable.default_profile)
                    + '/' + resources.getResourceEntryName(R.drawable.default_profile);
        }
        image.setImageURI(Uri.parse(imageuri));
        name.setText(intent.getStringExtra("name"));
        phone.setText(intent.getStringExtra("phone"));
        email.setText(intent.getStringExtra("email"));
    }
}
