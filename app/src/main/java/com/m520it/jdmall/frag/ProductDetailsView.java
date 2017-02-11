package com.m520it.jdmall.frag;

import com.m520it.jdmall.R;
import com.m520it.jdmall.activity.ProductDetailsActivity;
import com.m520it.jdmall.cons.NetworkConst;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ProductDetailsView extends Fragment {
	
	private String mProductId;
	private WebView mWebView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.product_details_view,null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mProductId = ((ProductDetailsActivity)getActivity()).mProductId;
		mWebView=(WebView)getActivity().findViewById(R.id.webview);
		mWebView.loadUrl(NetworkConst.LOAD_PRODUCTDETAILS_URL+mProductId);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient(){
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			
		});
	}
	
}
