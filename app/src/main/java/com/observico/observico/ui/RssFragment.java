package com.observico.observico.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.observico.observico.R;
import com.observico.observico.RssConverterFactory;
import com.observico.observico.RssFeed;
import com.observico.observico.model.RssService;
import com.observico.observico.model.RssItem;
import com.observico.observico.util.RssItemAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.SimpleXmlConverterFactory;


public class RssFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RssItemAdapter.OnItemClickListener {

    private static final String KEY_FEED = "FEED";
    private String mFeedUrl;
    private RssItemAdapter mAdapter;
    private SwipeRefreshLayout mSwRefresh;
    private RecyclerView mRecyclerView;

    public static RssFragment newInstance(String feedUrl) {
        RssFragment rssFragment = new RssFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_FEED, feedUrl);
        rssFragment.setArguments(args);
        return rssFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeedUrl = getArguments().getString(KEY_FEED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rss, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mSwRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swRefresh);
        mAdapter = new RssItemAdapter(getActivity());
        mAdapter.setListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mSwRefresh.setOnRefreshListener(this);

        fetchRss();
        return view;
    }

    private void fetchRss() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://github.com")
                .addConverterFactory(RssConverterFactory.create())
//                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        showLoading();
        RssService service = retrofit.create(RssService.class);
        service.getRss(mFeedUrl)
                .enqueue(new Callback<RssFeed>() {
                    @Override
                    public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                        onRssItemsLoaded(response.body().getItems());
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Call<RssFeed> call, Throwable t) {
                        Toast.makeText(getActivity(), "Ошибка загрузки", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void onRssItemsLoaded(List<RssItem> rssFeeds) {
        mAdapter.setFeed(rssFeeds);
        mAdapter.notifyDataSetChanged();
        if (mRecyclerView.getVisibility() != View.VISIBLE) {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void showLoading() {
        mSwRefresh.setRefreshing(true);
    }

    public void hideLoading() {
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        fetchRss();
    }

    @Override
    public void onItemSelected(RssItem rssItem) {
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(rssItem.getLink())));
        Intent intent = new Intent(getContext(), DetailedNewsActivity.class);
        intent.putExtra("url", rssItem.getLink());
        startActivity(intent);

    }
}
