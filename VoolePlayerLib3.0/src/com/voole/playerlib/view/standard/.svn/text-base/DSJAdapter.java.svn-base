package com.voole.playerlib.view.standard;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gntv.tv.common.utils.DisplayManager;
import com.voole.playerlib.R;
import com.voole.playerlib.view.PlayItem;

public class DSJAdapter extends BaseAdapter {
	private Context context = null;
	private List<PlayItem> items;
	private List<PlayItem> currentItems;
	private int lastPosition;
	private int itemSize = 8;
	private int currentPosition = 0;
	private int scrollPosition = 0;
	private boolean isFrist = true ;
	public DSJAdapter(Context context, List<PlayItem> items, int currentPosition) {
		this.context = context;
		this.items = items;
		this.currentPosition = currentPosition;
		currentItems = new ArrayList<PlayItem>() ;
		int len = items.size() ;
		if (currentPosition + itemSize > len) {
			if (itemSize < len) {
				lastPosition = itemSize - (len-currentPosition) ;
				isFrist = false ;
				currentPosition = len-itemSize ;
			} else {
				lastPosition = currentPosition ;
				currentPosition = 0 ;
			}
		}
		for (int i = currentPosition; i < itemSize + currentPosition && i < items.size(); i++) {
			currentItems.add(items.get(i)) ;
		}
		//		DisplayManager.GetInstance().init(context) ;
	}

	public void setNewItems(List<PlayItem> newItems) {
		items.addAll(newItems) ;
	}

	@Override
	public int getCount() {
		if(items != null) {
			return currentItems.size();
		} else {
			return 0;
		}
	}

	@Override
	public String getItem(int position) {
		return items.get(currentPosition).contentName;
	}

	@Override
	public long getItemId(int position) {
		return currentPosition;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null) {
			int height = DisplayManager.GetInstance().getScreenHeight() / itemSize;
			Log.d("TAG", "height---------------->"+height);
			holder = new ViewHolder(); 
			convertView = new DSJItem(context);
			holder.dsjName = ((DSJItem) convertView).getDsjName();
			AbsListView.LayoutParams noselectParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT
					, height) ;
			convertView.setLayoutParams(noselectParams) ;
			convertView.setTag(holder);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.dsjName.setText(currentItems.get(position).contentName);
		//
		if(lastPosition == position) {
			holder.dsjName.setTextSize(32);
			holder.dsjName.setTextColor(Color.BLACK);
			holder.dsjName.setBackgroundResource(R.drawable.cs_playlist_tv_bg_selected);
//			LayoutParams layoutParams = holder.dsjName.getLayoutParams() ;
//			layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT ;
			holder.dsjName.setGravity(Gravity.CENTER);
		} else {
			holder.dsjName.setTextSize(20);
			holder.dsjName.setTextColor(Color.WHITE);
			holder.dsjName.setBackgroundResource(R.drawable.cs_playlist_tv_bg);
//			LayoutParams layoutParams = holder.dsjName.getLayoutParams() ;
//			layoutParams.width = 190 ;
			holder.dsjName.setGravity(Gravity.CENTER);
		}
		return convertView;
	}

	class ViewHolder {
		private TextView dsjName;
	}

	public void addPosition() {
		currentPosition++;
		if (currentPosition <= items.size() - 1) {
			changeScroll() ;
		} else {
			currentPosition-- ;
		}
	}

	private void changeScroll() {
		if(currentPosition / itemSize != scrollPosition) {
			scrollPosition = currentPosition / itemSize ;
		}
	}

	public int getItemSize() {
		return itemSize;
	}

	public void removeTop() {
		if (currentPosition < items.size()) {
			addItems(currentPosition);
			notifyDataSetChanged();
		}
	}

	private void addItems(int currentPosition) {
		currentItems.clear();
		for (int i = currentPosition - itemSize + 1; i < currentPosition + 1; i++) {
			currentItems.add(items.get(i));
		}
	}

	public void addBottom() {
		if (currentPosition >= 0) {
			removeItems(currentPosition);
			notifyDataSetChanged();
		}
	}

	private void removeItems(int currentPosition) {
		currentItems.clear();
		for (int i = currentPosition; i < currentPosition + itemSize && i < items.size(); i++) {
			currentItems.add(items.get(i));
		}
	}

	public void minusPosition() {
		currentPosition--;
		if (currentPosition >= 0) {
			changeScroll() ;
		} else {
			currentPosition++ ;
		}
	}

	public void changeDataVisable(int position) {
		if (isFrist) {
			lastPosition = position;
		}
		isFrist = true ;
		if (currentPosition < items.size() ) {
			notifyDataSetChanged();
		}
	}

	public void setLastPosition(int i) {
		this.lastPosition = i ;
		notifyDataSetChanged();
	}

}
