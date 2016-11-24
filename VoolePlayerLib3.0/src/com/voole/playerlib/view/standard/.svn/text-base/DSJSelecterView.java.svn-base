package com.voole.playerlib.view.standard;

import com.voole.playerlib.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class DSJSelecterView extends RelativeLayout{
	private DSJListView lView = null;
	private Context mContext = null;
	private ImageView upArrow, downArrow;
	public DSJSelecterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public DSJSelecterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public DSJSelecterView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.mContext = context;
		RelativeLayout.LayoutParams param_upArrow = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		upArrow = new ImageView(mContext);
		upArrow.setId(101);
		upArrow.setBackgroundResource(R.drawable.cs_playlist_up_arrow);
		param_upArrow.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		param_upArrow.addRule(RelativeLayout.CENTER_HORIZONTAL);
		upArrow.setLayoutParams(param_upArrow);
		addView(upArrow);
		
		
		lView = new DSJListView(context) ;
		RelativeLayout.LayoutParams lvParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT) ;
		lvParams.addRule(RelativeLayout.BELOW, 101);
		lvParams.addRule(RelativeLayout.ABOVE, 102);
		lvParams.addRule(RelativeLayout.CENTER_VERTICAL);
//		lvParams.addRule(RelativeLayout.LEFT_OF,0x002211) ;
		lvParams.setMargins(20, 10, 10, 0) ;
		lView.setLayoutParams(lvParams) ;
		addView(lView) ;
		
		RelativeLayout.LayoutParams param_downArrow = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		downArrow = new ImageView(mContext);
		downArrow.setId(102);
		downArrow.setBackgroundResource(R.drawable.cs_playlist_down_arrow);
		param_downArrow.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		param_downArrow.addRule(RelativeLayout.CENTER_HORIZONTAL);
		downArrow.setLayoutParams(param_downArrow);
		addView(downArrow);
		lView.setArrowView(upArrow,downArrow);
	}
	
	public DSJListView getVListView() {
		return lView ;
	}
	
	public void showDSJSelecterView() {
		setVisibility(View.VISIBLE);
		lView.setVisibility(VISIBLE) ;
		lView.startListHideTimer() ;
//		upArrow.setVisibility(VISIBLE);
//		downArrow.setVisibility(VISIBLE);
		lView.showArrowView() ;
		requestFocus();
	}

}
