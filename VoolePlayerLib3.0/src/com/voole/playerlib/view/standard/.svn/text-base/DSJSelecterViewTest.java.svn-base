package com.voole.playerlib.view.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.voole.playerlib.R;
import com.voole.playerlib.util.DisplayUtil;
import com.voole.playerlib.util.LogVoolePlayer;
import com.voole.playerlib.view.PlayItem;

public class DSJSelecterViewTest extends RelativeLayout {
	private String TAG = DSJSelecterViewTest.class.getName();
	private Context mContext = null;
	private ImageView upArrow = null;
	private ImageView downArrow = null;
	private PlayListTextView[] textViews = null;
	private static final int ITEM_SIZE = 8;
	private int currentList = -1;
	private int currentSelectedIndex = -1;
	private int totalList = -1;	
	private List<PlayItem> items;
	
	public DSJSelecterViewTest(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public DSJSelecterViewTest(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public DSJSelecterViewTest(Context context) {
		super(context);
		init(context);
	}
	
	private void getData() {
		items = new ArrayList<PlayItem>();
		PlayItem[] item = new PlayItem[10];
		for(int i = 0 ;i < 10; i++) {
			item[i] = new PlayItem();
			item[i].contentName="第"+i+"集";
			items.add(item[i]);
		}
	}
	private void init(Context context) {
		this.mContext = context;
		setFocusable(true);
		setFocusableInTouchMode(true);
		setClickable(true);
		getData();
		upArrow = new ImageView(context);
		RelativeLayout.LayoutParams param_upArrow = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		upArrow.setId(101);
		upArrow.setBackgroundResource(R.drawable.cs_playlist_up_arrow);
		upArrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				performOperationListener();
//				previousList();
			}
		});
		param_upArrow.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		param_upArrow.addRule(RelativeLayout.CENTER_HORIZONTAL);
		addView(upArrow, param_upArrow);
		
		RelativeLayout.LayoutParams param_list = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		LinearLayout layout_list = new LinearLayout(context);
		layout_list.setOrientation(LinearLayout.VERTICAL);
		layout_list.setLayoutParams(param_list);
		param_list.addRule(RelativeLayout.BELOW, 101);
		param_list.addRule(RelativeLayout.ABOVE, 102);
		param_list.topMargin = (int) DisplayUtil.dip2px(
				5, mContext);
		param_list.bottomMargin = (int) DisplayUtil.dip2px(
				5, mContext);
		textViews = new PlayListTextView[ITEM_SIZE];
		for(int i = 0; i < ITEM_SIZE; i++) {
			textViews[i] = new PlayListTextView(mContext);
			LinearLayout.LayoutParams param_tv = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
			param_tv.topMargin = (int) DisplayUtil.dip2px(
					5, mContext);
			param_tv.gravity = Gravity.CENTER;
			textViews[i].setLayoutParams(param_tv);
			final int index = i;
			textViews[i].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					DSJSelecterViewTest.this.requestFocus();
					currentSelectedIndex = (currentList - 1) * ITEM_SIZE + index;
					updateView();
				}
			});
			layout_list.addView(textViews[i]);
		}
		addView(layout_list);
		
		RelativeLayout.LayoutParams param_downArrow = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		downArrow = new ImageView(context);
		downArrow.setId(102);
		downArrow.setBackgroundResource(R.drawable.cs_playlist_down_arrow);
		downArrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				performOperationListener();
				nextList();
			}
		});
		param_downArrow.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		param_downArrow.addRule(RelativeLayout.CENTER_HORIZONTAL);
		downArrow.setLayoutParams(param_downArrow);
		addView(downArrow);
	}
	
	private void nextList(){
		if(currentList < totalList){
			currentList ++;
		}else{
			currentList = 1;
		}
		gotoList(currentList);
	}
	
	private void gotoList(int index){
		if(index > 0 && index <= totalList){
			currentSelectedIndex = (index - 1) * ITEM_SIZE;
			updateView();
		}
	}
	
	public void setItems(List<PlayItem> items, int currentIndex){
		this.items = items;
		this.currentSelectedIndex = currentIndex;
		int totalSize = items.size();
		if(totalSize % ITEM_SIZE > 0){
			totalList = totalSize / ITEM_SIZE + 1;
		}else{
			totalList = totalSize / ITEM_SIZE ;
		}
		this.currentList = currentIndex / ITEM_SIZE + 1;
		updateView();
	}

	protected void updateView() {
		int startIndex = currentSelectedIndex /ITEM_SIZE * ITEM_SIZE;
		int endIndex = (currentSelectedIndex / ITEM_SIZE + 1) * ITEM_SIZE - 1;
		int totalIndex = items.size();
		if (endIndex >= totalIndex) {
			endIndex = totalIndex - 1;
		}
		
		for(int i = 0, index = startIndex; i < ITEM_SIZE; i++,index++) {
			if(index <= endIndex && index >= 0) {
				textViews[i].setText(items.get(index).contentName);
				if (index == currentSelectedIndex) {
					setSelected(i);
				}else {
					setUnSelected(i);
				}
			} else {
				textViews[i].setVisibility(View.INVISIBLE);
			}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		cancelHideTimer();
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_LEFT:
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			startHideTimer();
			return true;
		case KeyEvent.KEYCODE_DPAD_UP:
			LogVoolePlayer.i(TAG+"--------------currentSelectedIndex------>>"+currentSelectedIndex);
			if(currentSelectedIndex > 0){
				currentSelectedIndex --;
				updateView();
				startHideTimer();
				return true;
			}
			if (currentSelectedIndex == 0) {
				currentSelectedIndex = items.size() - 1;
				updateView();
				startHideTimer();
				return true;
			}
			return false;
		case KeyEvent.KEYCODE_DPAD_DOWN:
		LogVoolePlayer.i(TAG+"-------KEYCODE_DPAD_DOWN-------currentSelectedIndex------>>"+currentSelectedIndex);
		if (items != null && currentSelectedIndex < items.size() - 1) {
			currentSelectedIndex ++;
			updateView();
			startHideTimer();
			return true;
		}
		if (currentSelectedIndex == items.size() - 1) {
			currentSelectedIndex = 0;
			updateView();
			startHideTimer();
			return true;
		}
		return false;
			
		default:
			startHideTimer();
			return false;
		}
	}

	private Timer seekBarTimer = null;
    private HideTimerTask seekBarTimerTask = null;
    private Handler seekBarTimerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
        	DSJSelecterViewTest.this.setVisibility(View.GONE);
        }
    };
	
    
    private class HideTimerTask extends TimerTask{

		@Override
		public void run() {
			seekBarTimerHandler.sendEmptyMessage(0);
		}
    }
    public void startHideTimer(){
    	LogVoolePlayer.i(TAG+"startHideTimer---->");
    	seekBarTimer = new Timer();
    	seekBarTimerTask = new HideTimerTask();
        seekBarTimer.schedule(seekBarTimerTask, 6*1000);
    }
    public void cancelHideTimer(){
    	LogVoolePlayer.i(TAG+"cancelHideTimer---->");
        if (seekBarTimer != null) {
            seekBarTimer.cancel();
//            seekBarTimer.purge();
            seekBarTimer = null;
        }
        if (seekBarTimerTask != null) {
            seekBarTimerTask.cancel();
            seekBarTimerTask = null;
        }
    }
	
	private void setSelected(int i) {
		if (textViews[i].getVisibility()!=VISIBLE) {
			textViews[i].setVisibility(View.VISIBLE);
		}
		textViews[i].setState(1);
	}

	private void setUnSelected(int i) {
		if (textViews[i].getVisibility()!=VISIBLE) {
			textViews[i].setVisibility(View.VISIBLE);
		}
		textViews[i].setState(2);
	}
	
	private class PlayListTextView extends TextView{
		private static final int UNSELECT_PADING = 35;
		private static final int SELECTED_PADING = 50;
		private static final int maxlength = 8;
		private static final int hasFocus = 1;
		private static final int loseFocus = 2;
		private int state = loseFocus;
		public void setState(int state){
			if (this.state==state) {
				return;
			}
			this.state = state;
			updateUI();
		}
		
		public int getState(){
			return state;
		}
		
		public PlayListTextView(Context context, AttributeSet attrs,
				int defStyle) {
			super(context, attrs, defStyle);
			init(context);
		}

		public PlayListTextView(Context context, AttributeSet attrs) {
			super(context, attrs);
			init(context);
		}

		public PlayListTextView(Context context) {
			super(context);
			init(context);
		}
		
		private void init(Context context) {
			setGravity(Gravity.CENTER);
			setTextColor(Color.WHITE);
			setSingleLine(true); // 显示一行
			this.setEms(maxlength);
			setTextSize(TypedValue.COMPLEX_UNIT_PX,
					DisplayUtil.sp2px(18, mContext));
			updateUI();
		}
		
		private void updateUI(){
			if(state == loseFocus) {
				setTextSize(TypedValue.COMPLEX_UNIT_PX,
						DisplayUtil.sp2px(18, mContext));
				setBackgroundResource(R.drawable.cs_playlist_tv_bg);
				setTextColor(Color.parseColor("#f1f1f1")) ;
				this.setPadding(UNSELECT_PADING, this.getPaddingTop(), UNSELECT_PADING, this.getPaddingBottom());
				setSelected(false);
			} else if(state == hasFocus){
				setSelected(true) ;
				this.setPadding(SELECTED_PADING, this.getPaddingTop(), SELECTED_PADING, this.getPaddingBottom());
				setEllipsize(TruncateAt.MARQUEE) ;//加入跑马灯效果
				setTextSize(TypedValue.COMPLEX_UNIT_DIP, DisplayUtil.sp2px(28, mContext));
				setTextColor(Color.parseColor("#222222")) ;
				setBackgroundResource(R.drawable.cs_playlist_tv_bg_selected);
			}
			
		}
		
	}

}
