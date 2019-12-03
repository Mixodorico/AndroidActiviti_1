package com.mixodorico.passaggioactivity_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;

    Context context;
    final int duration = Toast.LENGTH_SHORT;

    Intent cambiaActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext(); // il contesto va inizializzato all'interno dell'OnCreate

        button1 = (Button)findViewById(R.id.button);
        button1.setOnClickListener(simpleClick);
        button1.setOnLongClickListener(longClick);
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(simpleClickB2);
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(simpleClickB3);
    }

    //region Button1 Listeners

    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener simpleClick = new View.OnClickListener() {
        public void onClick(View v) {
            button1.setText(R.string.button1_name2);
            boolean enable = areEnabled();

            if(!enable) {
                button2.setVisibility(View.VISIBLE);
                button3.setVisibility(View.VISIBLE);
                }else{
                button2.setVisibility(View.GONE);
                button3.setVisibility(View.GONE);
            }
            button2.setEnabled(!enable);
            button3.setEnabled(!enable);
        }
    };

    // Create an anonymous implementation of OnLongClickListener
    private View.OnLongClickListener longClick = new View.OnLongClickListener(){
        public boolean onLongClick(View v){
            button1.setText("Premuto a Lungo");
            return true;
        }
    };
    //endregion

    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener simpleClickB2 = new View.OnClickListener() {
        public void onClick(View v) {
            CharSequence testoToast = context.getResources().getString(R.string.toast_button2); //R.string.toast_button2 restituisce "int"
            Toast.makeText(context, testoToast, duration).show();

            cambiaActivity = new Intent(context,Activity1.class);
            //cambiaActivity.putExtra("username", account_username);
            startActivity(cambiaActivity);

        }
    };

    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener simpleClickB3 = new View.OnClickListener() {
        public void onClick(View v) {
            CharSequence testoToast = context.getResources().getString(R.string.toast_button3); //R.string.toast_button2 restituisce "int"
            Toast.makeText(context, testoToast, duration).show();

            cambiaActivity = new Intent(context,Activity2.class);
            //cambiaActivity.putExtra("username", account_username);
            startActivity(cambiaActivity);

        }
    };


    private boolean areEnabled(){
        if(button2.isEnabled())   // posso controllare anche solo uno dei due
            return true;
        else
            return false;
    }
}
