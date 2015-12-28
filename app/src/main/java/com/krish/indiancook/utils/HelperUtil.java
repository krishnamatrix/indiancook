package com.krish.indiancook.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.krish.indiancook.R;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import java.util.Arrays;
import java.util.List;

/**
 * Created by u462716 on 12/28/2015.
 */
public class HelperUtil {

    public static List<String> getResourceList(Context context, String ingredientId){
        //ResourceHelper.getMultiTypedArray(context, "INGREDIENT" + ingredientId);
        int ingredientIdentifier = context.getResources().getIdentifier(ingredientId, "array", context.getPackageName());
        String[] ingredientList = context.getResources().getStringArray(ingredientIdentifier);
        /*List<IngredientDTO> ingredList = new ArrayList<IngredientDTO>();
        IngredientDTO igdto = new IngredientDTO();
        igdto.setIngredientImage("soup");
        igdto.setIngredientImage("Add 1/2 tsp of Salt");
        ingredList.add(igdto);*/

        return Arrays.asList(ingredientList);
    }

    public static void openSearch(final Toolbar toolbar, final SearchBox searchbox, final Activity activity) {
        toolbar.setTitle("");
        searchbox.revealFromMenuItem(R.id.action_search, activity);
        for (int x = 0; x < 10; x++) {
            SearchResult option = new SearchResult("Result "
                    + Integer.toString(x), activity.getResources().getDrawable(
                    R.drawable.ic_clear));
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
