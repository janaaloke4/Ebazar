package com.example.ebazer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    database db=new database(this);
    TextView display;
    Button signout;
    String eml;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        display=(TextView) findViewById(R.id.dis);
        signout=(Button) findViewById(R.id.signout);


        eml=getIntent().getStringExtra("vemail");

       // Toast.makeText(getApplicationContext(),"EMAIL="+eml,Toast.LENGTH_LONG).show();
       Cursor c= db.getAllData(eml);

        StringBuilder st=new StringBuilder();

            while (c.moveToNext()) {
                st.append("Id:       " + c.getString(0) + "\n");
                st.append("userName: " + c.getString(1) + "\n");
                st.append("Phone No: " + c.getString(2) + "\n");
                st.append("Email:    " + c.getString(3) + "\n");
                st.append("password: " + c.getString(4) + "\n\n\n");

            }
            display.setText(st.toString());





        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),signin.class);
                startActivity(i);
                kept_login kb1=new kept_login(getApplicationContext());
                kb1.removeUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        kept_login kb=new kept_login(getApplicationContext());
        kb.firsttime();
    }



}


