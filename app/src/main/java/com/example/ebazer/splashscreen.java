package com.example.ebazer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        SystemClock.sleep(3000);
        Intent i=new Intent(splashscreen.this,signin.class);
        startActivity(i);
        finish();

        Intent i1=new Intent(splashscreen.this,HomePage.class);
        startActivity(i1);
        finish();

    }


}
