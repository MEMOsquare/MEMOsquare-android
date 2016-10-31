package com.estsoft.memosquare.utils;

import android.content.Context;

import com.estsoft.memosquare.models.FbUserModel;
import com.estsoft.memosquare.preferences.ComplexPreferences;

/**
 * Created by sun on 2016-10-31.
 * Used for storing class object in sharedpreference
 */

public class PrefUtils {
    public static void setCurrentUser(FbUserModel currentUser, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        complexPreferences.putObject("current_user_value", currentUser);
        complexPreferences.commit();
    }

    public static FbUserModel getCurrentUser(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        FbUserModel currentUser = complexPreferences.getObject("current_user_value", FbUserModel.class);
        return currentUser;
    }

    public static void clearCurrentUser( Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }
}
