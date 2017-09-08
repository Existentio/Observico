package com.observico.observico.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.observico.observico.R;

public class DetailedNewsActivity extends AppCompatActivity {

    WebView mWebView;
    String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_news);

        mUrl = getIntent().getStringExtra("url");
        mWebView = (WebView) findViewById(R.id.webview);
        runWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void runWebView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(mUrl);
                WebSettings webSettings = mWebView.getSettings();
                webSettings.setUseWideViewPort(true);
                webSettings.setLoadWithOverviewMode(true);
                webSettings.setJavaScriptEnabled(true);
                mWebView.setWebViewClient(new WebViewClient());
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}
