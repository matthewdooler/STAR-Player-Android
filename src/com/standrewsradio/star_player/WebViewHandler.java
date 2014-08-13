package com.standrewsradio.star_player;

import android.view.View;

public class WebViewHandler {
	//class to handle webview
	
	View view;
	
	View webview;
	
	boolean online;
	
	public WebViewHandler(View webview){
		this.webview = webview;
	}
	
	public void initialisze(){
		//initialise webview object
	}
	
	public void update(){
		if(online){
			//load standrewsradio.com/_buzzbox
			
		}else{
			//change view to display - no connection display
			
		}
	}
	
}
