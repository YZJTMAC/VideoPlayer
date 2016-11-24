package com.voole.playerlib.view.standard;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.voole.playerlib.view.PlayItem;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class DSJListView extends ListView {
	private DSJAdapter adapter = null;
	private int position = -1;
	private Context context;
	private List<PlayItem> items = null;
	private ImageView upArrow,downArrow;
	
	public DSJListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public DSJListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public DSJListView(Context context) {
		super(context);
		init(context);
	}

	private void init(final Context context) {
		this.context = context;
		position = 0;
		setFocusable(true);
		setFocusableInTouchMode(true);
		setClickable(true);
		setDividerHeight(0) ;
		setSelector(android.R.color.transparent) ;
		this.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(onDSJItemClickListener != null) {
					onDSJItemClickListener.onDsjItemClick((int)getAdapter().getItemId(position));
					DSJListView.this.setVisibility(INVISIBLE);
					upArrow.setVisibility(INVISIBLE);
					downArrow.setVisibility(INVISIBLE);
				}
			}
		});
		
		this.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
					DSJListView.this.position = position ;
					adapter.changeDataVisable(position);
					showArrowView() ;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		}) ;
		
	}
	
	public void setAdapterData(List<PlayItem> items, int currentPosition) {
		if(adapter == null) {
			adapter = new DSJAdapter(context, items, currentPosition);
			setAdapter(adapter);
			this.items = items ;
			int len = items.size() ;
			if (currentPosition + adapter.getItemSize() > len) {
				if (adapter.getItemSize() >= len) {
					setSelection(currentPosition) ;
				} else {
					setSelection(adapter.getItemSize() - (len-currentPosition)) ;
				}
			}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("onKey", "onKeyDown code--------------->"+keyCode);
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_DOWN:
			stopListHideTimer() ;
			startListHideTimer();
			adapter.addPosition();
			if (position == adapter.getItemSize() - 1) {
				adapter.removeTop();
				return true;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_UP:
			stopListHideTimer() ;
			startListHideTimer();
			adapter.minusPosition();
			if (position == 0) {
				adapter.addBottom();
				return true;
			}
			break;
		case KeyEvent.KEYCODE_BACK:
			stopListHideTimer() ;
			if (getVisibility() == INVISIBLE) {
				break ;
			} else {
				setVisibility(INVISIBLE) ;
				upArrow.setVisibility(View.INVISIBLE);
				downArrow.setVisibility(View.INVISIBLE);
				return true ;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private OnDSJItemClickListener onDSJItemClickListener = null;
	
	public OnDSJItemClickListener getOnDSJItemClickListener() {
		return onDSJItemClickListener;
	}

	public void setOnDSJItemClickListener(
			OnDSJItemClickListener onDSJItemClickListener) {
		this.onDSJItemClickListener = onDSJItemClickListener;
	}

	public interface OnDSJItemClickListener{
		void onDsjItemClick(int positionIndex);
	}

	public void setArrowView(ImageView upArrow, ImageView downArrow) {
		this.upArrow = upArrow;
		this.downArrow = downArrow;
	}
	
	public void showArrowView() {
		if (adapter != null) {
			if (items != null && items.size() <= adapter.getItemSize()) {
				if (upArrow != null && upArrow.getVisibility() == VISIBLE ) {
					upArrow.setVisibility(View.INVISIBLE) ;
				}
				
				if (downArrow != null &&  downArrow.getVisibility() == VISIBLE) {
					downArrow.setVisibility(View.INVISIBLE) ;
				}
			} else {
				if (adapter.getItemId(position) - position == 0) {
					if (upArrow.getVisibility() == View.VISIBLE) {
						upArrow.setVisibility(View.INVISIBLE) ;
					}
					if (downArrow.getVisibility() == View.INVISIBLE) {
						downArrow.setVisibility(View.VISIBLE) ;
					}
				} else if (adapter.getItemId(position) + (adapter.getItemSize() - position - 1) == items.size() - 1) {
					if (downArrow.getVisibility() == View.VISIBLE) {
						downArrow.setVisibility(View.INVISIBLE) ;
					}
					if (upArrow.getVisibility() == View.INVISIBLE) {
						upArrow.setVisibility(View.VISIBLE) ;
					}
				} else {
					upArrow.setVisibility(View.VISIBLE) ;
					downArrow.setVisibility(View.VISIBLE) ;
				}
			}
		}
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			//        	VoolePlayerListView.this.setVisibility(View.GONE);
			switch (msg.what) {
			case 0:
				setVisibility(View.INVISIBLE) ;
				if (upArrow != null && upArrow.getVisibility() == VISIBLE ) {
					upArrow.setVisibility(View.INVISIBLE) ;
				}
				
				if (downArrow != null &&  downArrow.getVisibility() == VISIBLE) {
					downArrow.setVisibility(View.INVISIBLE) ;
				}
				listHideTimer = null ;
				listHideTimerTask = null ;
				break;
			default:
				break;
			}
		}
	};
	private Timer listHideTimer = null;
	private TimerTask listHideTimerTask = null;

	public void startListHideTimer() {
		if (listHideTimer == null) {
			listHideTimer = new Timer();
		}
		if (listHideTimerTask == null) {
			listHideTimerTask = new TimerTask() {
				@Override
				public void run() {
					handler.sendEmptyMessage(0);
				}
			};
			listHideTimer.schedule(listHideTimerTask, 7000);
		}
	}

	public void stopListHideTimer() {
		if (listHideTimer != null) {
			listHideTimer.cancel();
			listHideTimer = null;
		}
		if (listHideTimerTask != null) {
			listHideTimerTask.cancel();
			listHideTimerTask = null;
		}
	}

	
}
