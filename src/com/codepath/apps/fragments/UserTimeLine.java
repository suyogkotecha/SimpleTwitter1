package com.codepath.apps.fragments;

import org.json.JSONArray;
import org.json.JSONObject;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;

public class UserTimeLine extends TweetsListFragment {

	public static UserTimeLine newInstance(String user){
		UserTimeLine userTimeLine = new UserTimeLine();
		Bundle args = new Bundle();
		args.putString("screen_name", user);
		userTimeLine.setArguments(args);
		return userTimeLine;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//String screen_name = getArguments().getString("screen_name","");
		
			
		
		TwitterApplication.getRestClient().getUserTimeLine(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray json) {
				// TODO Auto-generated method stub
				addAll(Tweet.fromJsonArray(json));
				
			}
		});
		
	}
	@Override
	public void populateTimeLine() {
		// TODO Auto-generated method stub
		
	}

}
