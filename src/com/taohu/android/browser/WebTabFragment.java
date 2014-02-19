package com.taohu.android.browser;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebTabFragment extends Fragment {

	private WebView wv;
	private boolean isCreated = false;
	//private Bundle wvBundle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d("Tab", "onCreate()" + this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = null;
		if (!isCreated) {
			isCreated = true;
			v = inflater.inflate(R.layout.webtab_fragment, container, false);
			wv = (WebView) v.findViewById(R.id.wv_webtab);
			wv.setWebViewClient(new WebViewClient());
			setWebViewCache();
			// wv = new WebView(this.getActivity());
			// if (wvBundle != null) {
			// wv.restoreState(wvBundle);
			// Log.d("fragment", "onCreateView() " + this.getTag() +
			// " bundle not null");
			// } else {
			String url = this.getArguments().getString(AppSettings.TAB_TITLE);
			wv.loadUrl(url);
			Log.d("Tab", "onCreateView() " + this);
			// }
		}
		return v;
	}

	// @Override
	// public void onActivityCreated(Bundle savedInstanceState) {
	// super.onActivityCreated(savedInstanceState);
	// wv.restoreState(savedInstanceState);
	// Log.d("fragment", "onActivityCreated() " + this.getTag());
	// }
	//
	// @Override
	// public void onPause() {
	// super.onPause();
	// wvBundle = new Bundle();
	// wvBundle.putBoolean(AppSettings.TAB_HAS_LOADED, true);
	// }

	// @Override
	// public void onSaveInstanceState(Bundle outState) {
	// super.onSaveInstanceState(outState);
	// wv.saveState(outState);
	// Log.d("fragment", "onSaveInstanceState() " + this.getTag());
	// }

	public void setWebViewCache() {
		wv.getSettings().setAppCacheEnabled(true);
		wv.getSettings().setAppCachePath(this.getActivity().getCacheDir().getAbsolutePath());
		wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		// wv.getSettings().setJavaScriptEnabled(true);
	}

	public WebView getWebView() {
		return wv;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.d("Tab", "onDetach() " + this);
	}

	public class WebTabClient extends WebViewClient {

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			// how to pass favicon to tabhost?
		}

	}

}
