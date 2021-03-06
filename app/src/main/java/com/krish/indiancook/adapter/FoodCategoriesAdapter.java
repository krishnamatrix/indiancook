package com.krish.indiancook.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.krish.indiancook.R;
import com.krish.indiancook.activity.DisplayListActivity;

import java.util.List;

/**
 * Created by Krishna on 12/29/2015.
 */
public class FoodCategoriesAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> values;

    public FoodCategoriesAdapter(Context context, List<String> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public String getItem(int arg0) {
        return values.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout rowView;
        if(convertView == null) {
            rowView = new LinearLayout(getContext());
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.foodcategoriesitem, rowView, true);
        } else {
            rowView = (LinearLayout)convertView;
        }
        String ingredItem = values.get(position);
        String[] ingredItemList = ingredItem.split("\\|");
        TextView textView = (TextView) rowView.findViewById(R.id.foodItemText);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.foodItem);
        textView.setText(ingredItemList[0]);
        textView.setTextColor(Color.parseColor("#000000"));
        rowView.setTag(ingredItemList[2] + "|" + ingredItemList[0]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, DisplayListActivity.class);
                String[] itemDetails = ((String)v.getTag()).split("\\|");
                intent.putExtra("category", itemDetails[0]);
                intent.putExtra("categoryname", itemDetails[1]);
                context.startActivity(intent);
            }
        });
        try{
            int res = context.getResources().getIdentifier(ingredItemList[1] , "drawable", context.getPackageName());
            if(res != 0) {
                imageView.setImageResource(res);
            } else{
                imageView.setBackgroundResource(R.drawable.defaultgif);
            }
        } catch(Exception e){
            imageView.setBackgroundResource(R.drawable.defaultgif);
        }
        return rowView;
    }
}

