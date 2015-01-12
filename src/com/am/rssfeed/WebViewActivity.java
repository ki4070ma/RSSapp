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

	LinearLayout layout_ad;// 広告表示用スペース
	AdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		String URL = intent.getStringExtra("link");

		setContentView(R.layout.webview);

		// レイアウトで指定したWebViewのIDを指定する。
		mWebView = (WebView) findViewById(R.id.webView);

		// リンクをタップしたときに標準ブラウザを起動させない
		mWebView.setWebViewClient(new WebViewClient());

		mWebView.loadUrl(URL);

		adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-9552058847412056/1027930595");
		adView.setAdSize(AdSize.BANNER);

		layout_ad = (LinearLayout) findViewById(R.id.layout_ad);
		layout_ad.addView(adView);

		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
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
}
