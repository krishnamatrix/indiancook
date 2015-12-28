package com.krish.indiancook.adapter;

/**
 * Created by u462716 on 12/28/2015.
 */

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.krish.indiancook.R;

import java.util.List;

public class IngredientAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> values;

    public IngredientAdapter(Context context, List<String> values) {
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
            inflater.inflate(R.layout.ingredient, rowView, true);
        } else {
            rowView = (LinearLayout)convertView;
        }
        String ingredItem = values.get(position);
        Log.d("IndianCook", "IngredItem:" + ingredItem +",position:" + position);
        String[] ingredItemList = ingredItem.split("\\|");
        TextView textView = (TextView) rowView.findViewById(R.id.ingredientDetail);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.ingredientImage);
        textView.setText(ingredItemList[0]);
        textView.setTextColor(Color.parseColor("#000000"));
        try{
            int res = context.getResources().getIdentifier(ingredItemList[1] , "drawable", context.getPackageName());
            if(res != 0) {
                imageView.setBackgroundResource(res);
            }
        } catch(Exception e){
        }

        if(position % 2 == 0) {
            rowView.setBackgroundColor(Color.rgb(256, 256, 256));
        } else {
            rowView.setBackgroundColor(Color.rgb(180, 180, 180));
        }
        return rowView;
    }
}

