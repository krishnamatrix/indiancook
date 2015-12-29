package com.krish.indiancook.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.krish.indiancook.R;
import com.krish.indiancook.adapter.DirectionsAdapter;
import com.krish.indiancook.adapter.IngredientAdapter;
import com.krish.indiancook.utils.HelperUtil;
import com.krish.indiancook.utils.NonScrollListView;

/**
 * Created by u462716 on 12/28/2015.
 */
public class DisplayItemActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.WHITE), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        Bundle extras = getIntent().getExtras();
        String itemid = null, itemDesc = null;
        if (extras != null) {
            itemid = extras.getString("itemid");
            itemDesc = extras.getString("itemdesc");
        }
        toolbar.setTitle(itemDesc);
        Log.d("IndianCook","The ingredientList"+ HelperUtil.getResourceList(this, "INGREDIENT_" + itemid));
        IngredientAdapter ingredientadapter = new IngredientAdapter(this, HelperUtil.getResourceList(this, "INGREDIENT_" + itemid));
        NonScrollListView ingredList = (NonScrollListView)findViewById(R.id.ingredientList);
        ingredList.setAdapter(ingredientadapter);

        DirectionsAdapter directionsadapter = new DirectionsAdapter(this, HelperUtil.getResourceList(this, "DIRECTIONS_" +itemid));
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
