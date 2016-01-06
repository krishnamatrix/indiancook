package com.krish.indiancook.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.krish.indiancook.R;
import com.krish.indiancook.utils.CookingConstants;
import com.krish.indiancook.utils.StorageUtil;

/**
 * Created by Krishna on 1/6/2016.
 */
public class FilterActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    private Toolbar toolbar;
    private SwitchCompat vegfilter, nonvegfilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filterscreen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.WHITE), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        vegfilter = (SwitchCompat)findViewById(R.id.vegfilter);
        vegfilter.setOnCheckedChangeListener (this);
        String value = StorageUtil.getFromPreferences(this, CookingConstants.VEG_FILTER);
        if(value != null && CookingConstants.TRUE.equalsIgnoreCase(value)) {
            vegfilter.setChecked(true);
        }

        nonvegfilter = (SwitchCompat)findViewById(R.id.nonvegfilter);
        nonvegfilter.setOnCheckedChangeListener (this);
        value = StorageUtil.getFromPreferences(this, CookingConstants.NON_VEG_FILTER);
        if(value != null && CookingConstants.TRUE.equalsIgnoreCase(value)) {
            nonvegfilter.setChecked(true);
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.vegfilter:
                if(!isChecked){
                    StorageUtil.saveToPreferences(FilterActivity.this, CookingConstants.VEG_FILTER, CookingConstants.FALSE);
                }else{
                    StorageUtil.saveToPreferences(FilterActivity.this, CookingConstants.VEG_FILTER, CookingConstants.TRUE);
                }
                break;
            case R.id.nonvegfilter:
                if(!isChecked){
                    StorageUtil.saveToPreferences(FilterActivity.this, CookingConstants.NON_VEG_FILTER, CookingConstants.FALSE);
                }else{
                    StorageUtil.saveToPreferences(FilterActivity.this, CookingConstants.NON_VEG_FILTER, CookingConstants.TRUE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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