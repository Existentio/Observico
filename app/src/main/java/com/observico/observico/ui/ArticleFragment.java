package com.observico.observico.ui;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.observico.observico.App;
import com.observico.observico.R;
import com.observico.observico.data.local.AppPreferenceHelper;
import com.observico.observico.ui.rss.RssFragment;
import com.observico.observico.ui.rss.RssPresenter;
import com.observico.observico.ui.rss.RssPresenterImpl;
import com.observico.observico.ui.rss.RssView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {

    TextView tvTitle, tvText;
    AppPreferenceHelper appPreferenceHelper;
    String url;
    private static final String KEY_FEED = "FEED";
    private String mFeedUrl;
    private RssPresenter presenter;

    public ArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle = (TextView) getActivity().findViewById(R.id.detailed_tv_title);
        tvText = (TextView) getActivity().findViewById(R.id.detailed_tv_text);

        appPreferenceHelper = new AppPreferenceHelper("main");
//        url = getActivity().getIntent().getExtras().getString("url");

        new ArticleFragment.HtmlParserTask().execute();
    }

    public static ArticleFragment newInstance(String feedUrl) {
        ArticleFragment articleFragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_FEED, feedUrl);
        articleFragment.setArguments(args);
        return articleFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false);
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
            Element links = doc.select("[itemprop=articleBody]").first();
            tvText.setText(links.text());
        }
    }


}
