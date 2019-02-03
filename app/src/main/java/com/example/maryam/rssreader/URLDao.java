package com.example.maryam.rssreader;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.maryam.rssreader.Models.URLs;

import java.util.List;

@Dao
public interface URLDao {

    @Query("SELECT * FROM urls  ORDER BY id")
    LiveData<List<URLs>> loadAllUrl();

    @Insert
    void insertUrl(URLs url);

    @Delete
    void deleteUrl(URLs url);

}
