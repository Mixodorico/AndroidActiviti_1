package com.mixodorico.passaggioactivity_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        WebView myWebView = (WebView) findViewById(R.id.webview); // definizione della connessione internet
        myWebView.loadUrl("http://www.html.it");


    }
}
