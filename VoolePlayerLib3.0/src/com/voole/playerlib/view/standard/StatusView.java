package com.voole.playerlib.view.standard;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gntv.tv.common.utils.DisplayManager;
import com.voole.playerlib.R;
	
public class StatusView extends RelativeLayout{
	private LinearLayout bufferLayout = null;
	private ProgressBar progressBar = null;
	private TextView txtSpeed = null;
	private TextView infoTextView = null;
	public StatusView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public StatusView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public StatusView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		bufferLayout = new LinearLayout(context);
		bufferLayout.setGravity(LinearLayout.VERTICAL);
		bufferLayout.setOrientation(LinearLayout.VERTICAL);
		bufferLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout.LayoutParams param_buffer = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		bufferLayout.setLayoutParams(param_buffer);
		bufferLayout.setBackgroundResource(R.drawable.bg_play_buffer);
		addView(bufferLayout);
		
		progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
		progressBar.setId(1001111);
		bufferLayout.addView(progressBar);
		
		txtSpeed = new TextView(context);
		txtSpeed.setTextSize(TypedValue.COMPLEX_UNIT_PX, DisplayManager.GetInstance().changeTextSize(18));
		txtSpeed.setTextColor(Color.parseColor("#f1f1f1"));
		txtSpeed.setGravity(Gravity.CENTER);
		txtSpeed.setText("0KB/s");
		bufferLayout.addView(txtSpeed);
		
		infoTextView = new TextView(context);
		infoTextView.setGravity(Gravity.CENTER);
		infoTextView.setTextColor(Color.parseColor("#f1f1f1"));
		infoTextView.setText(context.getResources().getString(R.string.play_buffer_info));
		infoTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, DisplayManager.GetInstance().changeTextSize(18));
		bufferLayout.addView(infoTextView);
	}
	
	public void setSpeedText(String speedText) {
		txtSpeed.setText(speedText);
	}

}
