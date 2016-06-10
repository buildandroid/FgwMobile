package Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jim on 16/6/10.
 */
public final class SharedObject {
    public static final String TOKEN = "token";

    public static void saveToken(Context context, String tokenValue){
        saveToken(PreferenceManager.getDefaultSharedPreferences(context), tokenValue);
    }

    public static void saveToken(SharedPreferences sharedPreferences, String tokenValue){
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        saveToken(edit, tokenValue);
        edit.commit();
    }

    public static void saveToken(SharedPreferences.Editor editor, String tokenValue){
        editor.putString(TOKEN, tokenValue).apply();
    }

    public static String getToken(Context context){
        return  getToken(PreferenceManager.getDefaultSharedPreferences(context));
    }

    public static String getToken(SharedPreferences sharedPreferences){
        return sharedPreferences.getString(TOKEN, null);
    }
}
