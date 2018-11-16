package com.android.mangaliso.news_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String TAG = NewsActivity.class.getName();
    private NewsAdapter mNewsAdapter;
    private TextView mEmptyStateTextView;
    private static final int NEWS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView newsListView = findViewById(R.id.list);
        mEmptyStateTextView = findViewById(R.id.empty_state_view);
        mNewsAdapter = new NewsAdapter(this, new ArrayList<News>());

        newsListView.setAdapter(mNewsAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                News currentNews = mNewsAdapter.getItem(position);
                assert currentNews != null;
                Uri newsUri = Uri.parse(currentNews.getUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(webIntent);
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            View loading = findViewById(R.id.loading);
            loading.setVisibility(View.GONE);
            mEmptyStateTextView.setText(getString(R.string.no_internet));
        }
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, @Nullable Bundle bundle) {

        Uri.Builder builtUri = new Uri.Builder();
        builtUri.scheme("https")
                .authority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("q","technology")
                .appendQueryParameter("api-key","test");
        String stringUrl = builtUri.toString();
        Log.d(TAG,"The built string is "+stringUrl);
        return new NewsLoader(this, stringUrl);
    }


    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> news) {

        View loading = findViewById(R.id.loading);
        loading.setVisibility(View.GONE);

        if (news != null && !news.isEmpty()) {
            mNewsAdapter.clear();
            mNewsAdapter.addAll(news);
        }
        else {
            mEmptyStateTextView.setText(getString(R.string.no_news));
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        mNewsAdapter.clear();
    }
}
