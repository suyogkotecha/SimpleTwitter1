package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.fragments.UserTimeLine;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends android.support.v4.app.FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_profile);
		String screen_name = getIntent().getStringExtra("screen_name");
		
		if(screen_name == null){
			loadProfileInfo();
		}else{
			loadOtherProfile(screen_name);
		}
		
		
	}

	private void loadOtherProfile(String screen_name) {
		// TODO Auto-generated method stub
		TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject json) {
				// TODO Auto-generated method stub
				User u = User.fromJSON(json);
				Log.d("json", json.toString());
				getActionBar().setTitle(u.getScreenName());
				populateProfileHeader(u);
			}
		}, screen_name);
		
	}

	private void loadProfileInfo() {
		// TODO Auto-generated method stub
		TwitterApplication.getRestClient().getMyInfo(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject json) {
				// TODO Auto-generated method stub
				User u = User.fromJSON(json);
				getActionBar().setTitle("@"+u.getScreenName());
				populateProfileHeader(u);
			}

			
		});
	}
	private void populateProfileHeader(User u) {
		// TODO Auto-generated method stub
		TextView tvName = (TextView)findViewById(R.id.tvName);
		TextView tvTagLine = (TextView)findViewById(R.id.tvTagLine);
		TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);
		final ImageView ivPic = (ImageView)findViewById(R.id.ivProfileImage);
		ivPic.setTag(u.getScreenName());
		
		
		tvName.setText(u.getName());
		tvTagLine.setText(u.getTagLine());
		tvFollowers.setText(u.getFollowers()+" Followers   ");
		tvFollowing.setText(u.getFollowing()+" Following");
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivPic);
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
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
	 
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_profile,
					container, false);
			return rootView;
		}
	}
*/
}
