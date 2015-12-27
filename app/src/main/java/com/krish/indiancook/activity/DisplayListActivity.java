package com.krish.indiancook.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.krish.indiancook.Layouts.FlowLayout;
import com.krish.indiancook.R;
import com.krish.indiancook.dto.CookingItem;
import com.krish.indiancook.utils.CookingConstants;
import com.krish.indiancook.utils.ResourceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by u462716 on 12/23/2015.
 */
public class DisplayListActivity extends AppCompatActivity {
    EditText custname, custphone, custemail, custaddress1, custaddress2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE); //To display the activity in Full Screen

        setContentView(R.layout.activity_displaylist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String category = null;

        if (extras != null) {
            category = extras.getString("category");
        }

        if(CookingConstants.SOUPS.equalsIgnoreCase(category)){
            populateItems(getItemList(CookingConstants.SOUPLIST));
        }

    }

    private void populateItems(List<CookingItem> cookingItemList) {
        LayoutInflater mInflater = LayoutInflater.from(this);
        FlowLayout mFlowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        View v = null; CookingItem ci = null;
        for (int i = 0; i < cookingItemList.size(); i++) {
            v = mInflater.inflate(R.layout.foodcategoriesitem, mFlowLayout, false);
            ImageView iv = (ImageView) v.findViewById(R.id.imageLabel);
            TextView tv = (TextView) v.findViewById(R.id.textLabel);
            ci = cookingItemList.get(i);
            tv.setText(ci.getName());
            iv.setBackgroundResource(getResources().getIdentifier(ci.getCode(), "drawable", this.getPackageName()));
            v.setTag(ci.getCode());
            mFlowLayout.addView(v);
        }
    }

    private List<CookingItem> getItemList(String categoryType) {
        List<CookingItem> mCategories = new ArrayList<CookingItem>();
        for(TypedArray item : ResourceHelper.getMultiTypedArray(this,categoryType)) {
            CookingItem citem = new CookingItem();
            citem.setName(item.getString(0));
            citem.setCategory(item.getString(1));
            citem.setCode(item.getString(1));
            mCategories.add(citem);
        }
        return mCategories;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
