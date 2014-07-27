package com.standrewsradio.star_player;

import java.io.IOException;
import java.io.InputStream;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener {
	
	private String TAG = getClass().getSimpleName();
	String serverAddr = "http://stream.standrewsradio.com:8080/stream/1/";
	private MediaPlayer mediaPlayer = null;
	boolean playing = false;
	boolean loading = false;// TODO: loading icon. it's easy, srsly.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
	    // Begin playback
	    // TODO: maybe not here?? it blocks! esp. if no internets..
	    // TODO: test what happens when not connuctud
		//play();
		
	    //InputStream stream = null;
	    //try {
	     //  stream = getAssets().open("loading_black.gif");
	    //} catch (IOException e) {
	      //e.printStackTrace();
	    //}
	    //GifMovieView view = new GifMovieView(this, stream);
	    //setContentView(view);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void playbackButtonHandler(View v){
	    if(v.getId() == R.id.imageButton1){
	    	togglePlayback();
	    }
	}
	
	private synchronized void togglePlayback() {
		if(!playing) {
			// Not playing, so start playback
			play();
    		playing = true;
    		//R.id.imageButton1.setImageResource(R.drawable.);
    		findViewById(R.id.imageButton1).setBackgroundResource(R.drawable.pause_button_small);
    	} else {
    		// Already playing, so stop playback
    		stop();
    		playing = false;
    		//R.id.imageButton1.setImageResource(R.drawable.ic_launcher_play);
    		findViewById(R.id.imageButton1).setBackgroundResource(R.drawable.play_button_small);
    	}
	}
	
	private void play() {
		Uri serverUri = Uri.parse(serverAddr);
		try {
			if(mediaPlayer == null) {
				mediaPlayer = new MediaPlayer();
			} else {
				mediaPlayer.stop();
				mediaPlayer.reset();
			}
			mediaPlayer.setDataSource(this, serverUri);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnBufferingUpdateListener(this);

			mediaPlayer.setOnErrorListener(this);
			mediaPlayer.prepareAsync();

			//Log.d(TAG, "LoadClip Done");
		} catch (Throwable t) {
			Log.d(TAG, t.toString());
		}
	}

	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int percent) {
		//Log.d(TAG, "PlayerService onBufferingUpdate : " + percent + "%");
	}

	@Override
	public boolean onError(MediaPlayer arg0, int what, int extra) {
		StringBuilder sb = new StringBuilder();
		sb.append("Media Player Error: ");
		switch (what) {
		case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
			sb.append("Not Valid for Progressive Playback");
			break;
		case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
			sb.append("Server Died");
			break;
		case MediaPlayer.MEDIA_ERROR_UNKNOWN:
			sb.append("Unknown");
			break;
		default:
			sb.append(" Non standard (");
			sb.append(what);
			sb.append(")");
		}
		sb.append(" (" + what + ") ");
		sb.append(extra);
		Log.d(TAG, sb.toString());
		return true;
	}

	@Override
	public void onPrepared(MediaPlayer arg0) {
		mediaPlayer.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stop();
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		stop();
	}

	private void stop() {
		mediaPlayer.stop();
	}
	
	//build a method that handles web-view
	public void getBuzzBox(){
		WebView webview = new WebView(this);
		setContentView(webview);
		
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		
		webview.getSettings().setJavaScriptEnabled(true);
		
		final Activity activity = this;
		
		webview.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				activity.setProgress(progress * 1000);
			}
		});
		webview.setWebViewClient(new WebViewClient(){
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
				Toast.makeText(activity, "Can't load websites.", Toast.LENGTH_SHORT).show();
		
			}
		});
		
		webview.loadUrl("https://standrewsradio.com/_buzzbox");
		
		
	}
}
