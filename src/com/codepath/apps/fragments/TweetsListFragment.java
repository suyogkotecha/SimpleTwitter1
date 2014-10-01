package com.codepath.apps.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public abstract class TweetsListFragment extends Fragment {

	protected JsonHttpResponseHandler handler;
	private SwipeRefreshLayout swipeContainer;
	private ArrayList<Tweet> tweets;
	protected ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	protected TwitterClient client;
	protected int since_id = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		client = TwitterApplication.getRestClient();
		handler = new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable e, String s) {
				// TODO Auto-generated method stub
				Log.d("debug",e.toString());
				Log.d("debug",s);
			}
			@Override
			public void onSuccess(JSONArray json) {
				// TODO Auto-generated method stub
				addAll(Tweet.fromJsonArray(json));
				aTweets.notifyDataSetChanged();
				since_id+=25;
				swipeContainer.setRefreshing(false);
			}
		};
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		//assign view references
		loadItems(v);
		return v;
	}

	public void loadItems(View v){
		swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
		
		swipeContainer.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				refreshTimeLineView();
			}
		});

		swipeContainer.setColorScheme(android.R.color.holo_blue_bright, 
                android.R.color.holo_green_light, 
                android.R.color.holo_orange_light, 
                android.R.color.holo_red_light);
		
		

		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				populateTimeLine();
				
			}
		});
		//populateTimeLine();
		lvTweets.setAdapter(aTweets);
	}
	public abstract void populateTimeLine();

	//delegate the adding to the itnernal adapter
	public void addAll(ArrayList<Tweet> tweets){
		aTweets.addAll(tweets);
	}
	public void refreshTimeLineView(){
		tweets.removeAll(tweets);
		since_id = 1;
		populateTimeLine();
	}
}
