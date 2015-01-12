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

	LinearLayout layout_ad;// �L���\���p�X�y�[�X
	AdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Item�I�u�W�F�N�g��ێ����邽�߂̃��X�g�𐶐����A�A�_�v�^�ɒǉ�����
		mItems = new ArrayList<Item>();
		mAdapter = new RssListAdapter(this, mItems);

		// �^�X�N���N������
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

	// ���X�g�̍��ڂ�I���������̏���
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Item item = mItems.get(position);
		// Intent intent = new Intent(this, ItemDetailActivity.class);
		Intent intent = new Intent(this, WebViewActivity.class);
		intent.putExtra("link", item.getLink());
		startActivity(intent);
	}

	// MENU�{�^�����������Ƃ��̏���
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		// �f�t�H���g�ł̓A�C�e����ǉ��������Ԓʂ�ɕ\������
		menu.add(0, MENU_ITEM_RELOAD, 0, "�X�V");
		return result;
	}

	// MENU�̍��ڂ��������Ƃ��̏���
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// �X�V
		case MENU_ITEM_RELOAD:
			// �A�_�v�^�����������A�^�X�N���N������
			mItems = new ArrayList();
			mAdapter = new RssListAdapter(this, mItems);
			// �^�X�N�͂��̓s�x��������
			RssParserTask task = new RssParserTask(this, mAdapter);
			task.execute(RSS_FEED_URL);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}