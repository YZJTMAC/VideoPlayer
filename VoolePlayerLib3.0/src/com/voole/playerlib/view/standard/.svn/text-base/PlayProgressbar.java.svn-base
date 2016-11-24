package com.voole.playerlib.view.standard;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voole.playerlib.R;
import com.voole.playerlib.util.DisplayUtil;
import com.voole.playerlib.util.LogVoolePlayer;
import com.voole.playerlib.util.VoolePlayerUtil;
/**
 * player progress view
 * @author Administrator
 *
 */
public class PlayProgressbar extends LinearLayout{
	private String TAG = PlayProgressbar.class.getName();

	    // 快进递增时间
    private static final int FAST_TO_TIME = 1000 * 30;
	
	private int measuredWidthText;

	private ProgressView progressBar;

	private LayoutParams buttonParms;

	private TextView timeView;

	private OnKeyListener listener;
	private boolean direction = false;
	private boolean isMouseSeek = false;


	public PlayProgressbar(Context context) {
		super(context);
		init(context);
	}

	public PlayProgressbar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public void setSeekOnkeyListener(OnKeyListener listener){
		this.listener = listener;
	}
	
	
	private void init(Context context) {
		this.setOrientation(LinearLayout.VERTICAL);
		timeView = new TextView(context);
//		this.setClickable(true);
		timeView.setBackgroundResource(R.drawable.progress_text_bg);
//		button.requestFocus();
		timeView.setTextSize(TypedValue.COMPLEX_UNIT_PX, DisplayUtil.sp2px(20, context));
		timeView.setTextColor(Color.parseColor("#222222"));
		timeView.setGravity(Gravity.CENTER_VERTICAL) ;
		buttonParms = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		timeView.setText("00:00:00");
		this.addView(timeView,buttonParms);
		timeView.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (listener!=null) {
					return listener.onKey(v, keyCode, event);
				}else{
					return true;
				}
			}
		});
		progressBar = new ProgressView(context);
		LinearLayout.LayoutParams parmas = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		this.addView(progressBar,parmas);
		timeView.setLayoutParams(buttonParms);
		timeView.setPadding((int)DisplayUtil.px2dip(18, context), timeView.getPaddingTop(), (int)DisplayUtil.px2dip(18, context), timeView.getPaddingBottom());
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (this.getChildCount()==2) {
			View child0 = this.getChildAt(0);
			View child1 = this.getChildAt(1);
			measuredWidthText = child0.getMeasuredWidth();
			LayoutParams child1Params = (LayoutParams) child1.getLayoutParams();
			if (child1Params.leftMargin==0) {
				System.out.println("leftMargin"+child1Params.leftMargin);
				child1Params.leftMargin +=measuredWidthText/2;
				child1Params.rightMargin +=measuredWidthText/2;
				System.out.println("leftMargin"+child1Params.leftMargin);
				child1.setLayoutParams(child1Params);
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	
	public void setMax(long max){
		progressBar.setMax(max);
	}
	public long getMax(){
		return progressBar.getMax();
	}
	public void setProgress(long progress){
		int measuredWidth = progressBar.getProgressWidth();
//			double currProgress =  progressBar.getProgress();
	
		double speed = measuredWidth*1.0/progressBar.getMax();
		int leftMargin = (int) (speed*progress);
		progressBar.setProgress(progress,isMouseSeek);
		if (leftMargin>=0&&leftMargin<measuredWidth) {
			String currentTime = VoolePlayerUtil.secondToString((int)progress/1000);
			timeView.setText(currentTime);
			buttonParms.leftMargin =leftMargin;
			timeView.setLayoutParams(buttonParms);
		}
	}
	public double getProgress(){
		return progressBar.getProgress();
	}
	
	/**
	 * 
	 * @param progress
	 * @param direction true 快进
	 */
	public void setProgress(long progress,boolean direction){
		LogVoolePlayer.d("-------> progress "+ progress + " isMouseSeek " + isMouseSeek);
		setProgress(progress);
		timeView.setVisibility(VISIBLE);
		String currentTime = VoolePlayerUtil.secondToString((int)progress/1000);
		timeView.setText(currentTime);
	}
	
	public void setSeek(boolean isMouseSeek) {
		this.isMouseSeek = isMouseSeek;
	}

	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
//		button.setVisibility(visibility);
	}
	
	public void showSeekInfo(int visible){
		if (visible==VISIBLE) {
			this.setVisibility(visible);
			timeView.setVisibility(visible);
		}else{
			timeView.setVisibility(visible);
		}
	}

	public ProgressView getProgressView() {
		return progressBar;
	}
}
