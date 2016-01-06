package com.krish.indiancook.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.krish.indiancook.R;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by u462716 on 12/28/2015.
 */
public class HelperUtil {

    public static List<String> getResourceList(Context context, String ingredientId){
        Log.d("IndianCook", "Trying to fetch the resource:" + ingredientId);
        //ResourceHelper.getMultiTypedArray(context, "INGREDIENT" + ingredientId);
        String[] ingredientList = null;
        int ingredientIdentifier = context.getResources().getIdentifier(ingredientId, "array", context.getPackageName());
        try {
            if(ingredientIdentifier != 0) {
                ingredientList = context.getResources().getStringArray(ingredientIdentifier);
                return Arrays.asList(ingredientList);
            }
        }catch(Exception e){
        }
        return null;
    }

    public static List<String> populateDataForSearch(Context context, int itemType){
        String[] item = context.getResources().getStringArray(itemType);
        String[] splitItem;
        List<String> itemList = new ArrayList<String>();
        for(int i= 0; i< item.length; i++){
            splitItem = item[i].split("\\|");
            itemList.add(splitItem[1]);
        }
        return itemList;
    }

    public static List<String> populateDataForSearch(List<String> itemList){
        String[] splitItem;
        List<String> outputItemList = new ArrayList<String>();
        for(int i= 0; i< itemList.size(); i++){
            splitItem = itemList.get(i).split("\\|");
            outputItemList.add(splitItem[1]);
        }
        return outputItemList;
    }

    public static List<String> filterData(Context context, int resourceId){
        String[] categoryList = context.getResources().getStringArray(resourceId);
        List<String> inputList = new ArrayList<String>(Arrays.asList(categoryList));
        String vegFilter = StorageUtil.getFromPreferences(context, CookingConstants.VEG_FILTER);
        String nonVegFilter = StorageUtil.getFromPreferences(context, CookingConstants.NON_VEG_FILTER);
        Log.d("IndianCook","The vegfilter:" + vegFilter + " Non:" + nonVegFilter);
        String[] value; String tempVal;
        List<String> tempList = new ArrayList<String>();
        Iterator<String> filterIterator = inputList.iterator();
        while (filterIterator.hasNext()) {
            tempVal = filterIterator.next();
            value = tempVal.split("\\|");
            //Log.d("IndianCook", "Removing the Vegfilter item: "+ value[1] + ", " + (CookingConstants.TRUE.equalsIgnoreCase(vegFilter) && !CookingConstants.VG.equalsIgnoreCase(value[4])));
            if(CookingConstants.TRUE.equalsIgnoreCase(vegFilter) && !CookingConstants.VG.equalsIgnoreCase(value[4])){
                //Log.d("IndianCook", "Removing from veg list");
                filterIterator.remove();
            } else if(CookingConstants.TRUE.equalsIgnoreCase(nonVegFilter) && !CookingConstants.NVG.equalsIgnoreCase(value[4])){
                //Log.d("IndianCook", "Removing from nonveg list");
                filterIterator.remove();
            } else {
                tempList.add(tempVal);
            }
        }
        return tempList;

        /*String vegFilter = StorageUtil.getFromPreferences(context, CookingConstants.VEG_FILTER);
        String nonVegFilter = StorageUtil.getFromPreferences(context, CookingConstants.NON_VEG_FILTER);
        Log.d("IndianCook","The vegfilter:" + vegFilter + " Non:" + nonVegFilter);
        String[] value;
        for(int i = 0 ; i < inputList.size(); i++) {
            value = inputList.get(i).split("\\|");
            Log.d("IndianCook", "Removing the Vegfilter item: "+ value[1] + ", " + (CookingConstants.TRUE.equalsIgnoreCase(vegFilter) && !CookingConstants.VG.equalsIgnoreCase(value[4])));
            if(CookingConstants.TRUE.equalsIgnoreCase(vegFilter) && !CookingConstants.VG.equalsIgnoreCase(value[4])){
                filteredList.remove(i);
            } else if(CookingConstants.TRUE.equalsIgnoreCase(nonVegFilter) && !CookingConstants.NVG.equalsIgnoreCase(value[4])){
                filteredList.remove(i);
            }
        }
        return filteredList;*/
    }

    public static List<String> addSearchData(Context context){
        List<String> searchData = new ArrayList<String>();
        List<String> itemDetails = HelperUtil.filterData(context, R.array.SOUPS);
        searchData.addAll(HelperUtil.populateDataForSearch(itemDetails));
        itemDetails = HelperUtil.filterData(context, R.array.CHUTNEYS);
        searchData.addAll(HelperUtil.populateDataForSearch(itemDetails));
        itemDetails = HelperUtil.filterData(context, R.array.FRYS);
        searchData.addAll(HelperUtil.populateDataForSearch(itemDetails));
        itemDetails = HelperUtil.filterData(context, R.array.STARTERS);
        searchData.addAll(HelperUtil.populateDataForSearch(itemDetails));
        itemDetails = HelperUtil.filterData(context, R.array.MAINCOURSE);
        searchData.addAll(HelperUtil.populateDataForSearch(itemDetails));
        itemDetails = HelperUtil.filterData(context, R.array.SWEETS);
        searchData.addAll(HelperUtil.populateDataForSearch(itemDetails));
        itemDetails = HelperUtil.filterData(context, R.array.DESSERTS);
        searchData.addAll(HelperUtil.populateDataForSearch(itemDetails));
        return searchData;
    }
    /*public static List<String> addSearchData(Context context){
        List<String> searchData = new ArrayList<String>();
        searchData.addAll(HelperUtil.populateDataForSearch(context, R.array.SOUPS));
        searchData.addAll(HelperUtil.populateDataForSearch(context, R.array.CHUTNEYS));
        searchData.addAll(HelperUtil.populateDataForSearch(context, R.array.FRYS));
        searchData.addAll(HelperUtil.populateDataForSearch(context, R.array.STARTERS));
        searchData.addAll(HelperUtil.populateDataForSearch(context, R.array.MAINCOURSE));
        searchData.addAll(HelperUtil.populateDataForSearch(context, R.array.SWEETS));
        searchData.addAll(HelperUtil.populateDataForSearch(context, R.array.DESSERTS));
        return searchData;
    }*/
    public static void openSearch(final Toolbar toolbar, final SearchBox searchbox, final Activity activity, List<String> searchData) {
        //toolbar.setTitle("");
        final CharSequence currentTitle = ((AppCompatActivity) activity).getSupportActionBar().getTitle();;
        searchbox.revealFromMenuItem(R.id.action_search, activity);
        searchbox.setSearchables(new ArrayList<SearchResult>());
        for (int x = 0; x < searchData.size(); x++) {
            SearchResult option = new SearchResult(searchData.get(x), activity.getResources().getDrawable(R.drawable.ic_clear));
            searchbox.addSearchable(option);
        }
        searchbox.setMenuListener(new SearchBox.MenuListener() {
            @Override
            public void onMenuClick() {
                // Hamburger has been clicked
                Toast.makeText(activity.getApplicationContext(), "Menu click",
                        Toast.LENGTH_LONG).show();
            }

        });
        searchbox.setSearchListener(new SearchBox.SearchListener() {
            @Override
            public void onSearchOpened() {
                // Use this to tint the screen
            }

            @Override
            public void onSearchClosed() {
                // Use this to un-tint the screen
                closeSearch(toolbar, searchbox, activity);
                ((AppCompatActivity) activity).getSupportActionBar().setTitle(currentTitle);
            }

            @Override
            public void onSearchTermChanged(String term) {
                // React to the search term changing
                // Called after it has updated results
            }

            @Override
            public void onSearch(String searchTerm) {
                Toast.makeText(activity.getApplicationContext(), searchTerm + " Searched",
                        Toast.LENGTH_LONG).show();
                toolbar.setTitle(searchTerm);
            }

            @Override
            public void onResultClick(SearchResult result) {
                //React to result being clicked
            }

            @Override
            public void onSearchCleared() {

            }
        });

    }

    private static void closeSearch(final Toolbar toolbar, final SearchBox searchbox, Activity activity) {
        searchbox.hideCircularly(activity);
        if(searchbox.getSearchText().isEmpty())toolbar.setTitle("");
    }
}
