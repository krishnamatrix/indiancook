package com.krish.indiancook.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.krish.indiancook.R;
import com.krish.indiancook.adapter.FoodCategoriesAdapter;
import com.krish.indiancook.utils.HelperUtil;
import com.krish.indiancook.views.ExpandableGridView;
import com.quinny898.library.persistentsearch.SearchBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CookActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private SearchBox searchbox;
    private Toolbar toolbar;
    List<String> searchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchbox = (SearchBox) findViewById(R.id.searchbox);
        setSupportActionBar(toolbar);
        setDrawer(this, toolbar);
        populateFoodCategories();
        populateMainPageOtherCategories(R.array.POPULAR_DISHES, R.id.populardishes);
        populateMainPageOtherCategories(R.array.POPULAR_DESSERTS, R.id.populardesserts);

        searchData = HelperUtil.addSearchData(this);
    }

    private void populateMainPageOtherCategories(int category, int layoutId){
        LayoutInflater mInflater = LayoutInflater.from(this);
        LinearLayout lp = (LinearLayout) findViewById(layoutId);
        View v = null;String item = null;
        String[] categoryList = getResources().getStringArray(category);
        for(int i = 0; i< categoryList.length;i++){
            item = categoryList[i];
            final String[] splitItem = item.split("\\|");
            v = mInflater.inflate(R.layout.populardishesitem,lp, false);
            ImageView iv = (ImageView)v.findViewById(R.id.imageLabel);
            TextView tv = (TextView)v.findViewById(R.id.textLabel);
            tv.setText(splitItem[0]);
            iv.setBackgroundResource(getResources().getIdentifier(splitItem[1], "drawable", this.getPackageName()));
            Log.d("IndianCook", "The split item list is " + splitItem + ",the item" + splitItem[3]);
            v.setTag(splitItem[3]);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(CookActivity.this, DisplayItemActivity.class);
                    intent.putExtra("itemid", (String) v.getTag());
                    intent.putExtra("itemdesc", (String) splitItem[0]);
                    CookActivity.this.startActivity(intent);
                }
            });
            lp.addView(v);
        }
    }
    private void populateFoodCategories(){
        String[] categoryList = getResources().getStringArray(R.array.FOOD_CATEGORIES);
        ExpandableGridView gridview = (ExpandableGridView) findViewById(R.id.foodcategoriesgridview);
        gridview.setExpanded(true);
        gridview.setAdapter(new FoodCategoriesAdapter(this, Arrays.asList(categoryList)));

        /*LayoutInflater mInflater = LayoutInflater.from(this);
        FlowLayout mFlowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        View v = null;
        for(int i = 0; i< CookingConstants.FOOD_CATEGORIES.length;i++){
            v = mInflater.inflate(R.layout.foodcategoriesitem,mFlowLayout, false);
            RoundedImageView iv = (RoundedImageView)v.findViewById(R.id.imageLabel);
            TextView tv = (TextView)v.findViewById(R.id.textLabel);
            tv.setText(CookingConstants.FOOD_CATEGORIES[i][0]);
            iv.setImageResource(getResources().getIdentifier(CookingConstants.FOOD_CATEGORIES[i][1], "drawable", this.getPackageName()));
            v.setTag(CookingConstants.FOOD_CATEGORIES[i][1]);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(CookActivity.this, DisplayListActivity.class);
                    intent.putExtra("category", (String) v.getTag());
                    CookActivity.this.startActivity(intent);
                }
            });
            mFlowLayout.addView(v);
        }*/
    }
    private void setDrawer(final Activity act, Toolbar toolbar){
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
                Intent intent = new Intent();
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
                    case R.id.favouritesMenu:
                        intent.setClass(act, DisplayListActivity.class);
                        intent.putExtra("category", "FAVORITES");
                        intent.putExtra("categoryname", "Favorites");
                        act.startActivity(intent);
                        return true;
                    case R.id.shoppingcart:
                        intent.setClass(act, DisplayListActivity.class);
                        intent.putExtra("category", "FAVORITES");
                        intent.putExtra("categoryname", "Favorites");
                        act.startActivity(intent);
                        return true;
                    case R.id.healthtips:
                        intent.setClass(act, DisplayListActivity.class);
                        intent.putExtra("category", "FAVORITES");
                        intent.putExtra("categoryname", "Favorites");
                        act.startActivity(intent);
                        return true;
                    case R.id.filter:
                        intent.setClass(act, FilterActivity.class);
                        act.startActivity(intent);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                HelperUtil.openSearch(toolbar, searchbox, this, searchData);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            searchbox.populateEditText(matches.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
