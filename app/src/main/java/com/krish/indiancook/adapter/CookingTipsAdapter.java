package com.krish.indiancook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.krish.indiancook.R;

import java.util.List;

/**
 * Created by Krishna on 1/8/2016.
 */
public class CookingTipsAdapter


        extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> values;

    public CookingTipsAdapter(Context context, List<String> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cookingtips_item, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.cookingTipDetail);
        textView.setText(values.get(position));
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}