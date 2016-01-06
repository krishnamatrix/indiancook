package com.krish.indiancook.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.krish.indiancook.R;
import com.krish.indiancook.adapter.IngredientAdapter;
import com.krish.indiancook.utils.HelperUtil;
import com.krish.indiancook.views.NonScrollListView;

/**
 * Created by Krishna on 1/6/2016.
 */
public class HealthTipsActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        IngredientAdapter ingredientadapter = new IngredientAdapter(this, HelperUtil.getResourceList(this, "INGREDIENT_"));
        NonScrollListView ingredList = (NonScrollListView)findViewById(R.id.ingredientList);
        ingredList.setAdapter(ingredientadapter);
        ingredList.setFocusable(false);
    }

}

