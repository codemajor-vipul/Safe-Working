package com.example.hello.safe_working;

import android.content.Intent;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
    private static EditText fullName, emailId, mobileNumber,password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox admin;
    private final String passforadmin="5667788";
    private static int flag=0;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        fullName = (EditText)findViewById(R.id.fullName);
        emailId = (EditText)findViewById(R.id.userEmailId);
        mobileNumber = (EditText)findViewById(R.id.mobileNumber);
        password = (EditText)findViewById(R.id.password);
        confirmPassword = (EditText)findViewById(R.id.confirmPassword);
        signUpButton = (Button)findViewById(R.id.signUpBtn);
        login = (TextView)findViewById(R.id.already_user);
        admin= (CheckBox)findViewById(R.id.terms_conditions);
        setListeners();
    }
    public void setListeners()
    {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Signup.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        admin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button,
                                         boolean isChecked) {


                if (isChecked) {

                    EditText editText=(EditText)findViewById(R.id.confirmadminPassword);
                    editText.setVisibility(View.VISIBLE);

                } else {


                    EditText editText=(EditText)findViewById(R.id.confirmadminPassword);
                    editText.setVisibility(View.GONE);
                }
            }
        });
    }

    private void checkValidation() {

        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)

            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();

            // Check if email id valid or not
        else if (!m.find())
            Toast.makeText(this, "Your Email Id is Invalid.", Toast.LENGTH_SHORT).show();
            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            Toast.makeText(this, "Both password doesn't match.", Toast.LENGTH_SHORT).show();

            // Make sure user should check Terms and Conditions checkbox

        else if(admin.isChecked())
        {


            EditText editText=(EditText)findViewById(R.id.confirmadminPassword);
            if(editText.getText().toString().equals(passforadmin))
            {
                flag=1;
                Login();
            }
            else
            {
                flag=0;
                Toast.makeText(this, "Admin password not matched", Toast.LENGTH_SHORT).show();
            }

        }
            // Else do signup or do your stuff
        else {
            Login();
        }

    }
    public void Login()
    {

        if(admin.isChecked() && flag==1)
        {
            mAuth.createUserWithEmailAndPassword(emailId.getText().toString().trim(), password.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                NewRegister nr=new NewRegister();
                                nr.setEmailid(emailId.getText().toString().trim());
                                nr.setFullname(fullName.getText().toString().trim());
                                nr.setMobileno(mobileNumber.getText().toString().trim());
                                nr.setAdminflag("1");
                                nr.setNumber("0");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("NewRegister").child(nr.getEmailid().replaceAll("[^a-zA-Z0-9]",""));
                                myRef.setValue(nr);
                                //FirebaseUser users=mAuth.getCurrentUser();
                                //UserProfileChangeRequest profile=new UserProfileChangeRequest().Builder().setDisplayName(nr.getFullname());
                                //users.updateProfile(profile);
                                Intent intent=new Intent(Signup.this,Admin.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Signup.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });


        }
        if(!admin.isChecked())
        {
            mAuth.createUserWithEmailAndPassword(emailId.getText().toString().trim(), password.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");

                                FirebaseUser user = mAuth.getCurrentUser();
                                NewRegister nr=new NewRegister();
                                nr.setEmailid(emailId.getText().toString().trim());
                                nr.setFullname(fullName.getText().toString().trim());
                                nr.setMobileno(mobileNumber.getText().toString().trim());
                                nr.setAdminflag("0");
                                nr.setNumber("0");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("NewRegister").child(nr.getEmailid().replaceAll("[^a-zA-Z0-9]",""));
                                myRef.setValue(nr);
                                Intent intent=new Intent(Signup.this,HomePage.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Signup.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }
    }
}
