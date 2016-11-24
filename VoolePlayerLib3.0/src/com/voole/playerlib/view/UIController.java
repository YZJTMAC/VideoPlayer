package com.voole.playerlib.view;

import com.voole.playerlib.interfaces.IControllerView;
import com.voole.playerlib.interfaces.ISelecterView;
import com.voole.playerlib.interfaces.IStatusView;
import com.voole.playerlib.interfaces.ITitleView;

public class UIController {
	private ITitleView mTitleView = null;
	private IStatusView mStatusView = null;
	private IControllerView mControllerView = null;
	private ISelecterView mSelecterView = null;
	
	public UIController(ITitleView titleView, IStatusView statusView, IControllerView controllerView, ISelecterView selecterView){
		this.mTitleView = titleView;
		this.mStatusView = statusView;
		this.mControllerView = controllerView;
		this.mSelecterView = selecterView;
	}
}
