package com.application.todo.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SaveSharedPreferences {
    private static final String EMAIL_ID = "email_id";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setEmail(Context ctx, String email) {
        Log.d("SET EMAIL*******", email);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(EMAIL_ID, email);
        editor.commit();
    }


    public static void clear(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }

    public static String getEmail(Context ctx) {
        Log.d("GET EMAIL*******", getSharedPreferences(ctx).getString("EMAIL_ID", ""));
        return getSharedPreferences(ctx).getString(EMAIL_ID, "");
    }


}
