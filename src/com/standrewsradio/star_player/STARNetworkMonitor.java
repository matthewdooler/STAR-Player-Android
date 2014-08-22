package com.standrewsradio.star_player;

import android.content.Context;
import android.webkit.WebView;

public class STARNetworkMonitor extends NetworkStatus {

	WebView webview;
	WebViewHandler handler;
	
	public STARNetworkMonitor(Context context, WebView webview) {
		super(context);
 		this.webview = webview;		
	}

	@Override
	public void onNetworkStatusChangeed(NetworkStatus status) {
		handler.update(status.isConnected());
	}

}
