package com.krish.indiancook.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.krish.indiancook.R;
import com.krish.indiancook.adapter.CategoryItemsAdapter;
import com.krish.indiancook.dto.CookingItem;
import com.krish.indiancook.utils.CookingConstants;
import com.krish.indiancook.utils.HelperUtil;
import com.krish.indiancook.utils.ResourceHelper;
import com.krish.indiancook.utils.StorageUtil;
import com.krish.indiancook.views.ExpandableGridView;
import com.quinny898.library.persistentsearch.SearchBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by u462716 on 12/23/2015.
 */
public class DisplayListActivity extends AppCompatActivity {
    private SearchBox searchbox;
    private Toolbar toolbar;
    List<String> searchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaylist);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchbox = (SearchBox) findViewById(R.id.searchbox);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        Bundle extras = getIntent().getExtras();
        String type = null, categoryName = null;
        if (extras != null) {
            type = extras.getString("category");
            categoryName = extras.getString("categoryname");
        }
        getSupportActionBar().setTitle(categoryName);

        searchData = new ArrayList<String>();
        List<String> itemDetails;
        if(CookingConstants.SOUPS.equalsIgnoreCase(type)){
            itemDetails = HelperUtil.filterData(this, R.array.SOUPS);
            populateItems(itemDetails);
            searchData.addAll(HelperUtil.populateDataForSearch(itemDetails));
        } else if(CookingConstants.CHUTNEYS.equalsIgnoreCase(type)){
            itemDetails = HelperUtil.filterData(this, R.array.CHUTNEYS);
            populateItems(itemDetails);
            searchData.addAll(HelperUtil.populateDataForSearch(itemDetails));
        } else if(CookingConstants.FRYS.equalsIgnoreCase(type)){
            itemDetails = HelperUtil.filterData(this, R.array.FRYS);
            populateItems(itemDetails);
            searchData.addAll(HelperUtil.populateDataForSearch(itemDetails));
        } else if(CookingConstants.STARTERS.equalsIgnoreCase(type)){
            itemDetails = HelperUtil.filterData(this, R.array.STARTERS);
            populateItems(itemDetails);
            searchData.addAll(HelperUtil.populateDataForSearch(itemDetails));
        } else if(CookingConstants.MAINCOURSE.equalsIgnoreCase(type)){
            itemDetails = HelperUtil.filterData(this, R.array.MAINCOURSE);
            populateItems(itemDetails);
            searchData.addAll(HelperUtil.populateDataForSearch(itemDetails));
        } else if(CookingConstants.SWEETS.equalsIgnoreCase(type)){
            itemDetails = HelperUtil.filterData(this, R.array.SWEETS);
            populateItems(itemDetails);
            searchData.addAll(HelperUtil.populateDataForSearch(itemDetails));
        } else if(CookingConstants.FAVORITES.equalsIgnoreCase(type)){
            searchData = HelperUtil.addSearchData(this);
            toolbar.setBackgroundResource(R.color.SAFRON);
            Set favSet = StorageUtil.getFavorites(getBaseContext());
            if(favSet != null){
                List<String> list = new ArrayList<String>(favSet);
                populateItems(list);
            }
        } else{
            itemDetails = HelperUtil.filterData(this, R.array.DESSERTS);
            populateItems(itemDetails);
            searchData.addAll(HelperUtil.populateDataForSearch(itemDetails));
        }

    }
    private void populateItems(List<String> categoryList) {
        ExpandableGridView gridview = (ExpandableGridView) findViewById(R.id.itemsgridview);
        gridview.setExpanded(true);
        gridview.setAdapter(new CategoryItemsAdapter(this, categoryList));
    }
    private void populateItems(int resourceId) {
        ExpandableGridView gridview = (ExpandableGridView) findViewById(R.id.itemsgridview);
        gridview.setExpanded(true);
        gridview.setAdapter(new CategoryItemsAdapter(this,
                HelperUtil.filterData(this, resourceId)));

        /*LayoutInflater mInflater = LayoutInflater.from(this);
        FlowLayout mFlowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        View v = null; CookingItem ci = null;
        for (int i = 0; i < cookingItemList.size(); i++) {
            v = mInflater.inflate(R.layout.displaylistitems, mFlowLayout, false);
            ImageView iv = (ImageView) v.findViewById(R.id.imageLabel);
            TextView tv = (TextView) v.findViewById(R.id.textLabel);
            ci = cookingItemList.get(i);
            tv.setText(ci.getName());
            iv.setBackgroundResource(getResources().getIdentifier(ci.getCode(), "drawable", this.getPackageName()));
            v.setTag(ci.getCode());
            mFlowLayout.addView(v);
        }*/
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
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

