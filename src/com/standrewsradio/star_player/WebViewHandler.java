package com.standrewsradio.star_player;

import java.io.File;

import android.view.View;
import android.webkit.WebView;

public class WebViewHandler {
	private static final String NOCONNECTIONFILE = "noconnection.html";
	//class to handle webview
	
	private final String ENCODING = "UTF-8";
	private final String HTML = "text/html";

	WebView webview;
	
	boolean online;
	
	public WebViewHandler(WebView webview){
		this.webview = webview;
	}
	
	public void initialisze(){
		//initialise webview object
		this.update();
	}
	
	public void update(){
		if(online){
			//load standrewsradio.com/_buzzbox
			
		}else{
			//change view to display - no connection display
			String data = loadHTMLFile(NOCONNECTIONFILE);
			webview.loadData(data, HTML, ENCODING);
		}
	}
	
	public String loadHTMLFile(String filename){
		File htmlFile = new File(filename);
		String stringHtml = "";
		if(htmlFile.exists()){
			
		}else{
			return null;
		}
		return stringHtml;
	}
	
}
