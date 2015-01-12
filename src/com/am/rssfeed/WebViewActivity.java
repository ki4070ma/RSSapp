package com.am.rssfeed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class WebViewActivity extends Activity {

	private WebView mWebView;

	private LinearLayout mLayoutAd;

	private AdView mAdView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		String URL = intent.getStringExtra("link");

		setContentView(R.layout.webview);

		mWebView = (WebView) findViewById(R.id.webView);

		mWebView.setWebViewClient(new WebViewClient());

		mWebView.loadUrl(URL);

		mAdView = new AdView(this);
		mAdView.setAdUnitId("ca-app-pub-9552058847412056/1027930595");
		mAdView.setAdSize(AdSize.BANNER);

		mLayoutAd = (LinearLayout) findViewById(R.id.layout_ad);
		mLayoutAd.addView(mAdView);

		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}

		return super.onKeyDown(keyCode, event);
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
}
