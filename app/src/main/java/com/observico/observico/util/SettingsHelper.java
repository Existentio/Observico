package com.observico.observico.util;

import android.content.Context;
import android.view.View;

import com.observico.observico.R;

/**
 * Created by Георгий on 08.09.2017.
 */

public class SettingsHelper extends View {

    public static String mUrl;

    public SettingsHelper(Context context) {
        super(context);
    }

    public void chooseWebLink(String link) {
        if (link.equals(getContext().getString(R.string.pref_link_hinews_value))) {
            setLink(getContext().getString(R.string.hi_news));
        } else if (link.equals(getContext().getString(R.string.pref_link_reuters_value))) {
            setLink(getContext().getString(R.string.reuters_tech));
        } else if (link.equals(getContext().getString(R.string.pref_link_popmech_value))) {
            setLink(getContext().getString(R.string.popmech_tech));
        }
    }

    public static void setLink(String url) {
        mUrl = url;
    }

    public static String getLink() {
        return mUrl;
    }

}
