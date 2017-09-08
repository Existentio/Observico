package com.observico.observico.model;

import com.observico.observico.RssFeed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Георгий on 08.09.2017.
 */

public interface RssService {
    @GET
    Call<RssFeed> getRss(@Url String url);
}
