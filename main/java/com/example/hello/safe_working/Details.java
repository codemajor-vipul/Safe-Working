package com.example.hello.safe_working;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.internal.zzbfq.NULL;

public class Details extends AppCompatActivity {
    String str;
    int pos;
    TextView t1,t2,t3,t4,t5,t6,t7,timeline,chat,details;
    ImageView ign;
    FirebaseDatabase database;
    DatabaseReference ref,ref1;
    private List<Timeline> list1;
    private  List<ChatRoom> list2;
    private int FILE_SELECT_CODE=1;
    private StorageReference mStorageRef;
    ListView lt2;
    String evidence="";
    private static final int PERMISSION_REQUEST_CODE = 200;
    ListView lt,lt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        t1=(TextView)findViewById(R.id.type);
        t2=(TextView)findViewById(R.id.name);
        t3=(TextView)findViewById(R.id.date);
        t4=(TextView)findViewById(R.id.witness1);
        lt2=(ListView)findViewById(R.id.chatlist);
        t5=(TextView)findViewById(R.id.descript);
        t6=(TextView)findViewById(R.id.evidence1);
        t7=(TextView)findViewById(R.id.dashboardtitle);
        timeline=(TextView)findViewById(R.id.timeline);
        chat=(TextView)findViewById(R.id.chat);
        details=(TextView)findViewById(R.id.details1);
        lt=(ListView)findViewById(R.id.timelinelist);
        list1=new ArrayList<Timeline>();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        Intent in=getIntent();
        database=FirebaseDatabase.getInstance();
        pos=getIntent().getExtras().getInt("number");
       // str=in.getStringExtra("number");
        //pos=Integer.parseInt(str);
        ign=(ImageView)findViewById(R.id.chat_send_image);
        ign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Details.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    execute();
                    //Toast.makeText(Addon.this, "permission already granted", Toast.LENGTH_SHORT).show();
                } else {
                    requestpermission();
                }
            }
        });
        timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout=(LinearLayout)findViewById(R.id.detailsdisp);
                linearLayout.setVisibility(View.GONE);

                LinearLayout linearLayout2=(LinearLayout)findViewById(R.id.chat_layout);
                linearLayout2.setVisibility(View.VISIBLE);
                LinearLayout linearLayout1=(LinearLayout)findViewById(R.id.timeline_layout);
                linearLayout1.setVisibility(View.VISIBLE);
                TextView mybarcolor1=(TextView)findViewById(R.id.layout_barshow_timeline);
                TextView mybarcolor2=(TextView)findViewById(R.id.layout_barshow_details1);
                TextView mybarcolor3=(TextView)findViewById(R.id.layout_barshow_chat);
                mybarcolor1.setBackgroundResource(R.color.white);
                mybarcolor2.setBackgroundResource(R.color.newblue);
                mybarcolor3.setBackgroundResource(R.color.newblue);
                ref1=database.getReference("Timeline").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]", "")).child("complaint"+pos);
                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list1.clear();
                        for(DataSnapshot ar:dataSnapshot.getChildren())
                        {
                            Timeline tl=ar.getValue(Timeline.class);
                            list1.add(tl);
                        }
                        Main4Activity adapter=new Main4Activity(Details.this,list1);
                        lt.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout=(LinearLayout)findViewById(R.id.chat_layout);
                linearLayout.setVisibility(View.VISIBLE);
                LinearLayout linearLayout1=(LinearLayout)findViewById(R.id.timeline_layout);
                linearLayout1.setVisibility(View.GONE);
                LinearLayout linearLayout2=(LinearLayout)findViewById(R.id.detailsdisp);
                linearLayout2.setVisibility(View.GONE);
                TextView mybarcolor1=(TextView)findViewById(R.id.layout_barshow_timeline);
                TextView mybarcolor2=(TextView)findViewById(R.id.layout_barshow_details1);
                TextView mybarcolor3=(TextView)findViewById(R.id.layout_barshow_chat);
                mybarcolor1.setBackgroundResource(R.color.newblue);
                mybarcolor2.setBackgroundResource(R.color.newblue);
                mybarcolor3.setBackgroundResource(R.color.white);


                FirebaseDatabase database;
                DatabaseReference myref;

               list2=new ArrayList<ChatRoom>();


                database=FirebaseDatabase.getInstance();

                myref=database.getReference("Chat").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]", ""));
                myref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list2.clear();
                        for(DataSnapshot art:dataSnapshot.getChildren())
                        {
                            ChatRoom ad=art.getValue(ChatRoom.class);
                                list2.add(ad);
                        }
                        Main5Activity adapter=new Main5Activity(Details.this,list2);
                        lt2.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                FloatingActionButton button=(FloatingActionButton) findViewById(R.id.send_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database;
                        DatabaseReference myref;
                        database=FirebaseDatabase.getInstance();
                        myref=database.getReference("Chat").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]", ""));
                        ChatRoom chatRoom=new ChatRoom();
                        EditText editText=(EditText)findViewById(R.id.chat_send_edit_text);
                        if(editText.getText().toString().equals(""))
                        {
                            Toast.makeText(Details.this, "Enter text first", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            chatRoom.setMessage(editText.getText().toString());
                            chatRoom.setImage_url("");
                            chatRoom.setPerson(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            chatRoom.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid().toString().trim());
                            myref.push().setValue(chatRoom);
                            editText.setText("");
                        }
                    }
                });


            }
        });
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout=(LinearLayout)findViewById(R.id.chat_layout);
                linearLayout.setVisibility(View.GONE);
                LinearLayout linearLayout1=(LinearLayout)findViewById(R.id.timeline_layout);
                linearLayout1.setVisibility(View.GONE);
                LinearLayout linearLayout2=(LinearLayout)findViewById(R.id.detailsdisp);
                linearLayout2.setVisibility(View.VISIBLE);
                TextView mybarcolor1=(TextView)findViewById(R.id.layout_barshow_timeline);
                TextView mybarcolor2=(TextView)findViewById(R.id.layout_barshow_details1);
                TextView mybarcolor3=(TextView)findViewById(R.id.layout_barshow_chat);
                mybarcolor1.setBackgroundResource(R.color.newblue);
                mybarcolor2.setBackgroundResource(R.color.white);
                mybarcolor3.setBackgroundResource(R.color.newblue);
            }
        });
        t7.setText("Complaint"+" "+(pos+1));
        ref=database.getReference("Additional").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]", "")).child("complaint"+pos);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Additional ad=dataSnapshot.getValue(Additional.class);
                t1.setText(ad.getCompalintType());
                t2.setText(ad.getNameAccused());
                t3.setText(ad.getDate());
                t4.setText(ad.getWitness());
                t5.setText(ad.getDescription());
                t6.setText(ad.getEvidence());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
    private void requestpermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(Details.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this).setTitle("Permission needed").setMessage("This permission is needed to upload the evidence")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Details.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        }else{
            ActivityCompat.requestPermissions(Details.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==FILE_SELECT_CODE && resultCode==RESULT_OK) {
            Uri uri = data.getData();
            String str = uri.toString();
            File file = new File(str);
            String path1=null;
            if (str.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = Details.this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        path1 = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }

            } else if (str.startsWith("file://")) {
                path1 = file.getName();
            }
            final StorageReference riversRef = mStorageRef.child("Chat").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]", ""));
            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            evidence = downloadUrl.toString();
                            Toast.makeText(Details.this, "successful", Toast.LENGTH_SHORT).show();
                            FirebaseDatabase database;
                            DatabaseReference myref;
                            database=FirebaseDatabase.getInstance();
                            myref=database.getReference("Chat").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[^a-zA-Z0-9]", ""));
                            ChatRoom chatRoom=new ChatRoom();
                            //  EditText editText=(EditText)findViewById(R.id.chat_send_edit_text);

                            chatRoom.setMessage("");
                            chatRoom.setImage_url(evidence.toString().trim());
                            chatRoom.setPerson(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            chatRoom.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid().toString().trim());
                            myref.push().setValue(chatRoom);
                            //f=1;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            Toast.makeText(Details.this, "unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Details.this,HomePage.class);
        startActivity(intent);
        finish();
        //super.onBackPressed();
    }
}
