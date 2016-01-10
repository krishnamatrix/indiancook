package com.krish.indiancook.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.krish.indiancook.R;
import com.krish.indiancook.adapter.CookingTipsAdapter;
import com.krish.indiancook.utils.HelperUtil;
import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

/**
 * Created by Krishna on 1/6/2016.
 */
public class CookingTipsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    JazzyListView tipsList;
    private static final String KEY_TRANSITION_EFFECT = "transition_effect";
    private JazzyRecyclerViewScrollListener jazzyScrollListener;
    private int mCurrentTransitionEffect = JazzyHelper.FLY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookingtips);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.WHITE), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        CookingTipsAdapter cookingtipsadapter = new CookingTipsAdapter(this, HelperUtil.getResourceList(this, "COOKING_TIPS"));
        tipsList = (JazzyListView)findViewById(R.id.cookingTipsList);
        //tipsList.setTransitionEffect(new SlideInEffect());
        tipsList.setAdapter(cookingtipsadapter);
        //tipsList.setFocusable(false);

        if (savedInstanceState != null) {
            mCurrentTransitionEffect = savedInstanceState.getInt(KEY_TRANSITION_EFFECT, JazzyHelper.FLY);
            setupJazziness(mCurrentTransitionEffect);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_TRANSITION_EFFECT, mCurrentTransitionEffect);
    }

    private void setupJazziness(int effect) {
        mCurrentTransitionEffect = effect;
        tipsList.setTransitionEffect(mCurrentTransitionEffect);
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

