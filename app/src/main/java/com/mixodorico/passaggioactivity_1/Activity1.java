package com.mixodorico.passaggioactivity_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Activity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        //getActionBar().setDisplayHomeAsUpEnabled(true); // occorre per inserire la freccia all'indietro. Basta pero' modificare il Manifest inserendo il parent
    }
}
