package com.codepath.apps.basictwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.fragments.HomeTimeLineFragment;
import com.codepath.apps.fragments.MentionsTimeLineFragment;
import com.codepath.apps.fragments.TweetsListFragment;

public class TimeLineActivity extends FragmentActivity {

	


	private TweetsListFragment fragmentTweetList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		setupTabs();
		//fragmentTweetList = (TweetsListFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentTimeLine);
		//loadItems();
	}

	private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        Tab tab1 = actionBar
            .newTab()
            .setText("Home")
            .setIcon(R.drawable.ic_home)
            .setTag("HomeTimelineFragment")
            .setTabListener(
                new FragmentTabListener<HomeTimeLineFragment>(R.id.flContainer, this, "first",
                		HomeTimeLineFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        Tab tab2 = actionBar
            .newTab()
            .setText("Second")
            .setIcon(R.drawable.ic_mentions)
            .setTag("MentionsTimelineFragment")
            .setTabListener(
                new FragmentTabListener<MentionsTimeLineFragment>(R.id.flContainer, this, "second",
                		MentionsTimeLineFragment.class));

        actionBar.addTab(tab2);
    }

	/**
	 * Reset and Refresh the View
	 */
/*
	
*/
	
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

	public void composeTweet(MenuItem mi){
		Intent i = new Intent(TimeLineActivity.this, ComposeTweet.class);
		//Toast.makeText(this, "Trying to go to next Intent", Toast.LENGTH_SHORT).show();
		//startActivity(i);
		startActivityForResult(i, 20);
	}

	public void onProfileView(MenuItem mi){
		Intent i = new Intent(TimeLineActivity.this, ProfileActivity.class);
		//startActivityForResult(i, 21);
		startActivity(i);
	}
	/*
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
	}*/
}
