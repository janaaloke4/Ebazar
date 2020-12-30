package com.example.ebazer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class passwordUpdate extends AppCompatActivity {

    database db;
    TextInputLayout in_pass,in_cpass;
    EditText pass,cpass;
    Button save;
   // String check_pass,check_cpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_update);

        db=new database(this);

        in_pass=(TextInputLayout)findViewById(R.id.in_pass);
        in_cpass=(TextInputLayout)findViewById(R.id.in_cpass);

        pass = (EditText) findViewById(R.id.pass);
        cpass = (EditText) findViewById(R.id.cpass);

        save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isValidpass( ) | !isValidConpass( ))
                    return;
                else{

                    String inPass = pass.getText().toString().trim();
                    String ph=getIntent().getStringExtra("vph");

                    boolean isInserted= db.updatePass(inPass,ph);
                    if(isInserted) {
                        Toast.makeText(getApplicationContext(), "sucessfully updated ,Now you can login with new password", Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),signin.class);
                        startActivity(i);
                    }else
                        Toast.makeText(getApplicationContext(),"Not updated sucessfully! try Again",Toast.LENGTH_LONG).show();


                }
            }
        });
    }


    protected boolean isValidpass( )
    {
        String inputPass=pass.getText().toString().trim();//trim() is used to avoid space
        int color= Color.BLUE;
        if(inputPass.isEmpty())
        {
            String s="Please provide a suitable password !";
            ForegroundColorSpan f=new ForegroundColorSpan(color);
            SpannableStringBuilder sb=new SpannableStringBuilder(s);
            sb.setSpan(f,0,s.length(),0);
            in_pass.setError(sb);//end code
            return false;
        }
        else {

            if(inputPass.length()<6)
            {
                //for set error color start code
                String s1="Password Must be more than 6 Character Long.";
                ForegroundColorSpan f1=new ForegroundColorSpan(color);
                SpannableStringBuilder sb1=new SpannableStringBuilder(s1);
                sb1.setSpan(f1,0,s1.length(),0);
                in_pass.setError(sb1);//end code
                return false;
            }
            else
            {
                in_pass.setError(null);
                return true;
            }
        }
    }

    protected boolean isValidConpass( )
    {
        String in_con_pass=cpass.getText().toString().trim();//trim() is used to avoid space
        int color= Color.BLUE;
        if(in_con_pass.isEmpty())
        {
            String s="Please verify your password !";
            ForegroundColorSpan f=new ForegroundColorSpan(color);
            SpannableStringBuilder sb=new SpannableStringBuilder(s);
            sb.setSpan(f,0,s.length(),0);
            in_cpass.setError(sb);//end code
            return false;
        }
        else {
            String s11=pass.getText().toString().trim();
            if(!in_con_pass.matches(s11))
            {
                in_cpass.setError("Password not match !");
                return false;
            }
            else
            {
                in_cpass.setError(null);
                return true;
            }
        }
    }



}
