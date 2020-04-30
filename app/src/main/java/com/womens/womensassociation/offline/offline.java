package com.womens.womensassociation.offline;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class offline extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
