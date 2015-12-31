package com.krish.indiancook.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.krish.indiancook.R;
import com.krish.indiancook.adapter.DirectionsAdapter;
import com.krish.indiancook.adapter.IngredientAdapter;
import com.krish.indiancook.utils.HelperUtil;
import com.krish.indiancook.views.NonScrollListView;

/**
 * Created by u462716 on 12/28/2015.
 */
public class DisplayItemActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.WHITE), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        Bundle extras = getIntent().getExtras();
        String itemDesc = null;
        if (extras != null) {
            itemId = extras.getString("itemid");
            itemDesc = extras.getString("itemdesc");
        }
        toolbar.setTitle(itemDesc);

        FrameLayout favouritesIcon = (FrameLayout) findViewById(R.id.favouritesIcon);
        final TextView tv = (TextView) findViewById(R.id.lovesymbol);
        favouritesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setTextColor(getResources().getColor(R.color.TOMATO_RED));
            }
        });

        IngredientAdapter ingredientadapter = new IngredientAdapter(this, HelperUtil.getResourceList(this, "INGREDIENT_" + itemId));
        NonScrollListView ingredList = (NonScrollListView)findViewById(R.id.ingredientList);
        ingredList.setAdapter(ingredientadapter);

        DirectionsAdapter directionsadapter = new DirectionsAdapter(this, HelperUtil.getResourceList(this, "DIRECTIONS_" +itemId));
        NonScrollListView directionsList = (NonScrollListView)findViewById(R.id.directionList);
        directionsList.setAdapter(directionsadapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
