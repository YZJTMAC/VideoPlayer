package com.voole.playerlib.view.standard;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.voole.playerlib.view.PlayItem;

public class FilmSelecterView extends RelativeLayout{

	private PlaySeriesScrollBar scrollBar ;
	private PlaySeriesListView lView ;
	private TextView tv ;

	public FilmSelecterView(Context context) {
		this(context, null);
	}

	public FilmSelecterView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FilmSelecterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context) ;
	}

	private void init(Context context) {
		//		setPadding(20, 0, 20, 0) ;
		//		setBackgroundColor(Color.parseColor("#55888888"));
		scrollBar = new PlaySeriesScrollBar(context) ;
		scrollBar.setId(0x002211) ;
		RelativeLayout.LayoutParams barParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT) ;
		barParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT) ;
		barParams.setMargins(0, 20, 20, 20) ;
		scrollBar.setLayoutParams(barParams) ;
		scrollBar.setVisibility(View.INVISIBLE) ;
		addView(scrollBar) ;

		lView = new PlaySeriesListView(context) ;
		RelativeLayout.LayoutParams lvParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT) ;
		lvParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT) ;
		lView.setPadding(40, 0, 50, 0) ;
		lView.setLayoutParams(lvParams) ;
		lView.setVisibility(View.INVISIBLE) ;
		addView(lView) ;

		tv = new TextView(context) ;
		RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT) ;
		tvParams.addRule(RelativeLayout.CENTER_IN_PARENT) ;
		tv.setTextSize(35) ;
		tv.setGravity(Gravity.CENTER) ;
		tv.setTextColor(Color.WHITE) ;
		tv.setLayoutParams(tvParams) ;
		tv.setText("数据加载中...") ;
		tv.setBackgroundColor(Color.parseColor("#55888888"));
		addView(tv) ;
	}

	public PlaySeriesScrollBar getVScrollBar() {
		return scrollBar ;
	}

	public PlaySeriesListView getVListView() {
		return lView ;
	}

	private int totalPagers ;
	private int showItemSize = 6 ;

	public void setData(List<PlayItem> items,int totalSize,int currentPosition) {
		lView.setScrollBar(scrollBar) ;
		if (totalSize % showItemSize == 0) {
			totalPagers = totalSize/showItemSize ;
		} else {
			totalPagers = totalSize/showItemSize + 1 ;
		}
		lView.setVAdapter(items, totalPagers, currentPosition % items.size()) ;
		tv.setVisibility(View.INVISIBLE) ;
		if (getVisibility() == VISIBLE) {
			lView.startListHideTimer();
			lView.setVisibility(VISIBLE) ;
			lView.setFocusable(true);
			lView.requestFocusFromTouch();
			if (totalPagers > 1) {
				scrollBar.setVisibility(VISIBLE) ;
			}
			requestFocus();
		}
	}

	public void showFilmSelecterView() {
		setVisibility(VISIBLE) ;
		if (tv.getVisibility() == INVISIBLE) {
			lView.startListHideTimer();
			lView.setVisibility(VISIBLE) ;
			if (totalPagers > 1) {
				scrollBar.setVisibility(VISIBLE) ;
			}
			requestFocus();
		} else {
			requestFocus();
			setFocusable(true);
			requestFocusFromTouch();
		}
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (getVisibility() == VISIBLE) {
				setVisibility(INVISIBLE);
				return true;
			}

			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

}
