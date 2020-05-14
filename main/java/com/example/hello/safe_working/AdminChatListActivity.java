package com.example.hello.safe_working;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminChatListActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        if(f==0) {
            Intent intent=new Intent(AdminChatListActivity.this,Admin.class);
            startActivity(intent);
            finish();
        }
        else {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.chat_layout_admin);
            linearLayout.setVisibility(View.GONE);
            ListView linearLayout1 = (ListView) findViewById(R.id.admin_side_chat_list);
            linearLayout1.setVisibility(View.VISIBLE);
            f=0;
        }
    }
    ListView lt1;
    ListView lt2;
    private  List<ChatRoom> list2;
    private List<String> list1;
    FirebaseDatabase database2;
    DatabaseReference myref3;
    int f=0;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chat_list);

        lt2=(ListView)findViewById(R.id.chatlist_admin);
        mAuth = FirebaseAuth.getInstance();
        database2=FirebaseDatabase.getInstance();
        lt1=(ListView)findViewById(R.id.admin_side_chat_list);

        list1=new ArrayList<String>();

        lt1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LinearLayout linearLayout=(LinearLayout)findViewById(R.id.chat_layout_admin);
                linearLayout.setVisibility(View.VISIBLE);
                ListView linearLayout1=(ListView) findViewById(R.id.admin_side_chat_list);
                linearLayout1.setVisibility(View.GONE);
                f=1;
                FirebaseDatabase database;
                DatabaseReference myref;

                list2=new ArrayList<ChatRoom>();


                database=FirebaseDatabase.getInstance();

                myref=database.getReference("Chat").child(list1.get(position).toString().trim());
                myref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list2.clear();
                        for(DataSnapshot art:dataSnapshot.getChildren())
                        {
                            ChatRoom ad=art.getValue(ChatRoom.class);
                            list2.add(ad);
                        }
                        Main5Activity adapter=new Main5Activity(AdminChatListActivity.this,list2);
                        lt2.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                FloatingActionButton button=(FloatingActionButton)findViewById(R.id.send_button_admin);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database;
                        DatabaseReference myref;
                        database=FirebaseDatabase.getInstance();
                        myref=database.getReference("Chat").child(list1.get(position).toString().trim());
                        ChatRoom chatRoom=new ChatRoom();
                        EditText editText=(EditText)findViewById(R.id.chat_send_edit_text_admin);
                        if(editText.getText().toString().equals(""))
                        {
                            Toast.makeText(AdminChatListActivity.this, "Enter text first", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            chatRoom.setMessage(editText.getText().toString());
                            chatRoom.setPerson(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            chatRoom.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid().toString().trim());
                            myref.push().setValue(chatRoom);
                            editText.setText("");
                        }
                    }
                });

            }
        });
        myref3=database2.getReference("Additional");
        myref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ar:dataSnapshot.getChildren())
                {
                     String ad=ar.getKey();
                       // Toast.makeText(AdminChatListActivity.this, ar1.getKey()+" "+ar.getKey(), Toast.LENGTH_SHORT).show();
                        list1.add(ad);

                }
                Main6Activity adapter=new Main6Activity(AdminChatListActivity.this,list1);
                lt1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
