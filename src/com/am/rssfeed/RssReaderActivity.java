package com.am.rssfeed;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class RssReaderActivity extends ListActivity {
	private static final String RSS_FEED_URL = "http://www.rssmix.com/u/7400410/rss.xml";
	private RssListAdapter mAdapter;
	private ArrayList<Item> mItems;
	public static final int MENU_ITEM_RELOAD = Menu.FIRST;

	private LinearLayout mLayoutAd;
	private AdView mAdView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mItems = new ArrayList<Item>();
		mAdapter = new RssListAdapter(this, mItems);

		RssParserTask task = new RssParserTask(this, mAdapter);
		task.execute(RSS_FEED_URL);

		mAdView = new AdView(this);
		mAdView.setAdUnitId("ca-app-pub-9552058847412056/1027930595");
		mAdView.setAdSize(AdSize.BANNER);

		mLayoutAd = (LinearLayout) findViewById(R.id.layout_ad);
		mLayoutAd.addView(mAdView);

		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
	}

	@Override
	public void onPause() {
		mAdView.pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mAdView.resume();
	}

	@Override
	public void onDestroy() {
		mAdView.destroy();
		super.onDestroy();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Item item = mItems.get(position);
		Intent intent = new Intent(this, WebViewActivity.class);
		intent.putExtra("link", item.getLink());
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_ITEM_RELOAD, 0, "çXêV");
		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ITEM_RELOAD:
			mItems = new ArrayList();
			mAdapter = new RssListAdapter(this, mItems);
			RssParserTask task = new RssParserTask(this, mAdapter);
			task.execute(RSS_FEED_URL);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}