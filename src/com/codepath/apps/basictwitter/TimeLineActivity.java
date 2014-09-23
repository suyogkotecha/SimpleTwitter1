package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimeLineActivity extends Activity {

	private SwipeRefreshLayout swipeContainer;
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	private int since_id = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		loadItems();
	}

	/**
	 * Reset and Refresh the View
	 */

	public void refreshTimeLineView(){
		tweets.removeAll(tweets);
		since_id = 1;
		populateTimeLine();
	}

	public void loadItems(){
		swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
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
		
		
	
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				populateTimeLine();
				
			}
		});
		
		
		
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		client = TwitterApplication.getRestClient();
		populateTimeLine();
		lvTweets.setAdapter(aTweets);
	}
	public void populateTimeLine(){
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable e, String s) {
				// TODO Auto-generated method stub
				Log.d("debug",e.toString());
				Log.d("debug",s);
			}
			@Override
			public void onSuccess(JSONArray json) {
				// TODO Auto-generated method stub
				aTweets.addAll(Tweet.fromJsonArray(json));
				aTweets.notifyDataSetChanged();
				since_id+=25;
				swipeContainer.setRefreshing(false);
			}
		}, since_id);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time_line, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_time_line,
					container, false);
			return rootView;
		}
	}

	public void composeTweet(MenuItem mi){
		Intent i = new Intent(TimeLineActivity.this, ComposeTweet.class);
		//Toast.makeText(this, "Trying to go to next Intent", Toast.LENGTH_SHORT).show();
		//startActivity(i);
		startActivityForResult(i, 20);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == RESULT_OK && requestCode == 20){
			//compose Tweet:
			String text = data.getStringExtra("tweet");
			if(text!=null && text.length()>0){
				client.postStatus(new JsonHttpResponseHandler(){
					@Override
					public void onFailure(Throwable arg0) {
						// TODO Auto-generated method stub
						super.onFailure(arg0);
					}
					
					@Override
					public void onSuccess(JSONObject arg0) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0);
						refreshTimeLineView();
					}
				}, text);
				//tweet
				//reset the adapter
				//refresh the view
			}
		} else if(resultCode == RESULT_OK ){
			refreshTimeLineView();
		}
	}
}
