package com.krish.indiancook.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.krish.indiancook.activity.DisplayItemActivity;

import java.util.List;

/**
 * Created by Krishna on 1/4/2016.
 */
public class CategoryItemsAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> values;

    public CategoryItemsAdapter(Context context, List<String> values) {
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
            inflater.inflate(R.layout.displaylistitems, rowView, true);
        } else {
            rowView = (LinearLayout)convertView;
        }
        String ingredItem = values.get(position);
        final String[] ingredItemList = ingredItem.split("\\|");
        TextView textView = (TextView) rowView.findViewById(R.id.textLabel);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageLabel);
        textView.setText(ingredItemList[1]);
        textView.setTextColor(Color.parseColor("#000000"));
        //rowView.setTag(ingredItemList[0]+"|" + ingredItemList[1]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, DisplayItemActivity.class);
                //String[] itemDetails = ((String)v.getTag()).split("\\|");
                intent.putExtra("itemid", ingredItemList[0]);
                intent.putExtra("itemdesc", ingredItemList[1]);
                if(ingredItemList.length > 2) {
                    intent.putExtra("itemimage", ingredItemList[2]);
                }
                /*if(itemDetails.length > 3)
                    intent.putExtra("timetaken", itemDetails[3]);*/
                context.startActivity(intent);
            }
        });
        try{
            int res = context.getResources().getIdentifier(ingredItemList[2] , "drawable", context.getPackageName());
            if(res != 0) {
                imageView.setImageResource(res);
            } else{
                imageView.setImageResource(R.drawable.defaultgif);
            }
        } catch(Exception e){
            imageView.setImageResource(R.drawable.defaultgif);
        }
        return rowView;
    }
}

