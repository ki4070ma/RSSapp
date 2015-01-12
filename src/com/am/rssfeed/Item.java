package com.am.rssfeed;

public class Item {
	private CharSequence mTitle;
	private CharSequence mSite;
	private CharSequence mDate;
	private CharSequence mLink;

	public Item() {
		mTitle = "";
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

	public CharSequence getSite() {
		return mSite;
	}

	public void setSite(CharSequence site) {
		mSite = site;
	}

	public CharSequence getDate() {
		return mDate;
	}

	public void setDate(CharSequence date) {
		mDate = date;
	}
}
