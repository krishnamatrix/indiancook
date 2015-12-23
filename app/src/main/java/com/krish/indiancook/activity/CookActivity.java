package com.krish.indiancook.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.krish.indiancook.Layouts.FlowLayout;
import com.krish.indiancook.R;
import com.krish.indiancook.utils.CookingConstants;

public class CookActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        setDrawer(this, toolbar);
        populateFoodCategories();
        populateMainPageOtherCategories(CookingConstants.POPULAR_DISHES, R.id.populardishes);
        populateMainPageOtherCategories(CookingConstants.POPULAR_DESSERTS, R.id.populardesserts);
    }

    private void populateMainPageOtherCategories(String [][] othercategory, int layoutId){
        LayoutInflater mInflater = LayoutInflater.from(this);
        LinearLayout lp = (LinearLayout) findViewById(layoutId);
        View v = null;
        for(int i = 0; i< othercategory.length;i++){
            v = mInflater.inflate(R.layout.dishesitem,lp, false);
            ImageView iv = (ImageView)v.findViewById(R.id.imageLabel);
            TextView tv = (TextView)v.findViewById(R.id.textLabel);
            tv.setText(othercategory[i][0]);
            iv.setBackgroundResource(getResources().getIdentifier(othercategory[i][1], "drawable", this.getPackageName()));
            lp.addView(v);
        }
    }
    private void populateFoodCategories(){
        LayoutInflater mInflater = LayoutInflater.from(this);
        FlowLayout mFlowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        View v = null;
        for(int i = 0; i< CookingConstants.FOOD_CATEGORIES.length;i++){
            v = mInflater.inflate(R.layout.foodcategoriesitem,mFlowLayout, false);
            ImageView iv = (ImageView)v.findViewById(R.id.imageLabel);
            TextView tv = (TextView)v.findViewById(R.id.textLabel);
            tv.setText(CookingConstants.FOOD_CATEGORIES[i][0]);
            iv.setBackgroundResource(getResources().getIdentifier(CookingConstants.FOOD_CATEGORIES[i][1], "drawable", this.getPackageName()));
            v.setTag(CookingConstants.FOOD_CATEGORIES[i][1]);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(CookActivity.this,DisplayListActivity.class);
                    intent.putExtra("category", (String) v.getTag());
                    CookActivity.this.startActivity(intent);
                }
            });
            mFlowLayout.addView(v);
        }
    }
    private void setDrawer(Activity act, Toolbar toolbar){
        navigationView = (NavigationView) this.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if(menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.rateapp:
                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        }
                        return true;
                    case R.id.updateAddress:
                        //IntentCaller.callIntent(ShowMapActivity.this, AddCustomerAddressActivity.class);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
