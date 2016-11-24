package com.voole.playerlib.view;

import java.util.List;

import android.content.Context;


public interface IVideoViewListener {
	public void onPrepared(List<PlayItem> playList, int currentIndex, int time, int duration, Context context, boolean isPreview, int previewTime);

	public void onStartPlay(List<PlayItem> playList, int currentIndex, int time, int duration, Context context, boolean isPreview);

	public void onPausePlay(List<PlayItem> playList, int currentIndex, int time, int duration, Context context, boolean isPreview);

	public void onResumePlay(List<PlayItem> playList, int currentIndex, int time, int duration, Context context, boolean isPreview);

	public void onStopPlay(List<PlayItem> playList, int currentIndex, int time, int duration, Context context, boolean isPreview);

	public void onPlayCompleted(List<PlayItem> playList, int currentIndex, int time, int duration, Context context, boolean isPreview);
	
	public void bufferStart(List<PlayItem> playList, int currentIndex, int time, int duration, Context context, boolean isPreview);

	public void bufferEnd(List<PlayItem> playList, int currentIndex, int time, int duration, Context context, boolean isPreview);

	public void onError(String errorCode, String errorCodeOther, String errorMsg);
}
