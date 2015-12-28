package com.krish.indiancook.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.krish.indiancook.R;
import com.krish.indiancook.adapter.IngredientsAdapter;
import com.krish.indiancook.utils.HelperUtil;
import com.krish.indiancook.utils.NestedListView;

/**
 * Created by u462716 on 12/28/2015.
 */
public class DisplayItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Bundle extras = getIntent().getExtras();
        String itemid = null;
        if (extras != null) {
            itemid = extras.getString("itemid");
        }
        IngredientsAdapter iga = new IngredientsAdapter(this, HelperUtil.getIngredientsList(this,itemid));
        NestedListView ingredList = (NestedListView)findViewById(R.id.ingredientList);
        ingredList.setAdapter(iga);
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
