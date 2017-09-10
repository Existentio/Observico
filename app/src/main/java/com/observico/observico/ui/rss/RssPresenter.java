package com.observico.observico.ui.rss;

/**
 * Created by Георгий on 08.09.2017.
 */

public interface RssPresenter {

    void fetchRssFeed(String url);

    void onDestroy();
}
