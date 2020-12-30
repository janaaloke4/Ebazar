package com.example.ebazer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class signin extends AppCompatActivity {

    database db;
    TextView singup,forgetPass;
    Button login;
    EditText pass,email;
    TextInputLayout e_email,e_pass;
    //showProgress(true);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=new database(this);
        setContentView(R.layout.activity_signin);
        singup=(TextView) findViewById(R.id.sign_up);
        forgetPass=(TextView) findViewById(R.id.forgetPass);
        login=(Button) findViewById(R.id.login);

        e_email=(TextInputLayout) findViewById(R.id.userid);
        e_pass=(TextInputLayout) findViewById(R.id.pass);

        pass=(EditText)findViewById(R.id.Password);
        email=(EditText)findViewById(R.id.email);

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open=new Intent(getApplicationContext(),singup.class);
                startActivity(open);
            }
        });


        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),forgot_pass.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( !isValidEmail() | !isValidpass( ) )
                    return;
                String e=email.getText().toString();
                String p=pass.getText().toString();
                boolean isValidEmail= db.checkEmail(e);
                if(isValidEmail)
                {
                    String in_email = email.getText().toString().trim();
                    boolean b=db.checkLogIn(e,p);
                    if(b)
                    {
                        Intent i=new Intent(getApplicationContext(),HomePage.class);
                        i.putExtra("vemail", in_email);
                        startActivity(i);

                        kept_login kp=new kept_login(getApplicationContext());
                        i.putExtra("vemail", in_email);
                        kp.secondTime();
                    }
                    else
                    {
                        int color= Color.BLUE;
                        String s="Your password is incorrect !";
                        ForegroundColorSpan f=new ForegroundColorSpan(color);
                        SpannableStringBuilder sb=new SpannableStringBuilder(s);
                        sb.setSpan(f,0,s.length(),0);
                        e_pass.setError(sb);//end code
                    }

                }
                else
                {
                    int color= Color.BLUE;
                    //for set error color start code
                    String s="We can't find an account with that email address !";
                    ForegroundColorSpan f=new ForegroundColorSpan(color);
                    SpannableStringBuilder sb=new SpannableStringBuilder(s);
                    sb.setSpan(f,0,s.length(),0);
                    e_email.setError(sb);//end code
                }


            }
        });



    }

    protected boolean isValidEmail( ) {
        String in_email = email.getText().toString().trim();//trim() is used to avoid space
        int color = Color.BLUE;
        if (in_email.isEmpty()) {
            String s = "Email is required.";
            ForegroundColorSpan f = new ForegroundColorSpan(color);
            SpannableStringBuilder sb = new SpannableStringBuilder(s);
            sb.setSpan(f, 0, s.length(), 0);
            e_email.setError(sb);//end code
            return false;
        }
        else
            return true;
    }

    protected boolean isValidpass( ) {
        String in_pass = pass.getText().toString().trim();//trim() is used to avoid space
        int color = Color.BLUE;
        if (in_pass.isEmpty()) {


            String s = "Please provide a suitable password !";
            ForegroundColorSpan f = new ForegroundColorSpan(color);
            SpannableStringBuilder sb = new SpannableStringBuilder(s);
            sb.setSpan(f, 0, s.length(), 0);
            e_pass.setError(sb);//end code
            return false;
        }
        else
            return true;
    }
}
