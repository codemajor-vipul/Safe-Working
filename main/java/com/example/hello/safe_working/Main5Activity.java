package com.example.hello.safe_working;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class Main5Activity extends ArrayAdapter<ChatRoom> {
    private Activity context;
    private List<ChatRoom> ssList;
    int i;
    String disp = "No of Guests Visiting : ";
    private DatabaseReference databaseReferenceUsers;
    Date curr=Calendar.getInstance().getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    String t=sdf.format(curr);

    public Main5Activity(Activity context, List<ChatRoom> ssList)
    {
        super(context, R.layout.activity_main5, ssList);
        this.context = context;
        this.ssList = ssList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_main5, null, true);


        if(ssList.get(position).getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString().trim()))
        {
            //TextView name = (TextView) listViewItem.findViewById(R.id.main5_name1);
            TextView message = (TextView) listViewItem.findViewById(R.id.main5_message1);
            TextView message1 = (TextView) listViewItem.findViewById(R.id.main5_message);
            LinearLayout linearLayout=(LinearLayout)listViewItem.findViewById(R.id.main5_linear_layout1);
            linearLayout.setVisibility(View.VISIBLE);
            LinearLayout linearLayout1=(LinearLayout)listViewItem.findViewById(R.id.main5_linear_layout);
            linearLayout1.setVisibility(View.GONE);
            ImageView img=(ImageView)listViewItem.findViewById(R.id.main5_image);
            ImageView img1=(ImageView)listViewItem.findViewById(R.id.main5_image1);
            //name.setText(ssList.get(position).getPerson().toString());
            if(!ssList.get(position).getMessage().toString().equals("")) {
                message.setText(ssList.get(position).getMessage().toString());
                message.setVisibility(View.VISIBLE);
                message1.setVisibility(View.GONE);
                //name.setVisibility(View.GONE);
                img.setVisibility(View.GONE);
                img1.setVisibility(View.GONE);
            }
            else
            {
             message.setVisibility(View.GONE);
             img1.setVisibility(View.VISIBLE);
                img.setVisibility(View.VISIBLE);
                message1.setVisibility(View.GONE);
             Glide.with(img1.getContext()).load(ssList.get(position).getImage_url().toString().trim()).apply(new RequestOptions().placeholder(R.drawable.abcd).error(R.drawable.abcd)).into(img1);
                Glide.with(img.getContext()).load(ssList.get(position).getImage_url().toString().trim()).apply(new RequestOptions().placeholder(R.drawable.abcd).error(R.drawable.abcd)).into(img);
            }
        }
        else
        {
            //TextView name = (TextView) listViewItem.findViewById(R.id.main5_name);
            TextView message = (TextView) listViewItem.findViewById(R.id.main5_message);
            TextView message1 = (TextView) listViewItem.findViewById(R.id.main5_message1);
            LinearLayout linearLayout=(LinearLayout)listViewItem.findViewById(R.id.main5_linear_layout1);
            linearLayout.setVisibility(View.GONE);
            LinearLayout linearLayout1=(LinearLayout)listViewItem.findViewById(R.id.main5_linear_layout);
            linearLayout1.setVisibility(View.VISIBLE);
            //name.setText(ssList.get(position).getPerson().toString());
            //name.setVisibility(View.GONE);
            ImageView img=(ImageView)listViewItem.findViewById(R.id.main5_image);
            ImageView img1=(ImageView)listViewItem.findViewById(R.id.main5_image1);
            //name.setText(ssList.get(position).getPerson().toString());
            if(!ssList.get(position).getMessage().toString().equals("")) {
                message.setText(ssList.get(position).getMessage().toString());
                message.setVisibility(View.VISIBLE);
                message1.setVisibility(View.GONE);
                //name.setVisibility(View.GONE);
                img.setVisibility(View.GONE);
                img1.setVisibility(View.GONE);
            }
            else
            {
                message.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
                img1.setVisibility(View.VISIBLE);
                message1.setVisibility(View.GONE);
                Glide.with(img.getContext()).load(ssList.get(position).getImage_url().toString().trim()).apply(new RequestOptions().placeholder(R.drawable.abcd).error(R.drawable.abcd)).into(img);
                Glide.with(img1.getContext()).load(ssList.get(position).getImage_url().toString().trim()).apply(new RequestOptions().placeholder(R.drawable.abcd).error(R.drawable.abcd)).into(img1);
            }

        }

        return  listViewItem;
    }
}