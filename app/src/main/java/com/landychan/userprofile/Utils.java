package com.landychan.userprofile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class Utils {

    public static final String USERS_MAP_KEY = "usersmapkey";
    public static final int UPDATE_USER = 12345;
    private static final String TAG = "Utils";

    static HashMap<String, UserDetails> loadUsersMap(Context context) {

        Log.d(TAG, "Loading users");
        SharedPreferences preferences = context.getSharedPreferences("Users", MODE_PRIVATE);
        String serializedUsers = preferences.getString(USERS_MAP_KEY, "");

        if(serializedUsers.length() == 0) {
            return new HashMap<>();
        } else {
            java.lang.reflect.Type type = new TypeToken<HashMap<String, UserDetails>>(){}.getType();
            Gson gson = new Gson();
            return gson.fromJson(serializedUsers, type);
        }
    }


    static boolean saveUsers(Context context, HashMap<String, UserDetails> map) {

        try {
            SharedPreferences prefs = context.getSharedPreferences("Users", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            Gson gson = new Gson();
            String serializedUsers = gson.toJson(map);
            editor.putString(Utils.USERS_MAP_KEY, serializedUsers);
            editor.apply();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return true;
    }
}
