package com.example.hello.safe_working;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Approval extends AppCompatActivity {
CheckBox ch;
Button b1;
EditText e1,e2;
DatabaseReference ref,ref1;
int pos;
String id;
int flag=0;
    Additional ad;
FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        database=FirebaseDatabase.getInstance();
        ch=(CheckBox)findViewById(R.id.check);
        e1=(EditText)findViewById(R.id.process);
        e2=(EditText)findViewById(R.id.processdate);
        b1=(Button)findViewById(R.id.submit);
        pos=getIntent().getExtras().getInt("no");
        id=getIntent().getStringExtra("id");
        ref1=database.getReference("Additional").child(id).child("complaint"+pos);

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ad=dataSnapshot.getValue(Additional.class);
                if(ad.getApproved().equals("0"))
                {
                    ad.setApproved("1");
                }
                else
                {
                    flag=1;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(true)
        {
            ch.setVisibility(View.GONE);
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true) {
                    ref1.setValue(ad);
                    ref = database.getReference("Timeline").child(id).child("complaint" + pos);
                    Timeline tl = new Timeline();
                    tl.setProcess(e1.getText().toString());
                    tl.setProcessdetail(e2.getText().toString());
                    ref.push().setValue(tl);
                    Intent intent1=new Intent(Approval.this,AdminDetails.class);
                    intent1.putExtra("complaint",pos);
                    intent1.putExtra("id",id);
                    startActivity(intent1);
                    finish();
                }
                else
                {
                    Toast.makeText(Approval.this, "Please Approve the complaint First!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Approval.this,AdminDetails.class);
        intent.putExtra("complaint",pos);
        intent.putExtra("id",id);
        startActivity(intent);
        finish();
    }
}
