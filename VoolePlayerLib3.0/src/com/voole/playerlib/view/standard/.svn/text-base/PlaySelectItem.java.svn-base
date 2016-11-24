package com.voole.playerlib.view.standard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vad.sdk.core.view.v30.AlwaysMarqueeTextView;
import com.voole.playerlib.R;

public class PlaySelectItem extends LinearLayout {

	private AlwaysMarqueeTextView tvTop ;
	private TextView tvBottom ;
	private LinearLayout layout ;

	public PlaySelectItem(Context context) {
		this(context, null);
	}

	public PlaySelectItem(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public PlaySelectItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setGravity(Gravity.CENTER_VERTICAL);
//		setBackgroundColor(Color.BLACK) ;
//		setAlpha(0.1f) ;
		setOrientation(VERTICAL);
		initView(context) ;
		//		initData(context) ;
	}

	private void initView(Context context) {
		layout = new LinearLayout(context) ;

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) ;
		layout.setLayoutParams(layoutParams) ;
		layout.setBackgroundResource(R.drawable.cs_uicore_series_view_focused) ;
		layout.setPadding(20, 10, 20, 10) ;
		layout.setOrientation(VERTICAL);

		tvTop = new AlwaysMarqueeTextView(context) ;
		tvTop.setTextColor(Color.WHITE) ;
		LinearLayout.LayoutParams topParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT) ;
		topParams.gravity = Gravity.CENTER_VERTICAL ;
		tvTop.setLayoutParams(topParams) ;
		tvTop.setTextSize(30) ;
		tvTop.setSingleLine(true) ;
		layout.addView(tvTop) ;

		tvBottom = new TextView(context) ;
		tvBottom.setTextColor(Color.rgb(164, 164, 164)) ;
		tvBottom.setTextSize(20) ;
		LinearLayout.LayoutParams bottomParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) ;
		bottomParams.gravity = Gravity.CENTER_VERTICAL ;
		tvBottom.setLayoutParams(bottomParams) ;
		tvBottom.setLines(3) ;
		layout.addView(tvBottom) ;
		
		addView(layout) ;
		tvTop.setMarquee(true) ;
	}

	public AlwaysMarqueeTextView getTvTop() {
		return tvTop;
	}

	public TextView getTvBottom() {
		return tvBottom;
	}

	public LinearLayout getLayout() {
		return this ;
	}

}
