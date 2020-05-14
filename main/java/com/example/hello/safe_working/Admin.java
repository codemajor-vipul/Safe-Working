package com.example.hello.safe_working;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
    ListView lt1;
    private List<Additional> list1;
    FirebaseDatabase database2;
    DatabaseReference myref3;
    FloatingActionButton fb;
    TextView signout;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mAuth = FirebaseAuth.getInstance();
        database2=FirebaseDatabase.getInstance();
        lt1=(ListView)findViewById(R.id.adminlist);
        signout=(TextView)findViewById(R.id.signoutad);
        fb=(FloatingActionButton)findViewById(R.id.gotochat);
        list1=new ArrayList<Additional>();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent4=new Intent(Admin.this,MainActivity.class);
                startActivity(intent4);
                finish();
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Admin.this,AdminChatListActivity.class);
                startActivity(intent1);
                finish();
            }
        });
        lt1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Additional additional=list1.get(position);
                //Additional additional=lt1.get(position);
               Intent intent=new Intent(Admin.this,AdminDetails.class);
                //Intent intent=new Intent(Admin.this,AdminChatListActivity.class);
                int m=Integer.parseInt(additional.getComplaintno());
                intent.putExtra("id",additional.getId());
                intent.putExtra("complaint",Integer.parseInt(additional.getComplaintno()));
                startActivity(intent);
                finish();
            }
        });
        myref3=database2.getReference("Additional");
        myref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ar:dataSnapshot.getChildren())
                {
                    for (DataSnapshot ar1: ar.getChildren())
                    {
                        Additional ad=ar1.getValue(Additional.class);
                        list1.add(ad);
                    }
                }
                Main3Activity adapter=new Main3Activity(Admin.this,list1);
                lt1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
