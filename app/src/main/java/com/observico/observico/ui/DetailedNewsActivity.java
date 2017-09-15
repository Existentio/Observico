package com.observico.observico.ui;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.observico.observico.R;
import com.observico.observico.data.local.AppPreferenceHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class DetailedNewsActivity extends AppCompatActivity {

    TextView tvTitle, tvText;
    AppPreferenceHelper appPreferenceHelper;
    String url;

    private static final String ARTICLE_BODY = "[itemprop=articleBody]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_news);
        overridePendingTransition(R.anim.slide_left, 0);

        tvTitle = (TextView) findViewById(R.id.detailed_tv_title);
        tvText = (TextView) findViewById(R.id.detailed_tv_text);
        appPreferenceHelper = new AppPreferenceHelper("main");
        url = getIntent().getExtras().getString("url");

        initToolbar();
        new HtmlParserTask().execute();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private class HtmlParserTask extends AsyncTask<Void, Void, Document> {
        Document doc = null;
        String BASE_URL2 = "https://www.popmech.ru/science/386572-drevnyaya-kitayskaya-zagadka-smozhete-li-vy-eyo-reshit/";

        @Override
        protected Document doInBackground(Void... params) {
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {
            setTitle();
            setText();
        }

        private void setTitle() {
            String title = doc.title();
            tvTitle.setText(title);
        }

        private void setText() {
            Element links = doc.select(ARTICLE_BODY).first();
            tvText.setText(links.text());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right, 0);
    }
}
