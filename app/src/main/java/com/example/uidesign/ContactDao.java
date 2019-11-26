package com.example.uidesign;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact ORDER BY name")
    List<Contact> getAll();
    @Insert
    void insertAll(Contact... users);

    @Query("DELETE FROM contact WHERE name LIKE :username")
    void deleteByName(String username);
    @Delete
    void delete(Contact user);

}
