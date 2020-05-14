package com.example.hello.safe_working;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    private static TextView t1,t2;
    private static EditText e1,e2;
    private static CheckBox ch;
    private static Button b1;
    FirebaseDatabase database;
    DatabaseReference ref;
    int flag=0;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        t1=(TextView)findViewById(R.id.signup);
        t2=(TextView)findViewById(R.id.forgot_password);
        e1=(EditText)findViewById(R.id.login_emailid);
        e2=(EditText)findViewById(R.id.login_password);
        b1=(Button)findViewById(R.id.loginBtn);
        ch=(CheckBox)findViewById(R.id.show_hide_password);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent9=new Intent(MainActivity.this,Forgot.class);
                    startActivity(intent9);
                    finish();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(MainActivity.this,Signup.class);
                startActivity(intent2);
                finish();
            }
        });
        setListeners();
    }
    private void setListeners()
    {
        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button,
                                         boolean isChecked) {


                if (isChecked) {

                    ch.setText(R.string.hide_pwd);
                    e2.setInputType(InputType.TYPE_CLASS_TEXT);
                    e2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    ch.setText(R.string.show_pwd);
                    e2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    e2.setTransformationMethod(PasswordTransformationMethod.getInstance());// hide password

                }
            }
        });
    }
    private void checkValidation(){
        String getEmailId = e1.getText().toString();
        String getPassword = e2.getText().toString();
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            Toast.makeText(this, "Enter both credentials.", Toast.LENGTH_SHORT).show();
        }
        else if (!m.find())
            Toast.makeText(this, "Your Email Id is Invalid.", Toast.LENGTH_SHORT).show();
        else
        {
            Login();
        }
    }
 private void Login()
 {
     mAuth.signInWithEmailAndPassword(e1.getText().toString().trim(), e2.getText().toString().trim())
             .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()) {
                         // Sign in success, update UI with the signed-in user's information
                         Log.d("TAG", "signInWithEmail:success");
                         FirebaseUser user = mAuth.getCurrentUser();
                        ref=database.getReference("NewRegister").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]", ""));
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                NewRegister nr=dataSnapshot.getValue(NewRegister.class);
                                if(nr.getAdminflag().equals("0"))
                                {
                                    Intent intent1=new Intent(MainActivity.this,HomePage.class);
                                    startActivity(intent1);
                                    finish();
                                }
                                else
                                {
                                    Intent intent1=new Intent(MainActivity.this,Admin.class);
                                    startActivity(intent1);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                     } else {
                         // If sign in fails, display a message to the user.
                        // Log.w(TAG, "signInWithEmail:failure", task.getException());
                         Toast.makeText(MainActivity.this, "Authentication failed.",
                                 Toast.LENGTH_SHORT).show();
                         //updateUI(null);
                     }

                     // ...
                 }
             });

 }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        /*FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        if(currentUser!=null){
            ref=database.getReference("NewRegister").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]", ""));
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    NewRegister nr=dataSnapshot.getValue(NewRegister.class);
                    if(nr.getAdminflag().equals("0"))
                    {
                        Intent intent1=new Intent(MainActivity.this,HomePage.class);
                        startActivity(intent1);
                        finish();
                    }
                    else
                    {
                        Intent intent1=new Intent(MainActivity.this,Admin.class);
                        startActivity(intent1);
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });}*/
    }
}
