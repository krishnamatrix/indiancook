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
import android.widget.ImageView;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ingredient, parent, false);
        }
        String ingredItem = values.get(position);
        String[] ingredItemList = ingredItem.split("|");
        TextView textView = (TextView) convertView.findViewById(R.id.ingredientDetail);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ingredientImage);
        textView.setText(ingredItemList[0]);
        imageView.setBackgroundResource(context.getResources().getIdentifier(ingredItemList[1] , "drawable", context.getPackageName()));
        if(position % 2 ==1) {
            convertView.setBackgroundColor(Color.rgb(231, 249, 255));
        } else {
            convertView.setBackgroundColor(Color.rgb(195, 240, 255));
        }
        return convertView;
    }
}

