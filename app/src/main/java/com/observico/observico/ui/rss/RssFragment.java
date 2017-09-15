package com.observico.observico.ui.rss;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.observico.observico.App;
import com.observico.observico.R;

import com.observico.observico.model.RssItem;
import com.observico.observico.ui.ArticleFragment;
import com.observico.observico.ui.DetailedNewsActivity;

import java.util.List;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;


public class RssFragment extends Fragment implements RssView, SwipeRefreshLayout.OnRefreshListener, RssItemAdapter.OnItemClickListener {

    private static final String KEY_FEED = "FEED";
    private String mFeedUrl;
    private RssItemAdapter mAdapter;
    private SwipeRefreshLayout mSwRefresh;
    private RecyclerView mRecyclerView;
    private RssPresenter presenter;


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
        presenter = new RssPresenterImpl(this);
//
//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rss, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mSwRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swRefresh);
        mAdapter = new RssItemAdapter(getActivity());
        mAdapter.setListener(this);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return (2 - position % 2);
                }
                return 1;
            }
        });

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mSwRefresh.setOnRefreshListener(this);
        mSwRefresh.setColorSchemeColors(BLUE, GREEN, RED);
        presenter.fetchRssFeed(mFeedUrl);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        presenter.fetchRssFeed(mFeedUrl);
    }

    @Override
    public void onItemSelected(RssItem rssItem) {
        Intent intent = new Intent(getContext(), DetailedNewsActivity.class);
        intent.putExtra("url", rssItem.getLink());
        startActivity(intent);
//        openFragment();
    }

    private void openFragment() {
        getFragmentManager()
                .beginTransaction()
//                .replace(R.id.main_container, new ArticleFragment())
                .replace(R.id.main_container, ArticleFragment.newInstance(mFeedUrl))
                .setCustomAnimations(0, R.anim.slide_left)
                .commit();
    }

    @Override
    public void showProgress() {
        mSwRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwRefresh.setRefreshing(false);
    }

    @Override
    public void setItems(List<RssItem> items) {
        mAdapter.setFeed(items);
        mAdapter.notifyDataSetChanged();
        if (mRecyclerView.getVisibility() != View.VISIBLE) {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }


}
