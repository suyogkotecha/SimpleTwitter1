package com.codepath.apps.basictwitter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	Context context;
	public TweetArrayAdapter(Context context, List <Tweet> tweets) {
		super(context, 0, tweets);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//return super.getView(position, convertView, parent);
		Tweet tweet = getItem(position);
		
		//Find or inflate the template
		View v;
		if(convertView == null){
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.tweet_item, parent, false);
		}else{
			v = convertView;
		}
		final ImageView ivProfileImageView = (ImageView) v.findViewById(R.id.ivProfileImg);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvTweet);
		TextView tvRelTs = (TextView) v.findViewById(R.id.tvRelativeTimeStamp);
		ivProfileImageView.setImageResource(android.R.color.transparent);
		ivProfileImageView.setTag(tweet.getUser().getScreenName());
		ivProfileImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String tag = (String)ivProfileImageView.getTag();
				System.out.println("tag: "+ tag.substring(1));
				Intent i = new Intent(context, ProfileActivity.class);
				i.putExtra("screen_name", tag.substring(1));
				context.startActivity(i);
				
				
			}
		});
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImageView);
		tvUserName.setText(tweet.getUser().getScreenName());
		tvBody.setText(tweet.getBody());
		tvRelTs.setText(tweet.getRelativeTimeTs());
		return v;
	}
}
