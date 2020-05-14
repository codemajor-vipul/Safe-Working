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

import java.util.List;

public class Main6Activity extends ArrayAdapter<String> {
    private Activity context;
    private List<String> ssList;
    public Main6Activity(Activity context, List<String> ssList)
    {
        super(context, R.layout.activity_main6, ssList);
        this.context = context;
        this.ssList = ssList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_main6, null, true);
        TextView textViewSeatDetails = (TextView) listViewItem.findViewById(R.id.admin_side_chat_list_text1);
        //TextView textViewDOJ = (TextView) listViewItem.findViewById(R.id.doj);
        String ssdetails = ssList.get(position);
       textViewSeatDetails.setText(ssdetails.toString());

        //textViewDOJ.setText(ssdetails.getWitness());

        return  listViewItem;
    }
}
