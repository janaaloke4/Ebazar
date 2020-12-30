package com.example.ebazer;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class forgot_pass extends AppCompatActivity {

    FirebaseAuth mAuth;
    database db;

    TextInputLayout inPh;
    EditText ph,otp;
    Button verify,conti;
    TextView oph,resentOtp;
    String in_ph,cd,code,p12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        db=new database(this);

        inPh=(TextInputLayout)findViewById(R.id.in_ph);

        ph=(EditText)findViewById(R.id.ph);
        otp=(EditText)findViewById(R.id.otp);

        verify=(Button)findViewById(R.id.verify);
        conti=(Button)findViewById(R.id.conti);

        oph=(TextView)findViewById(R.id.oph);
        resentOtp=(TextView)findViewById(R.id.resentOtp);

        mAuth=FirebaseAuth.getInstance();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in_ph=ph.getText().toString().trim();
                int in=91;
                p12="+" + in + in_ph;
                oph.setText(p12);
                boolean vaildPhone=db.checkPhone(p12);
                if(!vaildPhone) {

                    int color= Color.BLUE;
                    Toast.makeText(getApplicationContext(), "his phone number isn't register with Ebazer", Toast.LENGTH_LONG).show();
                    ph.setText("");
                    String s1="This phone number isn't register with Ebazer";
                    ForegroundColorSpan f1=new ForegroundColorSpan(color);
                    SpannableStringBuilder sb1=new SpannableStringBuilder(s1);
                    sb1.setSpan(f1,0,s1.length(),0);
                    inPh.setError(sb1);//end code

                }
                else
                {
                    sentVerificationCode(p12);
                }

            }
        });

        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code=otp.getText().toString().trim();
                verifySignCode(code);
            }
        });

        resentOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean vaildPhone=db.checkPhone(p12);
                if(!vaildPhone) {

                    int color= Color.BLUE;
                    Toast.makeText(getApplicationContext(), "This phone number isn't register with Ebazer", Toast.LENGTH_LONG).show();
                    ph.setText("");
                    String s1="This phone number isn't register with Ebazer";
                    ForegroundColorSpan f1=new ForegroundColorSpan(color);
                    SpannableStringBuilder sb1=new SpannableStringBuilder(s1);
                    sb1.setSpan(f1,0,s1.length(),0);
                    inPh.setError(sb1);//end code

                }
                else
                {
                    sentVerificationCode(p12);
                }
            }
        });



    }
    //End of onCreate() method


    private void sentVerificationCode(String ph){
        //String phone=oph.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                ph,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

          /*  String code=phoneAuthCredential.getSmsCode();
            if(code !=null)
                verifySignCode(code); */
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            cd =s;
        }
    };


    private void verifySignCode(String code){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(cd, code);
        signInWithCredential(credential);
    }


    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Log.d(TAG, "signInWithCredential:success");
                            // FirebaseUser user = task.getResult().getUser();
                            Intent i=new Intent(getApplicationContext(),passwordUpdate.class);
                            i.putExtra("vph", p12);
                            startActivity(i);
                            //Toast.makeText(getApplicationContext(), "insert sucessfully", Toast.LENGTH_LONG).show();

                        }
                        else {
                            // Sign in failed, display a message and update the UI
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getApplicationContext(), "Incorrect Verification Code", Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                });
    }


}//End of class
