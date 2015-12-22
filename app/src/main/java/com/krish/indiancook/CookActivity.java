package com.krish.indiancook;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.krish.indiancook.Layouts.FlowLayout;

public class CookActivity extends AppCompatActivity {
    FlowLayout mFlowLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        LayoutInflater mInflater = LayoutInflater.from(this);
        mFlowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        View v = mInflater.inflate(R.layout.imageabovetextlayout,mFlowLayout, false);
        String [][] names = {{"Soups","soup"}, {"Starters","soup"},{"Main Course","soup"},{"Desserts","soup"}};

        for(int i = 0; i<names.length;i++){
            v = mInflater.inflate(R.layout.imageabovetextlayout,mFlowLayout, false);
            ImageView iv = (ImageView)v.findViewById(R.id.imageLabel);
            TextView tv = (TextView)v.findViewById(R.id.textLabel);
            tv.setText(names[i][0]);
            iv.setBackgroundResource(getResources().getIdentifier(names[i][1], "drawable", this.getPackageName()));
            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(20, 20);
            v.setLayoutParams(params);
            mFlowLayout.addView(v);
        }
    }

}
