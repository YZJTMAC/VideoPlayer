package com.voole.playerlib.view.standard;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.voole.playerlib.R;

public class PlaySeriesScrollBar extends RelativeLayout  {
	
	private View vSelected ;
	private View vTotal ;

	public PlaySeriesScrollBar(Context context) {
		this(context, null);
	}
	public PlaySeriesScrollBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public PlaySeriesScrollBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context) ;
	}
	@SuppressWarnings("deprecation")
	private void init(Context context) {
		RelativeLayout.LayoutParams totalParams = new RelativeLayout.LayoutParams(
				7,
				RelativeLayout.LayoutParams.WRAP_CONTENT) ;
		vTotal = new View(context) ;
		Drawable totalDrawable = context.getResources().getDrawable(R.drawable.scrollsharp) ;
		vTotal.setBackgroundDrawable(totalDrawable);
		vTotal.setLayoutParams(totalParams) ;
		addView(vTotal) ;
		
		RelativeLayout.LayoutParams selectedParams = new RelativeLayout.LayoutParams(
				7,
				RelativeLayout.LayoutParams.WRAP_CONTENT) ;
		vSelected = new View(context) ;
		Drawable selectedDrawable = context.getResources().getDrawable(R.drawable.scrollsharp_selected) ;
		vSelected.setBackgroundDrawable(selectedDrawable) ;
		vSelected.setLayoutParams(selectedParams) ;
		addView(vSelected) ;
	}
	
	public View getSelectedView() {
		return vSelected ;
	}
	
	@SuppressLint("NewApi")
	public void setVScroll(int distance) {
		ObjectAnimator animator = ObjectAnimator.ofFloat(vSelected, "TranslationY",distance).setDuration(300);
		animator.start() ;
	}
	
}
