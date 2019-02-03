package com.example.maryam.rssreader;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.maryam.rssreader.Models.AppDatabase;
import com.example.maryam.rssreader.Models.URLs;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private LiveData<List<URLs>> urls;

    public ViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        urls = database.urlsDao().loadAllUrl();
    }

    public LiveData<List<URLs>> getUrls() {
        return urls;
    }
}
