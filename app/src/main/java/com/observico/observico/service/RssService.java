package com.observico.observico.service;

import com.observico.observico.parser.RssConverterFactory;
import com.observico.observico.parser.RssFeed;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Георгий on 09.09.2017.
 */

public class RssService {
    private static String BASE_URL = "https://github.com";

    public interface RssAPI {
        @GET
        Call<RssFeed> getRss(@Url String url);
    }

    public RssAPI getAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(RssConverterFactory.create()) //working
//                .client(new OkHttpClient())
//                .addConverterFactory(SimpleXmlConverterFactory.create())
//                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
//                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(
//                        new Persister(new AnnotationStrategy())))
                .build();
        return retrofit.create(RssAPI.class);
    }
}
