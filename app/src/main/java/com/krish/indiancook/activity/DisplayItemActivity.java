package com.krish.indiancook.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.krish.indiancook.R;
import com.krish.indiancook.adapter.DirectionsAdapter;
import com.krish.indiancook.adapter.IngredientAdapter;
import com.krish.indiancook.utils.CookingConstants;
import com.krish.indiancook.utils.HelperUtil;
import com.krish.indiancook.utils.StorageUtil;
import com.krish.indiancook.views.NonScrollListView;

/**
 * Created by u462716 on 12/28/2015.
 */
public class DisplayItemActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private String itemId, itemDesc, itemImage, timetaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.BLACK), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            itemId = extras.getString("itemid");
            itemDesc = extras.getString("itemdesc");
            itemImage = extras.getString("itemimage");
            if(itemImage != null && !itemImage.isEmpty()){
                itemImage = itemImage.replace("icon","");
            }
            timetaken = extras.getString("timetaken");
        }
        TextView nameofdish = (TextView) findViewById(R.id.nameofdish);
        nameofdish.setText(itemDesc);

        if(timetaken != null){
            TextView timetakenText = (TextView) findViewById(R.id.timetaken);
            timetakenText.setText(timetaken);
        }

        ImageView itemImageView = (ImageView) findViewById(R.id.itemImage);
        if(itemImage != null){
            int res = getResources().getIdentifier(itemImage , "drawable", getPackageName());
            itemImageView.setImageResource(res);
        } else {
            itemImageView.setBackgroundResource(R.drawable.defaultgif);
        }
        Button favouritesIcon = (Button) findViewById(R.id.favouritesIcon);
        final TextView tv = (TextView) findViewById(R.id.lovesymbol);
        Log.d("IndianCook", " The item present " + StorageUtil.presentInFavorites(this, itemDesc));
        if(StorageUtil.presentInFavorites(this, itemDesc)) {
            tv.setTextColor(getResources().getColor(R.color.TOMATO_RED));
        }

        favouritesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StorageUtil.presentInFavorites(DisplayItemActivity.this, itemDesc)) {
                    tv.setTextColor(getResources().getColor(R.color.DARK_GREY));
                    StorageUtil.removeFromFavorites(DisplayItemActivity.this, itemId + CookingConstants.PIPE_SEPARATOR +
                            itemDesc + CookingConstants.PIPE_SEPARATOR + timetaken + CookingConstants.PIPE_SEPARATOR +
                            itemImage);
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Removed "+ itemDesc + " to Favorites!!", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbar.show();
                } else {
                    tv.setTextColor(getResources().getColor(R.color.TOMATO_RED));
                    StorageUtil.saveToFavorites(DisplayItemActivity.this, itemId + CookingConstants.PIPE_SEPARATOR +
                            itemDesc + CookingConstants.PIPE_SEPARATOR + timetaken + CookingConstants.PIPE_SEPARATOR +
                            itemImage);
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Added "+ itemDesc + " to Favorites!!", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }

            }
        });

        IngredientAdapter ingredientadapter = new IngredientAdapter(this, HelperUtil.getResourceList(this, "INGREDIENT_" + itemId));
        NonScrollListView ingredList = (NonScrollListView)findViewById(R.id.ingredientList);
        ingredList.setAdapter(ingredientadapter);
        ingredList.setFocusable(false);

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
