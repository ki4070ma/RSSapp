package com.am.rssfeed;

//Item.java
public class Item {
	// 記事のタイトル
	private CharSequence mTitle;
	// 記事の本文
	private CharSequence mDescription;
	//
	private CharSequence mLink;

	public Item() {
		mTitle = "";
		mDescription = "";
		mLink = "";
	}

	public CharSequence getDescription() {
		return mDescription;
	}

	public void setDescription(CharSequence description) {
		mDescription = description;
	}

	public CharSequence getTitle() {
		return mTitle;
	}

	public void setTitle(CharSequence title) {
		mTitle = title;
	}

	public void setLink(CharSequence link) {
		mLink = link;
	}

	public CharSequence getLink() {
		return mLink;
	}
}
