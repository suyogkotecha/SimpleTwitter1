package com.codepath.apps.basictwitter.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.DateUtils;
import android.util.Log;

public class Tweet {

	private String body;
	private long uid;
	private String createdAt;
	private User user;
	private String relativeTimeTs;
	
	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getRelativeTimeTs() {
		return relativeTimeTs;
	}

	public User getUser() {
		return user;
	}

	public static Tweet fromJson(JSONObject jsonObj){
		Tweet tweet = new Tweet();
		//extract values from json
		try{
			tweet.body = jsonObj.getString("text");
			tweet.uid = jsonObj.getLong("id");
			tweet.createdAt = jsonObj.getString("created_at");
			tweet.user = User.fromJSON(jsonObj.getJSONObject("user"));
			tweet.relativeTimeTs = getRelativeTimeAgo(tweet.createdAt);
		}catch (JSONException e){
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) {
		// TODO Auto-generated method stub
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
		try{
			for(int i=0; i< jsonArray.length();i++){
				JSONObject json = jsonArray.getJSONObject(i);
				Tweet tweet = Tweet.fromJson(json);
				if(tweet!=null){
					tweets.add(tweet);
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
		return tweets;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getBody() + " - "+this.getUser().getName();
	}
	
	public static String getRelativeTimeAgo(String rawJsonDate) {
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	 
		return relativeDate;
	}

	private static final String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
	private static final SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
}
