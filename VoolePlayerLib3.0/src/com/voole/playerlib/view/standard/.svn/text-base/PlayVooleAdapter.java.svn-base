package com.voole.playerlib.view.standard;

import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gntv.tv.common.utils.DisplayManager;
import com.vad.sdk.core.view.v30.AlwaysMarqueeTextView;
import com.voole.playerlib.view.PlayItem;

public class PlayVooleAdapter extends BaseAdapter {

	private List<PlayItem> items;
	private Context context;
	private int lastPosition;
	private List<PlayItem> currentItems = new ArrayList<PlayItem>();
	private int currentPosition;
	private boolean isChange ;
	public static int itemSize = 6 ;
	private int currentPager = 1 ;
	private int totalPagers = 1;
	private int scrollDistance ;
	private int scrollPosition ;
	private PlaySeriesScrollBar vScrollBar ;
	private boolean isFrist = true ;

	public PlayVooleAdapter(Context context, List<PlayItem> items, int currentPosition) {
		DisplayManager.GetInstance().init(context) ;
		this.context = context;
		if (items == null) {
			items = new ArrayList<PlayItem>();
		}
		this.items = items;
		int len = items.size();
		if (len == 0) {
			return ;
		}
		this.currentPosition = currentPosition ;
		if (len <= itemSize) {
			currentItems.addAll(items) ;
			lastPosition = currentPosition ;
			return ;
		}

		if (currentPosition - currentPosition % itemSize + itemSize <= len) {
			lastPosition = currentPosition % itemSize ;
			currentPosition -=  currentPosition % itemSize ;
		} else {
			lastPosition = currentPosition % itemSize + (itemSize - len % itemSize) ;
			currentPosition = currentPosition - (itemSize - len % itemSize) ;
			if (currentPosition + itemSize >= len ) {
				currentPosition = len - itemSize;
			}

		}
		isFrist = false ;
		for (int i = currentPosition; i < itemSize + currentPosition && i < len; i++) {
			currentItems.add(items.get(i));
		}
	}

	@Override
	public int getCount() {
		if (items.size() >= itemSize) {
			return itemSize;
		}
		return currentItems.size();
	}

	@Override
	public PlayItem getItem(int position) {
		return items.get(currentPosition);
	}

	@Override
	public long getItemId(int position) {
		return currentPosition;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		PlayItem item = currentItems.get(position);
		if (convertView == null) {
			switch (getItemViewType(position)) {
			case 0:
				holder = new ViewHolder();
				convertView = new AlwaysMarqueeTextView(context) ;
				holder.tvTop = (AlwaysMarqueeTextView) convertView ;
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
						,RelativeLayout.LayoutParams.MATCH_PARENT) ;
				params.addRule(RelativeLayout.CENTER_VERTICAL) ;
				holder.tvTop.setGravity(Gravity.CENTER_VERTICAL) ;
				holder.tvTop.setTextColor(Color.WHITE) ;
				holder.tvTop.setLayoutParams(params) ;
				holder.tvTop.setTextSize(30) ;
				holder.tvTop.setSingleLine(true) ;
				holder.tvTop.setMarquee(false) ;
				AbsListView.LayoutParams noselectParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT
						, DisplayManager.GetInstance().getScreenHeight()  / (itemSize + 1)) ;
				convertView.setLayoutParams(noselectParams) ;
				break;
			case 1:
				holder = new ViewHolder();
				convertView = new PlaySelectItem(context);
				holder.tvTop = ((PlaySelectItem)convertView).getTvTop();
				holder.tvBottom = ((PlaySelectItem)convertView).getTvBottom();
				AbsListView.LayoutParams selectParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT
						, DisplayManager.GetInstance().getScreenHeight() * 2  / (itemSize + 1)) ;
				convertView.setLayoutParams(selectParams) ;
				break;
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		switch (getItemViewType(position)) {
		case 0:
			holder.tvTop.setText(item.filmName);
			break ;
		case 1:
			holder.tvTop.setText(item.filmName);
			holder.tvBottom.setText(item.FilmDetail);
			ObjectAnimator.ofFloat(convertView, "alpha", 0f,1f).setDuration(300).start() ;
			break;
		}
		return convertView;

	}

	public void changeDataVisable(int position) {
		if (!isFrist) {
			lastPosition = position;
		}
		isFrist = false ;
		if (currentPosition < items.size() ) {
			notifyDataSetChanged();
		}
	}

	public void addPosition() {
		currentPosition++;
		if (currentPosition <= items.size() - 1) {
			isChange = true ;
			changeScroll() ;
		} else {
			currentPosition-- ;
			isChange = false ;
		}
	}

	public void changeScroll() {
		if (vScrollBar == null ) {
			return ;
		}
		if(currentPosition / itemSize != scrollPosition) {
			scrollPosition = currentPosition / itemSize ;
			vScrollBar.setVScroll(scrollDistance * scrollPosition) ;
		}
	}

	public void removeTop() {
		if (currentPosition < items.size()) {
			if (isChange) {
				addItems(currentPosition);
				notifyDataSetChanged();
				isChange = false ;
			} else {
				if (currentPager < totalPagers) {
					currentPager++ ;
					isChange=true ;
				}
			}
		}
	}   

	public void setNewItems(List<PlayItem> newItems) {
		items.addAll(newItems) ;
	}

	private void addItems(int currentPosition) {
		currentItems.clear();
		for (int i = currentPosition - itemSize + 1; i < currentPosition + 1; i++) {
			currentItems.add(items.get(i));
		}
	}

	public void minusPosition() {
		currentPosition--;
		if (currentPosition >= 0) {
			isChange = true ;
			changeScroll() ;
		} else {
			currentPosition++ ;
			isChange=false ;
		}
	}

	public void addBottom() {
		if (currentPosition >= 0) {
			removeItems(currentPosition);
			if (isChange) {
				notifyDataSetChanged();
				isChange = false ;
			}
		}
	}

	private void removeItems(int currentPosition) {
		currentItems.clear();
		for (int i = currentPosition; i < currentPosition + itemSize && i < items.size(); i++) {
			currentItems.add(items.get(i));
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (position == lastPosition) {
			return 1;
		}
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	private class ViewHolder {
		private AlwaysMarqueeTextView tvTop;
		private TextView tvBottom;
	}

	public void setTotalPagers(int totalPagers) {
		this.totalPagers = totalPagers ;
		if (vScrollBar != null) {
			if (totalPagers > 1) {
				instanceScroll() ;
				vScrollBar.setVisibility(View.VISIBLE) ;
			}
		}
	}

	public int getCurrentPager() {
		return currentPager ;
	}

	public List<PlayItem> getTotalItem() {
		return items ;
	}

	public int getCurrrentPosition() {
		return currentPosition ;
	}

	public void setScrollBar(PlaySeriesScrollBar vScrollBar) {
		if (vScrollBar != null) {
			this.vScrollBar = vScrollBar ;
			if (totalPagers == 1) {
				vScrollBar.setVisibility(View.INVISIBLE) ;
			} else {
				instanceScroll() ;
			}
		}
	}

	public void instanceScroll() {
		ViewTreeObserver vto2 = vScrollBar.getViewTreeObserver(); 
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener(){ 
			@Override  
			public void onGlobalLayout() { 
				vScrollBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				int height = vScrollBar.getHeight() ;
				vScrollBar.getSelectedView().getLayoutParams().height= height / totalPagers ;
				scrollDistance = height / totalPagers ;
				changeScroll() ;
			}
		}) ;
	}

	public int getItemSize() {
		return itemSize ;
	}

	public void setItemSize(int itemSize) {
		this.itemSize = itemSize ;
	}

}
