package com.standrewsradio.star_player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*
 * from https://next-nex.info/?s=2395
 * Written by Tomonao Miura 
 * 
 */

public abstract class NetworkStatus {
	private NetworkStatus _this;
	private Context context;
	private BroadcastReceiver networkstateReciever = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// ハンドラをコールする
			onNetworkStatusChangeed(_this);
		}
	};

	//handler
	public abstract void onNetworkStatusChangeed(NetworkStatus status);

	// --------------------------
	// コンストラクタ
	// --------------------------
	public NetworkStatus(Context context) {
		super();
		this.context = context;
		this._this = this;
	}

	// --------------------
	// 監視開始
	// --------------------
	public void start() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		context.registerReceiver(this.networkstateReciever, intentFilter);
	}

	// --------------------
	// 監視終了
	// --------------------
	public void stop() {
		context.unregisterReceiver(this.networkstateReciever);
	}

	// --------------------
	// パラメータ取得
	// --------------------
	public boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return (ni != null) && ni.isConnected();
	}
}
