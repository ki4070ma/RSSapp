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

//RssReaderActivity.java
public class RssReaderActivity extends ListActivity {
	// private static final String RSS_FEED_URL =
	// "http://itpro.nikkeibp.co.jp/rss/ITpro.rdf";
	private static final String RSS_FEED_URL = "http://www.rssmix.com/u/7347120/rss.xml";
	private RssListAdapter mAdapter;
	private ArrayList<Item> mItems;
	public static final int MENU_ITEM_RELOAD = Menu.FIRST;

	LinearLayout layout_ad;// 広告表示用スペース
	AdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Itemオブジェクトを保持するためのリストを生成し、アダプタに追加する
		mItems = new ArrayList<Item>();
		mAdapter = new RssListAdapter(this, mItems);

		// タスクを起動する
		RssParserTask task = new RssParserTask(this, mAdapter);
		task.execute(RSS_FEED_URL);

		adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-9552058847412056/1027930595");
		adView.setAdSize(AdSize.BANNER);

		layout_ad = (LinearLayout) findViewById(R.id.layout_ad);
		layout_ad.addView(adView);

		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}

	@Override
	public void onPause() {
		adView.pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		adView.resume();
	}

	@Override
	public void onDestroy() {
		adView.destroy();
		super.onDestroy();
	}

	// リストの項目を選択した時の処理
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Item item = mItems.get(position);
		// Intent intent = new Intent(this, ItemDetailActivity.class);
		Intent intent = new Intent(this, WebViewActivity.class);
		intent.putExtra("link", item.getLink());
		startActivity(intent);
	}

	// MENUボタンを押したときの処理
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		// デフォルトではアイテムを追加した順番通りに表示する
		menu.add(0, MENU_ITEM_RELOAD, 0, "更新");
		return result;
	}

	// MENUの項目を押したときの処理
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// 更新
		case MENU_ITEM_RELOAD:
			// アダプタを初期化し、タスクを起動する
			mItems = new ArrayList();
			mAdapter = new RssListAdapter(this, mItems);
			// タスクはその都度生成する
			RssParserTask task = new RssParserTask(this, mAdapter);
			task.execute(RSS_FEED_URL);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}