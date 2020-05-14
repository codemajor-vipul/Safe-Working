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

public class Main3Activity extends ArrayAdapter<Additional> {
    private Activity context;
    private List<Additional> ssList;
    public Main3Activity(Activity context, List<Additional> ssList)
    {
        super(context, R.layout.activity_main3, ssList);
        this.context = context;
        this.ssList = ssList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_main3, null, true);

        TextView textViewNameOfSharer = (TextView) listViewItem.findViewById(R.id.comtype);
        TextView textViewPriceExpected = (TextView) listViewItem.findViewById(R.id.namecom);
        TextView textViewSeatDetails = (TextView) listViewItem.findViewById(R.id.comby);
        //TextView textViewDOJ = (TextView) listViewItem.findViewById(R.id.doj);

        Additional ssdetails = ssList.get(position);

        textViewNameOfSharer.setText(ssdetails.getCompalintType());
        textViewPriceExpected.setText(ssdetails.getNameAccused());
        textViewSeatDetails.setText("Complaint by-"+ssdetails.getName());
        //textViewDOJ.setText(ssdetails.getWitness());

        return  listViewItem;
    }
}
