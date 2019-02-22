package com.example.etablissementmanagement.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.etablissementmanagement.Models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static android.content.Context.MODE_PRIVATE;

public class Utils {

    public static final String USER_LOGIN = "com.example.etablissementmanagement.user_login";
    public static final String SHARED_PREFS = "com.example.etablissementmanagement.sharedPrefs";

    public static void addUserToSharedPrefs(Context context, User user) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_LOGIN, userJson).apply();
    }

    public static void removeUser(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        prefs.edit().clear().apply();
    }

    public static User  getUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        //get from shared prefs
        String storedUserString = prefs.getString(USER_LOGIN, null);
        java.lang.reflect.Type type = new TypeToken<User>(){}.getType();
        User user = null;
        if(storedUserString != null){

            /**
             * we can use  user =  gson.fromJson(storedUserString, User.class);
             * without using type but we will have a problem in case we have a list of Users
             */

            user =  gson.fromJson(storedUserString, type);
        }
        return user;
    }

}
