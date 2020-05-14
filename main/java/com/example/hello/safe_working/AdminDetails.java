package com.example.hello.safe_working;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminDetails extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5;
    static int flag=0;
    Button b1,b2;
    FirebaseDatabase database;
    DatabaseReference ref;
    int pos;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_details);
        database=FirebaseDatabase.getInstance();
        t1=(TextView)findViewById(R.id.typead);
        t2=(TextView)findViewById(R.id.namead);
        t3=(TextView)findViewById(R.id.datead);
        t4=(TextView)findViewById(R.id.witness1ad);
        t5=(TextView)findViewById(R.id.descriptad);
        b1=(Button)findViewById(R.id.evidence1ad);
        b2=(Button)findViewById(R.id.approval);
        pos=getIntent().getExtras().getInt("complaint");
        id=getIntent().getStringExtra("id");
        ref=database.getReference("Additional").child(id).child("complaint"+pos);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Additional ad=dataSnapshot.getValue(Additional.class);
                t1.setText(ad.getCompalintType());
                t2.setText(ad.getNameAccused());
                t3.setText(ad.getDate());
                t4.setText(ad.getWitness());
                t5.setText(ad.getDescription());
                //Glide.with(img.getContext()).load(ad.getEvidence_url()).into(img);
                ImageView imageView=(ImageView)findViewById(R.id.giant_image);
                Glide.with(imageView.getContext()).load(ad.getEvidence_url()).into(imageView);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout linearLayout=(LinearLayout)findViewById(R.id.giant_linear_layout);
                if(flag==0) {
                    ImageView imageView=(ImageView)findViewById(R.id.giant_image);
                    imageView.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    flag= 1;
                }

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AdminDetails.this,Approval.class);
                in.putExtra("id",id);
                in.putExtra("no",pos);
                startActivity(in);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(flag==1)
        {ImageView imageView=(ImageView)findViewById(R.id.giant_image);
            LinearLayout linearLayout=(LinearLayout)findViewById(R.id.giant_linear_layout);
            imageView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            flag=0;
        }
        else{
        Intent intent=new Intent(AdminDetails.this,Admin.class);
        startActivity(intent);
        finish();}
    }
}
