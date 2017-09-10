package com.observico.observico.ui.rss;

import android.util.Log;

import com.observico.observico.parser.RssFeed;
import com.observico.observico.service.RssService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Георгий on 08.09.2017.
 */

public class RssPresenterImpl implements RssPresenter {

    private final String TAG = getClass().getName();

    private RssView rssView;
    private RssService rssService;

    public RssPresenterImpl(RssView rssView) {
        this.rssView = rssView;
        this.rssService = new RssService();
    }

    @Override
    public void fetchRssFeed(String url) {
        rssView.showProgress();

        rssService
                .getAPI()
                .getRss(url)
                .enqueue(new Callback<RssFeed>() {
                    @Override
                    public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                        rssView.setItems(response.body().getItems());
                        rssView.hideProgress();
                    }

                    @Override
                    public void onFailure(Call<RssFeed> call, Throwable t) {
                        Log.d(TAG , "Ошибка загрузки данных");
                    }
                });
    }

    @Override
    public void onDestroy() {
        rssView = null;
    }


}
