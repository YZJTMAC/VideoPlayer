package com.voole.playerlib.view;

import java.util.List;

public interface IPlayItemListener {
	public List<PlayItem> getCurrentPlayItems(int pageNo, int pageSize);

	public PlayMessageInfo getPlayItemData(List<PlayItem> playItemList, int index);
}
