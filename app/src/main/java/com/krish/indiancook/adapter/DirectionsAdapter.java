package com.krish.indiancook.adapter;

/**
 * Created by u462716 on 12/28/2015.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.krish.indiancook.R;
import com.krish.indiancook.utils.CookingConstants;

import java.util.List;

public class DirectionsAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> values;

    public DirectionsAdapter(Context context, List<String> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.directions, parent, false);
        }
        TextView startextView = (TextView) convertView.findViewById(R.id.startext);
        if(position % 5 == 0) {
            startextView.setTextColor(Color.parseColor(CookingConstants.STAR_COLOR_CODES[4]));
        } else if(position % 4 == 0) {
            startextView.setTextColor(Color.parseColor(CookingConstants.STAR_COLOR_CODES[3]));
        } else if(position % 3 == 0) {
            startextView.setTextColor(Color.parseColor(CookingConstants.STAR_COLOR_CODES[2]));
        }else if(position % 2 == 0) {
            startextView.setTextColor(Color.parseColor(CookingConstants.STAR_COLOR_CODES[1]));
        }else {
            startextView.setTextColor(Color.parseColor(CookingConstants.STAR_COLOR_CODES[0]));
        }
        String ingredItem = values.get(position);
        String[] ingredItemList = ingredItem.split("\\|");
        TextView textView = (TextView) convertView.findViewById(R.id.directionDetail);
        textView.setText(ingredItemList[0]);
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}

