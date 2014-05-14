package com.example.gamenews;

import java.util.Arrays;
import java.util.Vector;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ReaderActivity extends FragmentActivity implements SwipeRefreshLayout.OnRefreshListener {
	
	private SwipeRefreshLayout mListViewContainer;
	RssFragment currentFragment;
	String url = "";
//    private SwipeRefreshLayout mEmptyViewContainer;
//    private ArrayAdapter<String> mAdapter;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        
        Intent intent = getIntent();
        url = intent.getStringExtra(SimplifiedSelectorActivity.EXTRA_MESSAGE);
 
//     // SwipeRefreshLayout
        final LayoutInflater factory = getLayoutInflater();

        final View fragView = factory.inflate(R.layout.fragment_reader, null);

        mListViewContainer = (SwipeRefreshLayout) fragView.findViewById(R.id.swipeRefreshLayout_reader);
//        mEmptyViewContainer = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout_emptyView);
//
//        // Configure SwipeRefreshLayout
        onCreateSwipeToRefresh(mListViewContainer);
//        onCreateSwipeToRefresh(mEmptyViewContainer);
//
//        // Adapter
//        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//        mAdapter.addAll(new Vector(Arrays.asList(getResources().getStringArray(R.array.kings))));
//
//        // ListView
//        ListView listView = (ListView) findViewById(R.id.listView);
//        listView.setEmptyView(mEmptyViewContainer);
//        listView.setAdapter(mAdapter);
        
        if (savedInstanceState == null) {
            addRssFragment();
        }
    }
 
    private void addRssFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        RssFragment fragment = new RssFragment();
        currentFragment = fragment;
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();
    }
 
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragment_added", true);
    }
    
    private void onCreateSwipeToRefresh(SwipeRefreshLayout refreshLayout) {

        refreshLayout.setOnRefreshListener(this);

        refreshLayout.setColorScheme(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            	getSupportFragmentManager()
            		.beginTransaction()
            		.detach(currentFragment)
            		.attach(currentFragment)
            		.commit();
//                // Get the last king position
//                int lastKingIndex = mAdapter.getCount() - 1;
//
//                // If there is a king
//                if(lastKingIndex > -1) {
//                    // Remove him
//                    mAdapter.remove(mAdapter.getItem(lastKingIndex));
//                    mListViewContainer.setRefreshing(false);
//                }else {
//                    // No-one there, add new ones
//                    mAdapter.addAll(new Vector(Arrays.asList(getResources().getStringArray(R.array.kings))));
//                    mEmptyViewContainer.setRefreshing(false);
//                }
//
//                // Notify adapters about the kings
//                mAdapter.notifyDataSetChanged();

            }
        }, 1000);
    }
    
    @Override
    public void onBackPressed() {
    	Intent intent = new Intent(this,SimplifiedSelectorActivity.class);
		startActivity(intent);
		finish();
    }
}