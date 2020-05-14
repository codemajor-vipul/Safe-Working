package com.example.hello.safe_working;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hello.safe_working.Signup;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Addon extends AppCompatActivity {
 Button b1,b2,b3,b4,b5,b6;
 EditText e1,e2,e3,e4,e5;
 static String evidence ="";
 String str;
 private static int FILE_SELECT_CODE=1;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private StorageReference mStorageRef;
    static String flag="";
    ProgressBar mprogress;
    private Calendar mcal;
    private int day,month,year;
    DatabaseReference myref2;
     int i=0,f=0;
    NewRegister nr=new NewRegister();
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        setContentView(R.layout.activity_addon);
        mcal=Calendar.getInstance();
        //int f=0;
        mprogress=(ProgressBar)findViewById(R.id.progress);
        b1 = (Button) findViewById(R.id.sexual);
        b2 = (Button) findViewById(R.id.ip);
        b3 = (Button) findViewById(R.id.data);
        b4 = (Button) findViewById(R.id.whistle);
        b5 = (Button) findViewById(R.id.add);
        b6 = (Button) findViewById(R.id.done);
        e1=(EditText)findViewById(R.id.nameaccused);
        e2=(EditText)findViewById(R.id.calendar);
        e3=(EditText)findViewById(R.id.witness);
        e4=(EditText)findViewById(R.id.description);
        e5=(EditText)findViewById(R.id.evidence);
        final FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        myref2=database1.getReference("NewRegister").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]",""));
        myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nr=dataSnapshot.getValue(NewRegister.class);
                i=Integer.parseInt(nr.getNumber().toString().trim(),10);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag!="") {
                    if (ContextCompat.checkSelfPermission(Addon.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        execute();
                        //Toast.makeText(Addon.this, "permission already granted", Toast.LENGTH_SHORT).show();
                    } else {
                        requestpermission();
                    }
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String is1= "Sexual Harassment";
                //FirebaseDatabase database = FirebaseDatabase.getInstance();
                //Additional nr1=new Additional();
                flag="Sexual Harassment";
                //nr1.setComplainttype("Sexual Harassment");

                //DatabaseReference myRef = database.getReference("NewRegister").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]",""));
                //myRef.setValue(nr1);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String is1= "IP Theft";
                //FirebaseDatabase database = FirebaseDatabase.getInstance();
                //NewRegister nr1=new NewRegister();
                flag="IP Theft";
                //nr1.setComplainttype("IP Theft");
                //DatabaseReference myRef = database.getReference("Additional").child(nr1.getEmailid().replaceAll("[^a-zA-Z0-9]",""));
                //myRef.setValue(nr1);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String is1= "IP Theft";
                //FirebaseDatabase database = FirebaseDatabase.getInstance();
                flag="Data Theft";
                //NewRegister nr1=new NewRegister();
                //nr1.setComplainttype("Data Theft");
                //DatabaseReference myRef = database.getReference("NewRegister").child(nr1.getEmailid().replaceAll("[^a-zA-Z0-9]",""));
                //myRef.setValue(nr1);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String is1= "IP Theft";
                //FirebaseDatabase database = FirebaseDatabase.getInstance();
                //NewRegister nr1=new NewRegister();
                flag="Whistle Blower";
                //nr1.setComplainttype("Whistle Blower");
                //DatabaseReference myRef = database.getReference("NewRegister").child(nr1.getEmailid().replaceAll("[^a-zA-Z0-9]",""));
                //myRef.setValue(nr1);
            }
        });
        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = mcal.get(Calendar.YEAR);
                month = mcal.get(Calendar.MONTH);
                day= mcal.get(Calendar.DAY_OF_MONTH);
                dialog();
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Additional ad=new Additional();
                if(flag!="")
                {
                    ad.setCompalintType(flag);
                }
                if(e1.getText().toString()!=null)
                {
                    ad.setNameAccused(e1.getText().toString());
                }
                if(e3.getText().toString()!=null)
                {
                    ad.setWitness(e3.getText().toString());
                }
                if(e4.getText().toString()!=null)
                {
                    ad.setDescription(e4.getText().toString());
                }
                if(e2.getText().toString()!=null)
                {
                    ad.setDate(e2.getText().toString());
                }
                if(f==1)
                {
                    ad.setEvidence("Yes");
                }
                DatabaseReference myRef1;
                myRef1 = database1.getReference("Additional").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]", "")).child("complaint" +i);
                ad.setComplaintno(i+"");
                ad.setApproved("0");
                ad.setUid(FirebaseAuth.getInstance().getUid());
                i++;
                ad.setId(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]", ""));
                nr.setNumber(i+"");
                myref2.setValue(nr);
                ad.setName(nr.getFullname());
                ad.setEvidence_url(evidence.trim());
                myRef1.setValue(ad);
                Toast.makeText(Addon.this, "Complaint added successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Addon.this,HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void dialog()
    {
        Calendar cal=mcal;
        DatePickerDialog listener=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mcal.set(year,month,dayOfMonth,0,0);
                e2.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
            }
        },year,month,day);
        listener.show();
        //DatePickerDialog dp=new DatePickerDialog(this,listener,year,month,day);
    }
    private void requestpermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(Addon.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this).setTitle("Permission needed").setMessage("This permission is needed to upload the evidence")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(Addon.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        }else{
            ActivityCompat.requestPermissions(Addon.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==PERMISSION_REQUEST_CODE )
        {
            if(grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                execute();
            }
            else
            {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private  void execute()
    {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try{
            startActivityForResult(intent,FILE_SELECT_CODE);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "File manager not installed", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==FILE_SELECT_CODE && resultCode==RESULT_OK)
        {
            Uri file= data.getData();
            String str= file.toString();
            File file1=new File(str);
            String path1=null;
            InputStream is= null;
            try {
                is = getContentResolver().openInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if(str.startsWith("content://"))
            {
                Cursor cursor=null;
                try
                {
                    cursor=Addon.this.getContentResolver().query(file,null,null,null,null);
                    if(cursor!=null && cursor.moveToFirst())
                    {
                        path1=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                }finally {
                    cursor.close();
                }

            }else if(str.startsWith("file://"))
            {
                path1=file1.getName();
            }
            e5.setText(path1);
            final StorageReference riversRef = mStorageRef.child("image").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(flag).child("complaint"+i).child(path1);
            riversRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            evidence=downloadUrl.toString();

                            Toast.makeText(Addon.this, "successful", Toast.LENGTH_SHORT).show();
                            f=1;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            Toast.makeText(Addon.this, "unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress= (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                   mprogress.setProgress((int)progress);
                }
            });


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Intent intent5=new Intent(Addon.this,HomePage.class);
        startActivity(intent5);
        finish();
        //super.onBackPressed();
    }
}
