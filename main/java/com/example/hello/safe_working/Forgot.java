package com.example.hello.safe_working;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity {
 EditText e1;
 Button b1;
 FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        firebaseAuth=FirebaseAuth.getInstance();
        e1=(EditText)findViewById(R.id.forgottext);
        b1=(Button)findViewById(R.id.sendemail);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e1.getText().toString().trim().equals(""))
                {
                    Toast.makeText(Forgot.this, "Please enter email", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String str=e1.getText().toString().trim();
                    firebaseAuth.sendPasswordResetEmail(str).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(Forgot.this, "Success", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(Forgot.this, "no sent", Toast.LENGTH_SHORT).show();
                                        }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Forgot.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
