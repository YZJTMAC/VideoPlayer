package com.voole.playerlib.view.standard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.voole.playerlib.R;

public class DSJItem extends RelativeLayout{
	private TextView dsjName;
	@SuppressLint("NewApi")
	public DSJItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public DSJItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public DSJItem(Context context) {
		super(context);
		init(context);
	}

	@SuppressLint("ResourceAsColor")
	private void init(Context context) {
		setGravity(Gravity.CENTER);
		dsjName = new TextView(context) ;
		dsjName.setBackgroundResource(R.drawable.cs_playlist_tv_bg);
		dsjName.setTextColor(Color.WHITE) ;
		dsjName.setTextColor(R.color.color_stat_list) ;
		dsjName.setGravity(Gravity.CENTER) ;
		
		RelativeLayout.LayoutParams topParams = new RelativeLayout.LayoutParams(
				190, LayoutParams.MATCH_PARENT) ;
		topParams.addRule(RelativeLayout.CENTER_IN_PARENT) ;
		dsjName.setTextSize(20) ;
		topParams.topMargin = 3;
		topParams.bottomMargin = 3;
		dsjName.setGravity(Gravity.CENTER);
		dsjName.setSingleLine(true) ;
		addView(dsjName,topParams) ;
		
	}
	public TextView getDsjName() {
		return dsjName;
	}
	
	public RelativeLayout getLayout() {
		return this ;
	}
	
}
