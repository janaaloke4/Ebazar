package com.example.ebazer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verification extends AppCompatActivity {

    database db;
    TextView n,phn,e,p,cs;
    TextView change,oph;
    EditText getCode;
    Button sub,resentOtp;
    String cd,na,ph,eml,pas;
    String code;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        db=new database(this);

        n=(TextView)findViewById(R.id.n);
        phn=(TextView)findViewById(R.id.phn);
        e=(TextView)findViewById(R.id.e);
        p=(TextView)findViewById(R.id.p);
        cs=(TextView)findViewById(R.id.cs);

        change=(TextView)findViewById(R.id.change);
        oph=(TextView)findViewById(R.id.oph);

        sub=(Button) findViewById(R.id.submit);
        resentOtp=(Button) findViewById(R.id.resentOtp);

        getCode=(EditText) findViewById(R.id.code);

        mAuth=FirebaseAuth.getInstance();

              na=getIntent().getStringExtra("vname");
        n.setText(na);
               ph=getIntent().getStringExtra("vph");
        oph.setText(ph);
        phn.setText(ph);
         eml=getIntent().getStringExtra("vemail");
        e.setText(eml);
         pas=getIntent().getStringExtra("vpass");
        p.setText(pas);
              cd=getIntent().getStringExtra("vcd");
        cs.setText(cd);


        sentVerificationCode(ph);


        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code=getCode.getText().toString().trim();
                verifySignCode(code);


                    boolean isInserted= db.insertData(na,ph,eml,pas);
                    if(isInserted) {
                        Toast.makeText(getApplicationContext(), "sucessfully Register ,Now you can login", Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),signin.class);
                        startActivity(i);
                    }else
                        Toast.makeText(getApplicationContext(),"Not Register sucessfully! try Again",Toast.LENGTH_LONG).show();

            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),singup.class);
                startActivity(i);
            }
        });

        resentOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sentVerificationCode(ph);
            }
        });


    }





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
                            Intent i=new Intent(getApplicationContext(),signin.class);
                            startActivity(i);
                            Toast.makeText(getApplicationContext(), "insert sucessfully", Toast.LENGTH_LONG).show();

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





}
