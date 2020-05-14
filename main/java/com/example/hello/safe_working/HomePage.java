package com.example.hello.safe_working;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
        TextView guilty,mycomplaint,signout;
        FirebaseAuth mAuth;
        ListView lt,lt2;
        private List<Additional> list1;
        private List<Additional> list2;
        FloatingActionButton img;
        FirebaseDatabase database;
        DatabaseReference myref,myref2;
        String str="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mAuth = FirebaseAuth.getInstance();
        signout=(TextView)findViewById(R.id.signout);
        database=FirebaseDatabase.getInstance();
        guilty=(TextView)findViewById(R.id.guilty);
        img=(FloatingActionButton)findViewById(R.id.image_button_add);
        mycomplaint=(TextView)findViewById(R.id.mycomplaints);
        lt=(ListView)findViewById(R.id.list);
        lt2=(ListView)findViewById(R.id.listguilty);
        list1=new ArrayList<Additional>();
        list2=new ArrayList<Additional>();
       lt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent=new Intent(HomePage.this,Details.class);
             //  lt.getChildAt(position).
               intent.putExtra("number",position);
               startActivity(intent);
               finish();
           }
       });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent(HomePage.this,Addon.class);
                startActivity(intent4);
                finish();
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mAuth.signOut();
                Intent intent4=new Intent(HomePage.this,MainActivity.class);
                startActivity(intent4);
                finish();
            }
        });
        guilty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout1=(LinearLayout) findViewById(R.id.listview_mycomplaint);
                linearLayout1.setVisibility(View.GONE);
                LinearLayout linearLayout2=(LinearLayout) findViewById(R.id.listview_guilty);
                linearLayout2.setVisibility(View.VISIBLE);
                TextView mybarcolor1=(TextView)findViewById(R.id.layout_barshow_mycomplaints);
                TextView mybarcolor2=(TextView)findViewById(R.id.layout_barshow_guilty);
                mybarcolor1.setBackgroundResource(R.color.newblue);
                mybarcolor2.setBackgroundResource(R.color.white);
                myref2=database.getReference("NewRegister").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]", ""));
                myref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        NewRegister nr=dataSnapshot.getValue(NewRegister.class);
                        str=nr.getFullname();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //myref=database.getReference("Additional").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]", ""));
                myref=database.getReference("Additional");
                myref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list2.clear();
                        for(DataSnapshot art:dataSnapshot.getChildren())
                        {
                            for(DataSnapshot art1:art.getChildren()) {
                                //Additional ad = art.getValue(Additional.class);
                                Additional ad = art1.getValue(Additional.class);
                                /*if (ad.getApproved().equals("1")) {
                                    list2.add(ad);
                                }*/
                                //String str=FirebaseAuth.getInstance().getCurrentUser();
                                /*if(str.equals(ad.getNameAccused()))
                                {
                                    list2.add(ad);
                                }*/
                                if(str.equals(ad.getNameAccused()))
                                {
                                    list2.add(ad);
                                }
                            }
                            // adapter.notifyDataSetChanged();
                        }
                        Main2Activity adapter=new Main2Activity(HomePage.this,list2);
                        lt2.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        mycomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout linearLayout1=(LinearLayout)findViewById(R.id.listview_mycomplaint);
                linearLayout1.setVisibility(View.VISIBLE);
                TextView mybarcolor1=(TextView)findViewById(R.id.layout_barshow_mycomplaints);
                TextView mybarcolor2=(TextView)findViewById(R.id.layout_barshow_guilty);
                mybarcolor1.setBackgroundResource(R.color.white);
                mybarcolor2.setBackgroundResource(R.color.newblue);
                //lt2.setVisibility(View.VISIBLE);


            }
        });

        myref=database.getReference("Additional").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]", ""));
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list1.clear();
                for(DataSnapshot art:dataSnapshot.getChildren())
                {
                    Additional ad=art.getValue(Additional.class);
                    list1.add(ad);
                   // adapter.notifyDataSetChanged();
                }
                Main2Activity adapter=new Main2Activity(HomePage.this,list1);
                lt.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
