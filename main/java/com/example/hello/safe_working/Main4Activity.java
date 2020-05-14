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

public class Main4Activity extends ArrayAdapter<Timeline> {

    private Activity context;
    private List<Timeline> ssList;
    public Main4Activity(Activity context, List<Timeline> ssList)
    {
        super(context, R.layout.activity_main4, ssList);
        this.context = context;
        this.ssList = ssList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_main4, null, true);

        TextView textViewNameOfSharer = (TextView) listViewItem.findViewById(R.id.pr);
        TextView textViewPriceExpected = (TextView) listViewItem.findViewById(R.id.prdes);
        //TextView textViewSeatDetails = (TextView) listViewItem.findViewById(R.id.seat_details);
        //TextView textViewDOJ = (TextView) listViewItem.findViewById(R.id.doj);
        Timeline ssdetails = ssList.get(position);
        textViewNameOfSharer.setText(ssdetails.getProcess());
        textViewPriceExpected.setText(ssdetails.getProcessdetail());
        //textViewSeatDetails.setText(ssdetails.getDescription());
        //textViewDOJ.setText(ssdetails.getWitness());
        return  listViewItem;
    }
}
