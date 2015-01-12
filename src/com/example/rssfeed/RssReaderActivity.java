package com.example.rssfeed;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

//RssReaderActivity.java
public class RssReaderActivity extends ListActivity {
	private static final String RSS_FEED_URL = "http://itpro.nikkeibp.co.jp/rss/ITpro.rdf";
	private RssListAdapter mAdapter;
	private ArrayList<Item> mItems;
	public static final int MENU_ITEM_RELOAD = Menu.FIRST;

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
	}

	// ���X�g�̍��ڂ�I���������̏���
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Item item = mItems.get(position);
		Intent intent = new Intent(this, ItemDetailActivity.class);
		intent.putExtra("TITLE", item.getTitle());
		intent.putExtra("DESCRIPTION", item.getDescription());
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