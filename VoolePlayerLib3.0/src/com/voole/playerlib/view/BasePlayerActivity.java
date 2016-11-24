package com.voole.playerlib.view;

import com.voole.player.lib.core.VooleMediaPlayer;
import com.voole.playerlib.interfaces.IControllerView;
import com.voole.playerlib.interfaces.ISelecterView;
import com.voole.playerlib.interfaces.IStatusView;
import com.voole.playerlib.interfaces.ITitleView;

import android.app.Activity;
import android.os.Bundle;

public abstract class BasePlayerActivity extends Activity{
	protected MediaController mMediaController = null;
	protected UIController mUiController = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initContentView();
		initUIController();
		initMediaController();
	}
	
	private void initUIController(){
		mUiController = new UIController(getTitleView(), getStatusView(), getControllerView(), getSelecterView());
	}
	
	private void initMediaController(){
		mMediaController = new MediaController(getMediaPlayer());
	}
	
	private void getData(){
		
	}
	
	public abstract void initContentView();
	public abstract ITitleView getTitleView();
	public abstract IStatusView getStatusView();
	public abstract ISelecterView getSelecterView();
	public abstract IControllerView getControllerView();
	public abstract VooleMediaPlayer getMediaPlayer();
	
}
