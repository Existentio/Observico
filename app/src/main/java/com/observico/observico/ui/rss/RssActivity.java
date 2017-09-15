package com.observico.observico.ui.rss;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.observico.observico.R;
import com.observico.observico.data.local.AppPreferenceHelper;
import com.observico.observico.ui.ArticleFragment;
import com.observico.observico.ui.SettingsActivity;
//import com.observico.observico.util.MyApplication;


public class RssActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "main";
    private AppPreferenceHelper appPreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appPreferenceHelper = new AppPreferenceHelper("main");

        setupSharedPreferences();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, RssFragment.newInstance(appPreferenceHelper.getWebLink()))
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_link_key))) {
            loadUrlFromPreferences(sharedPreferences);
        }
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadUrlFromPreferences(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void loadUrlFromPreferences(SharedPreferences sharedPreferences) {
        appPreferenceHelper.chooseWebLink(sharedPreferences.getString(getString(R.string.pref_link_key),
                getString(R.string.pref_link_initial_value)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


