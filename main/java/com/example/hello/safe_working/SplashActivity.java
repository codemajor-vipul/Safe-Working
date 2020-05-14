package com.example.hello.safe_working;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {
 FirebaseDatabase database;
 DatabaseReference ref;
 FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                //updateUI(currentUser);
                if(currentUser!=null){
                    ref=database.getReference("NewRegister").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]", ""));
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            NewRegister nr=dataSnapshot.getValue(NewRegister.class);
                            if(nr.getAdminflag().equals("0"))
                            {
                                Intent intent1=new Intent(SplashActivity.this,HomePage.class);
                                startActivity(intent1);
                                finish();
                            }
                            else
                            {
                                Intent intent1=new Intent(SplashActivity.this,Admin.class);
                                startActivity(intent1);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },4000);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
