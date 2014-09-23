package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class ComposeTweet extends Activity {

	TextView tvTweetChars;
	EditText etTweetText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		loadComponents();
		registerListeners();
	}

	/**
	 * Get the tweet from text area and send back
	 */
	public void sendTweet(View v){
		//send tweet back to parentd
		Intent data = new Intent();
		data.putExtra("tweet", etTweetText.getText().toString());
		setResult(RESULT_OK, data);
		finish(); // closes the activity, pass data to parent
	}
	/**
	 * Register listeners for the views
	 */
	
	private void registerListeners() {
		etTweetText.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String tweetText = etTweetText.getText().toString();
				if(tweetText!=null){
					int remainingLength = 140 - tweetText.length();
					tvTweetChars.setText(remainingLength+"");
					if(remainingLength >=0){
						tvTweetChars.setTextColor(Color.GREEN);
					}else{
						tvTweetChars.setTextColor(Color.RED);
					}
				}
			}
			
		});
	}

	private void loadComponents() {
		// TODO Auto-generated method stub
		tvTweetChars = (TextView) findViewById(R.id.tvChars);
		tvTweetChars.setText(140+"");
		tvTweetChars.setTextColor(Color.GREEN);
		etTweetText = (EditText) findViewById(R.id.etTweet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_compose_tweet,
					container, false);
			return rootView;
		}
	}

}
