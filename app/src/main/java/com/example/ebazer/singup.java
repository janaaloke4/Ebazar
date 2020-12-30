package com.example.ebazer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;


public class singup extends AppCompatActivity {

    database db;
    boolean namef=false,phf=false;

    CountryCodePicker ccp;
    EditText ph,name,email,pass,con_pass;
    TextView sign_in;
    Button verify;
    TextInputLayout e_ph,e_email,e_name,e_pass,e_con_pass;
   // FirebaseAuth fAuth;
    //String codeSent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        db=new database(this);



        ccp = (CountryCodePicker) findViewById(R.id.ccp);

        ph = (EditText) findViewById(R.id.ph);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.Password);
        con_pass = (EditText) findViewById(R.id.con_Password);

        sign_in= (TextView) findViewById(R.id.sign_in);
        verify= (Button) findViewById(R.id.verify);

     /*   fAuth=FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser()!= null)//check whether user is log in or not
        {
            startActivity(new Intent(getApplicationContext(),signin.class));
            finish();
        }*/

        e_ph=(TextInputLayout)findViewById(R.id.in_ph);
        e_name=(TextInputLayout)findViewById(R.id.in_name);
        e_email=(TextInputLayout)findViewById(R.id.in_email);
        e_pass=(TextInputLayout)findViewById(R.id.in_pass);
        e_con_pass=(TextInputLayout)findViewById(R.id.in_con_pass);



        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),signin.class);
                startActivity(i);
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isValiduser()| !isValidEmail() | !isValidpass( ) | !isValidConpass( ))
                    return;
                else {
                    //Toast.makeText(getApplicationContext(), "insert sucessfully", Toast.LENGTH_LONG).show();


                    String in_name = name.getText().toString().trim();
                    String in_ph = ph.getText().toString().trim();
                    String in_email = email.getText().toString().trim();
                    String in_pass = pass.getText().toString().trim();
                   //
                    // String in_com_pass = con_pass.getText().toString().trim();

                   // sentVerificationCode();

                    int in=91;
                    String p12="+" + in + in_ph;
                    boolean vaildPhone=db.checkPhone(p12);
                    if(!vaildPhone) {
                        Intent i = new Intent(getApplicationContext(), verification.class);
                        i.putExtra("vname", in_name);
                        i.putExtra("vph", p12);
                        i.putExtra("vemail", in_email);
                        i.putExtra("vpass", in_pass);
                        //   i.putExtra("vcd",codeSent);

                        startActivity(i);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Phone number you provide already exist! try with new number", Toast.LENGTH_LONG).show();
                        name.setText("");
                        ph.setText("");
                        email.setText("");
                        pass.setText("");
                        con_pass.setText("");

                    }

                }
            }
        });

    }





  /*  private void sentVerificationCode(){
        String phone=ph.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent =s;
        }
    };

*/

    protected boolean isValiduser()
    {

        String in_name=name.getText().toString().trim();//trim() is used to avoid space
        int color= Color.BLUE;
        if(in_name.isEmpty())
        {
             //for set error color start code
            String s="Name is required";
            ForegroundColorSpan f=new ForegroundColorSpan(color);
            SpannableStringBuilder sb=new SpannableStringBuilder(s);
            sb.setSpan(f,0,s.length(),0);
            e_name.setError(sb);//end code

            return false;
        }
        else {
            String s11="^[a-zA-Z\\s]+$";
            if(!in_name.matches(s11))
            {

                 //for set error color start code
                String s1="not a valid name formate! try again";
                ForegroundColorSpan f1=new ForegroundColorSpan(color);
                SpannableStringBuilder sb1=new SpannableStringBuilder(s1);
                sb1.setSpan(f1,0,s1.length(),0);
                e_name.setError(sb1);//end code
                return false;
            }
            else
            {
                e_name.setError(null);
                return true;
            }
        }
    }



    protected boolean isValidEmail( )
    {
        String in_email=email.getText().toString().trim();//trim() is used to avoid space
        int color= Color.BLUE;
        if(in_email.isEmpty())
        {
            String s="Email is required.";
            ForegroundColorSpan f=new ForegroundColorSpan(color);
            SpannableStringBuilder sb=new SpannableStringBuilder(s);
            sb.setSpan(f,0,s.length(),0);
            e_email.setError(sb);//end code
            return false;
        }
        else {
            String s11="^[a-zA-Z0-9_.]+[0-9]+@[A-Za-z]+[.]+[c][o][m]+$";
            if(!in_email.matches(s11))
            {
                //for set error color start code
                String s1="not a valid email! ex: abcd12@gmail.com";
                ForegroundColorSpan f1=new ForegroundColorSpan(color);
                SpannableStringBuilder sb1=new SpannableStringBuilder(s1);
                sb1.setSpan(f1,0,s1.length(),0);
                e_email.setError(sb1);//end code
                return false;
            }
            else
            {
                e_email.setError(null);
                return true;
            }
        }
    }

    protected boolean isValidpass( )
    {
        String in_pass=pass.getText().toString().trim();//trim() is used to avoid space
        int color= Color.BLUE;
        if(in_pass.isEmpty())
        {


            String s="Please provide a suitable password !";
            ForegroundColorSpan f=new ForegroundColorSpan(color);
            SpannableStringBuilder sb=new SpannableStringBuilder(s);
            sb.setSpan(f,0,s.length(),0);
            e_pass.setError(sb);//end code
            return false;
        }
        else {

            if(in_pass.length()<6)
            {
                //for set error color start code
                String s1="Password Must be more than 6 Character Long.";
                ForegroundColorSpan f1=new ForegroundColorSpan(color);
                SpannableStringBuilder sb1=new SpannableStringBuilder(s1);
                sb1.setSpan(f1,0,s1.length(),0);
                e_pass.setError(sb1);//end code
                return false;
            }
            else
            {
                e_pass.setError(null);
                return true;
            }
        }
    }


    protected boolean isValidConpass( )
    {
        String in_con_pass=con_pass.getText().toString().trim();//trim() is used to avoid space
        int color= Color.BLUE;
        if(in_con_pass.isEmpty())
        {
            String s="Please verify your password !";
            ForegroundColorSpan f=new ForegroundColorSpan(color);
            SpannableStringBuilder sb=new SpannableStringBuilder(s);
            sb.setSpan(f,0,s.length(),0);
            e_con_pass.setError(sb);//end code
            return false;
        }
        else {
            String s11=pass.getText().toString().trim();
            if(!in_con_pass.matches(s11))
            {
                e_con_pass.setError("Password not match !");
                return false;
            }
            else
            {
                e_con_pass.setError(null);
                return true;
            }
        }
    }

  /*  protected boolean isValidPh( ) {
        String in_ph = ph.getText().toString().trim();//trim() is used to avoid space
        int color = Color.BLUE;
        if (in_ph.isEmpty()) {
            String s = "Please verify your phone number !";
            ForegroundColorSpan f = new ForegroundColorSpan(color);
            SpannableStringBuilder sb = new SpannableStringBuilder(s);
            sb.setSpan(f, 0, s.length(), 0);
            e_ph.setError(sb);//end code
            return false;
        } else
            return true;

    }*/


}
