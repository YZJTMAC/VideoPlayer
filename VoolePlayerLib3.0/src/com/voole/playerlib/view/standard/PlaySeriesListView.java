package com.voole.playerlib.view.standard;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.voole.playerlib.R;
import com.voole.playerlib.view.PlayItem;

public class PlaySeriesListView extends ListView {

	private PlayVooleAdapter vAdapter;
	private int position = -1;
	private Context context;
	private OnVooleItemSelectedListener vListener;
	private PlaySeriesScrollBar scrollBar;
	private int total = 1;

	public void setOnVooleItemSelectedListener(OnVooleItemSelectedListener vListener) {
		if (vListener != null) {
			this.vListener = vListener;
		}
	}

	public PlaySeriesListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		position = 0;
		setFocusable(true);
		setFocusableInTouchMode(true);
		setClickable(true);
		this.context = context;
		Drawable drawable = context.getResources().getDrawable(
				R.drawable.cs_uicore_series_view_divider);
		setDivider(drawable);
		setSelector(android.R.color.transparent);
		setBackgroundColor(Color.parseColor("#55888888"));
	}

	public PlaySeriesListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PlaySeriesListView(Context context) {
		this(context, null, 0);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		this.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				PlaySeriesListView.this.vAdapter.changeDataVisable(position);
				PlaySeriesListView.this.position = position;
				if (vListener != null) {
					vListener.onItemSelected(parent, view, position, id);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				if (vListener != null) {
					vListener.onNothingSelected(parent);
				}
			}
		});

		this.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mListener != null) {
					Log.e("TAG", "playListView play") ;
					mListener.onFilmItemClick(vAdapter.getItem(position), (int)vAdapter.getItemId(position)) ;
					PlaySeriesListView.this.setVisibility(INVISIBLE);
					scrollBar.setVisibility(INVISIBLE);
				}
			}
		}) ;

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_DOWN:
			stopListHideTimer() ;
			startListHideTimer();
			vAdapter.addPosition();
			if (position == vAdapter.getItemSize() - 1) {
				vAdapter.removeTop();
				return true;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_UP:
			stopListHideTimer() ;
			startListHideTimer();
			vAdapter.minusPosition();
			if (position == 0) {
				vAdapter.addBottom();
				return true;
			}
			break;
		case KeyEvent.KEYCODE_BACK:
			stopListHideTimer() ;
			if (getVisibility() == INVISIBLE) {
				break ;
			} else {
				setVisibility(INVISIBLE) ;
				if (scrollBar != null) {
					scrollBar.setVisibility(INVISIBLE) ;
				}
				return true ;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	public void setVAdapter(List<PlayItem> items, int totalPagers, int currentPosition) {
		this.total = totalPagers ;
		if (vAdapter == null) {
			vAdapter = new PlayVooleAdapter(context, items, currentPosition);
			vAdapter.setTotalPagers(totalPagers);
			vAdapter.setScrollBar(scrollBar);
			setAdapter(vAdapter);
			int len = items.size() ;
			int itemSize = vAdapter.getItemSize() ;
			if (len <= itemSize) {
				setSelection(currentPosition) ;
			} else {
				if (currentPosition - currentPosition%itemSize + itemSize <= len) {
					setSelection(currentPosition%vAdapter.getItemSize()) ;
				} else {
					setSelection(currentPosition%itemSize + (itemSize - len % itemSize)) ;
				}
			}
		} else {
			if (items != null) {
				vAdapter.setNewItems(items);
			}
		}

		if (totalPagers == 1) {
			scrollBar.setVisibility(INVISIBLE) ;
		}
	}

	public void setVAdapter(List<PlayItem> items, int currentPosition) {
		setVAdapter(items, total, currentPosition);
	}

	public PlayVooleAdapter getVAdapter() {
		return vAdapter;
	}

	public void setTotalPagers(int totalPagers) {
		if (vAdapter != null) {
			vAdapter.setTotalPagers(totalPagers);
			vAdapter.setScrollBar(scrollBar);
		}
	}

	public int getCurrentPager() {
		if (vAdapter != null) {
			return vAdapter.getCurrentPager();
		}
		return -1;
	}

	public List<PlayItem> getTotalItem() {
		return vAdapter.getTotalItem();
	}

	public int getCurrrentPosition() {
		return vAdapter.getCurrrentPosition();
	}

	public interface OnVooleItemSelectedListener {
		void onItemSelected(AdapterView<?> parent, View view, int position,
				long id);

		void onNothingSelected(AdapterView<?> parent);
	}

	public void setScrollBar(PlaySeriesScrollBar vScrollBar) {
		if (vScrollBar != null) {
			this.scrollBar = vScrollBar;
		}
	}

	public int getItemSize() {
		return PlayVooleAdapter.itemSize;
	}

	public void setItemSize(int itemSize) {
		if (vAdapter == null || itemSize < 0) {
			return;
		}
		vAdapter.setItemSize(itemSize);
	}

	public interface OnFilmItemClickListener {
		void onFilmItemClick(PlayItem item, int positionIndex);
	}
	private OnFilmItemClickListener mListener ;
	public void setOnFilmItemClickListener(OnFilmItemClickListener listener) {
		this.mListener = listener ;
	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			//        	VoolePlayerListView.this.setVisibility(View.GONE);
			Log.e("TAG", "handleMessage --->" + msg.what) ;
			switch (msg.what) {
			case 0:
				Log.e("TAG", "handleMessage ---> what ==== 0") ;
				setVisibility(View.INVISIBLE) ;
				if(scrollBar != null && scrollBar.getVisibility() == VISIBLE) {
					scrollBar.setVisibility(View.INVISIBLE) ;
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
					Log.e("TAG", "thread ---> what ==== 0") ;
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
