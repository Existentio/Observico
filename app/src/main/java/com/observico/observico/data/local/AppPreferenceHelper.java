package com.observico.observico.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.observico.observico.App;
import com.observico.observico.R;

/**
 * Created by Георгий on 09.09.2017.
 */

public class AppPreferenceHelper implements PreferencesHelper {

    private final SharedPreferences mPrefs;

    private static final String PREF_KEY_URL = "pref key";
    private Context mContext;

    public AppPreferenceHelper( String prefFileName) {
//        this.mContext = context.getApplicationContext();
        this.mPrefs = App.get().getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public void setWebLink(String url) {
        mPrefs.edit().putString(PREF_KEY_URL, url).apply();
    }

    @Override
    public String getWebLink() {
        return mPrefs.getString(PREF_KEY_URL, null);
    }

    public void chooseWebLink(String link) {
        if (link.equals(App.get().getString(R.string.pref_link_hinews_value))) {
            setWebLink(App.get().getString(R.string.hi_news));
        } else if (link.equals(App.get().getString(R.string.pref_link_reuters_value))) {
            setWebLink(App.get().getString(R.string.reuters_tech));
        } else if (link.equals(App.get().getString(R.string.pref_link_popmech_value))) {
            setWebLink(App.get().getString(R.string.popmech_tech));
        }
    }

}
