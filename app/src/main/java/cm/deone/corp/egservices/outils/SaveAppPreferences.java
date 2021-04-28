package cm.deone.corp.egservices.outils;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import cm.deone.corp.egservices.MainActivity;

import static cm.deone.corp.egservices.outils.Xconstants.EN;
import static cm.deone.corp.egservices.outils.Xconstants.LANGUAGEKEY;
import static cm.deone.corp.egservices.outils.Xconstants.THEMEKEY;
import static cm.deone.corp.egservices.outils.Xconstants.XPREF;

public class SaveAppPreferences {
    private final Context context;
    private final SharedPreferences sp;

    public SaveAppPreferences(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(XPREF, Context.MODE_PRIVATE);
    }

    public void setModeState(boolean value){
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(THEMEKEY, value);
        ed.apply();
    }

    public boolean getModeState(){
        return sp.getBoolean(THEMEKEY, false);
    }

    public void setAppMode(boolean value) {
        ((AppCompatActivity)context).getDelegate().setLocalNightMode( value ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void setLanguageState(String value){
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(LANGUAGEKEY, value);
        ed.apply();
    }

    public String getLanguageState(){
        return sp.getString(LANGUAGEKEY, EN);
    }

    public void setAppLocale(String value) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(new Locale(value.toLowerCase()));
        } else {
            configuration.locale = new Locale(value.toLowerCase());
        }
        resources.updateConfiguration(configuration, dm);
    }

}
