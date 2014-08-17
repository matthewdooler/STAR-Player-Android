package com.standrewsradio.star_player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

public class WebViewHandler {
	private static final String NOCONNECTIONFILE = "noconnection.html";
	private static final String DEFAULTPATH = "assets/";
	private static final String ENDLINE = "\\n";
	//class to handle webview

	private final String ENCODING = "UTF-8";
	private final String HTML = "text/html";

	WebView webview;

	private boolean initialLoad;

	private AtomicBoolean online;
	
	boolean offlinePage;

	private String defaultURL;

	public WebViewHandler(WebView webview){
		this.webview = webview;
		this.initialLoad = false;
	}

	public WebViewHandler() {
		// empty handler
	}

	public void initialisze(String defaultLoc){
		//initialise webview object
		this.update();
		this.defaultURL = defaultLoc;
		//set webview not to follow further link
		update();
	}

	public void update(){
		if(online){
			//load standrewsradio.com/_buzzbox
			if(!initialLoad){
				webview.loadUrl(defaultURL);
				initialLoad = true;
				offlinePage = false;
			}else if(offlinePage){
				webview.loadUrl(defaultURL);
			}
		}else{
			//change view to display - no connection display
			String data = loadHTMLFile(DEFAULTPATH+NOCONNECTIONFILE);
			webview.loadData(data, HTML, ENCODING);
			offlinePage = true;
		}
	}

	public String loadHTMLFile(String filename){
		File htmlFile = new File(filename);
		String stringHtml = "";
		if(htmlFile.exists()){
			BufferedReader reader = setupReader(filename);
			if(reader == null) return null;
			String temp = "";
			try {
				while((temp=reader.readLine())!=null){
					stringHtml.concat(temp+ENDLINE);
				}
			} catch (IOException e) {
				Log.e("loadingHTML", "IO Error");
			}
		}else{
			return null;
		}
		return stringHtml;
	}
	public BufferedReader setupReader(String filename){
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e){
			Log.e("setupReader", "File \"" + filename + "\" not found.");
			return null;
		}
		return reader;
	}
	
	private boolean networkStat(){
		android.net.ConnectivityManager connectivityManager =
			    (android.net.ConnectivityManager)getSystemService(android.content.Context.CONNECTIVITY_SERVICE);

			android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
			    // 接続済み
			} else {
			    // 未接続
			}
	}
}
