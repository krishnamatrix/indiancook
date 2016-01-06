package com.krish.indiancook.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Krishna on 1/6/2016.
 */
public class StorageUtil {
    public static void saveToFavorites(Context context, String favString) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        /*SharedPreferences settings = context.getSharedPreferences("favourites",
                Context.MODE_PRIVATE);*/
        Set<String> set = settings.getStringSet("favouriteitems", null);
        if(set == null)
            set = new TreeSet<String>();
        set.add(favString);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.putStringSet("favouriteitems", set);
        editor.commit();
    }

    public static void removeFromFavorites(Context context, String favString) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> set = settings.getStringSet("favouriteitems", null);
        if(set != null) {
            set.remove(favString);
            SharedPreferences.Editor editor = settings.edit();
            editor.clear();
            editor.putStringSet("favouriteitems", set);
            editor.commit();
        }
    }

    public static Set<String> getFavorites(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        /*SharedPreferences settings = context.getSharedPreferences("favourites",
                Context.MODE_PRIVATE);*/
        Set<String> set = settings.getStringSet("favouriteitems", null);
        return set;
    }

    public static boolean presentInFavorites(Context context, String favString) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> set = settings.getStringSet("favouriteitems", null);
        if(set != null) {
            List<String> list = new ArrayList<String>(set);
            for(int i = 0 ; i < list.size(); i++){
                if(list.get(i).contains(favString)){
                    return true;
                }
            }
        }
        return false;
    }

    public static void saveToPreferences(Context context, String key, String value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.putString(key, value);
        editor.commit();
    }


    public static String getFromPreferences(Context context, String key) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String value = settings.getString(key, null);
        return value;
    }
}
