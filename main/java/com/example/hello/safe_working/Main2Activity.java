package com.example.hello.safe_working;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Main2Activity extends ArrayAdapter<Additional> {
    private Activity context;
    private List<Additional> ssList;
    int i;
    String disp = "No of Guests Visiting : ";
    private DatabaseReference databaseReferenceUsers;


    public Main2Activity(Activity context, List<Additional> ssList)
    {
        super(context, R.layout.activity_main2, ssList);
        this.context = context;
        this.ssList = ssList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_main2, null, true);

        TextView textViewNameOfSharer = (TextView) listViewItem.findViewById(R.id.OAA_name_of_sharer);
        TextView textViewPriceExpected = (TextView) listViewItem.findViewById(R.id.price_expected);
        TextView textViewSeatDetails = (TextView) listViewItem.findViewById(R.id.seat_details);
        TextView textViewDOJ = (TextView) listViewItem.findViewById(R.id.comno);
        TextView textViewDOJ1 = (TextView) listViewItem.findViewById(R.id.app);
        Additional ssdetails = ssList.get(position);
        textViewNameOfSharer.setText(ssdetails.getCompalintType());
        textViewPriceExpected.setText(ssdetails.getNameAccused());
        textViewSeatDetails.setText(ssdetails.getDescription());
        i=Integer.parseInt(ssdetails.getComplaintno());
        i++;
        textViewDOJ.setText(i+"");
        if(ssdetails.getApproved().equals("0"))
        {
            textViewDOJ1.setText("Not Approved");
        }
        else
        {
            textViewDOJ1.setText("Approved");
        }
        return  listViewItem;
    }
}