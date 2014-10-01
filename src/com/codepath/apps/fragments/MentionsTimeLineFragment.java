package com.codepath.apps.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimeLineFragment extends TweetsListFragment {

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		populateTimeLine();
	}
	public void populateTimeLine(){
		
		Log.d("since","mention:"+since_id);
		client.getMentionsTimeLine(handler, since_id);
	}
}


