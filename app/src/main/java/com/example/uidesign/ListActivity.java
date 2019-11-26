package com.example.uidesign;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class ListActivity extends AppCompatActivity implements RecyclerViewClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "contact").allowMainThreadQueries().build();


        List<Contact> dataSet =  db.contactDao().getAll();

        mAdapter = new ContactsAdapter(dataSet, this, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void recyclerViewListClicked(Contact contact) {

        Intent i = new Intent(this, ContactActivity.class);
        i.putExtra("imageuri", contact.imageURI);
        i.putExtra("name", contact.name);
        i.putExtra("phone", contact.number);
        i.putExtra("email", contact.email);
        startActivity(i);
    }
}
