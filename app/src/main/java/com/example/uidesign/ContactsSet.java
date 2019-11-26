package com.example.uidesign;

import java.util.List;

public class ContactsSet {
    private static final ContactsSet ourInstance = new ContactsSet();
    private List<Contact> dataSet;
    public static ContactsSet getInstance() {
        return ourInstance;
    }

    private ContactsSet() {

    }

    public void setData(List<Contact> dataSet) {
        this.dataSet = dataSet;
    }

    public List<Contact> getData(){
        return dataSet;
    }
}
