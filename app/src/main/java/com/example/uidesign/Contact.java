package com.example.uidesign;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "phone")
    public String number;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "imageuri")
    public String imageURI;

    public Contact(String name, String number, String email, String imageURI) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.imageURI = imageURI;
    }

}
