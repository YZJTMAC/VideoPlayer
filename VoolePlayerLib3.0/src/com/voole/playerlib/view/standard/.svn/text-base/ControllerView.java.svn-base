package com.voole.playerlib.view.standard;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.TrafficStats;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gntv.tv.common.utils.DisplayManager;
import com.voole.player.lib.core.interfaces.IPlayer.Status;
import com.voole.playerlib.R;
import com.voole.playerlib.util.DisplayUtil;
import com.voole.playerlib.util.LogVoolePlayer;
import com.voole.playerlib.util.VoolePlayerUtil;
import com.voole.playerlib.view.PlayItem;
import com.voole.playerlib.view.standard.DSJListView.OnDSJItemClickListener;
import com.voole.playerlib.view.standard.PlaySeriesListView.OnFilmItemClickListener;

public class ControllerView extends RelativeLayout {
	private String TAG = "ControllerView";
	private Context mContext = null;
	private static final int TOP_WEIGHT = 1;
	private static final int MID_WEIGHT = 3;
	private static final int BOTM_WEIGHT = 2;
	private static final int INFO_TEXT_SIZE = 18;
	private static final int BAR_MARGINLEFT = 40;
	private static final int BAR_MARGINBOTTOM = 50;
	public static final int FILENAME_ID = 0x0011;
	public static final int PROGRESSBAR_ID = 0x0012;
	public static final int PLAYTIME_ID = 0x0013;
	public static final int TOTALTIME_ID = 0x0014;
	public static final int HINT_ID = 0x0016;
	private StatusView bufferView = null;
	private ImageView pauseiv = null;
	private TextView hinttv = null;
	private LinearLayout controllerLayout = null;
	private PlayProgressbar progressbar = null;
	private RelativeLayout rlTop = null;
	private RelativeLayout rlTopLayout = null;
	private RelativeLayout rlBottomLayout = null;
	private DSJSelecterView dsjSelecterView = null;
	private FilmSelecterView filmSelecterView = null;
	private View Topview = null;
	
	private TextView fileNametv = null;
	private TextView indexTv = null ;
	private ImageView imgLogo = null ;
	private TextView playTimetv, totalTimetv;
	private long mRxBytes;

	@SuppressLint("NewApi")
	public ControllerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ControllerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ControllerView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.mContext = context;
		initTopMidBottom();
		initDSJSelecterView();
		initFilmSelecterView();
	}

	private void initTopMidBottom() {
		controllerLayout = new LinearLayout(mContext);
		controllerLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams controllerParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		addView(controllerLayout, controllerParams);
		initTopLayout();
		initMidLayout();
		initBottomLayout();
	}

	private void initTopLayout() {
		rlTop = new RelativeLayout(mContext);
		LinearLayout.LayoutParams rlTopParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0, TOP_WEIGHT);
		rlTop.setLayoutParams(rlTopParams);
		controllerLayout.addView(rlTop);
		
		rlTopLayout = new RelativeLayout(mContext);
		// rlTopLayout.setBackgroundColor(Color.YELLOW);
		LinearLayout.LayoutParams rlToplayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		rlTopLayout.setLayoutParams(rlToplayoutParams);
		rlTop.addView(rlTopLayout);

		fileNametv = new TextView(mContext);
		RelativeLayout.LayoutParams filenametvParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		fileNametv.setId(0x11223344) ;
		fileNametv.setText("");
		filenametvParams.addRule(RelativeLayout.CENTER_VERTICAL);
		fileNametv.setTextColor(Color.parseColor("#f1f1f1"));
		fileNametv.setEllipsize(TruncateAt.END) ;
		fileNametv.setSingleLine(true) ;
		fileNametv.setMaxEms(15) ;
		float scale = mContext.getResources().getDisplayMetrics().density;
		filenametvParams.leftMargin = (int) DisplayUtil.dip2px(50, scale);
		fileNametv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				DisplayUtil.sp2px(30, scale));
		fileNametv.setLayoutParams(filenametvParams);
		rlTopLayout.addView(fileNametv, filenametvParams);
		
		indexTv = new TextView(mContext);
		RelativeLayout.LayoutParams indexTvParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		indexTv.setText("");
		indexTvParams.addRule(RelativeLayout.CENTER_VERTICAL);
		indexTvParams.addRule(RelativeLayout.RIGHT_OF,0x11223344);
		indexTv.setTextColor(Color.parseColor("#f1f1f1"));
		indexTvParams.leftMargin = (int) DisplayUtil.dip2px(10, scale);
		indexTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				DisplayUtil.sp2px(30, scale));
		indexTv.setLayoutParams(indexTvParams);
		rlTopLayout.addView(indexTv, indexTvParams);
		
		imgLogo = new ImageView(mContext);
		RelativeLayout.LayoutParams imgLogoParams = new RelativeLayout.LayoutParams(
				80,80);
		imgLogoParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		imgLogoParams.rightMargin = 40;
//		imgLogoParams.topMargin = 20;
		imgLogoParams.addRule(RelativeLayout.CENTER_VERTICAL);
		imgLogo.setImageResource(R.drawable.play_logo);
		rlTop.addView(imgLogo, imgLogoParams);
		
		Topview = new View(mContext);//占位
		Topview.setVisibility(View.INVISIBLE);
//		view.setBackgroundColor(Color.YELLOW);
		controllerLayout.addView(Topview, rlTopParams);

	}

	private void initMidLayout() {
		RelativeLayout rlMidLayout = new RelativeLayout(mContext);
		// rlMidLayout.setBackgroundColor(Color.BLUE);
		LinearLayout.LayoutParams rlMidParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0, MID_WEIGHT);
		rlMidLayout.setLayoutParams(rlMidParams);
		controllerLayout.addView(rlMidLayout);

		pauseiv = new ImageView(mContext);
		RelativeLayout.LayoutParams pauseivParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		pauseivParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		pauseiv.setBackgroundResource(R.drawable.play_pause);
		pauseiv.setVisibility(View.GONE);
		rlMidLayout.addView(pauseiv, pauseivParams);

		bufferView = new StatusView(mContext);
		// bufferView.setBackgroundColor(Color.YELLOW);
		RelativeLayout.LayoutParams bufferParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		bufferParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		bufferView.setLayoutParams(bufferParams);
		bufferView.setVisibility(View.VISIBLE);
		rlMidLayout.addView(bufferView, bufferParams);
	}

	@SuppressLint("NewApi")
	private void initBottomLayout() {
		rlBottomLayout = new RelativeLayout(mContext);
		LinearLayout.LayoutParams rlBottomParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0, BOTM_WEIGHT);
		controllerLayout.addView(rlBottomLayout, rlBottomParams);
		int[] bottomColors = new int[2];
		bottomColors[0] = Color.parseColor("#ff000000");
		bottomColors[1] = Color.parseColor("#00000000");
		GradientDrawable rlBottomBg = new GradientDrawable(
				Orientation.BOTTOM_TOP, bottomColors);
		rlBottomLayout.setBackgroundDrawable(rlBottomBg);
		// rlBottom.setVisibility(INVISIBLE);

		playTimetv = new TextView(mContext);
		playTimetv.setText("00:00:00");
		playTimetv.setId(PLAYTIME_ID);
		RelativeLayout.LayoutParams playTimeParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		playTimetv.setLayoutParams(playTimeParams);
		playTimetv.setTextColor(Color.parseColor("#f1f1f1"));
		playTimetv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				DisplayUtil.sp2px(INFO_TEXT_SIZE, mContext));
		playTimeParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		playTimeParams.leftMargin = (int) DisplayUtil.dip2px(BAR_MARGINLEFT,
				mContext);
		playTimeParams.addRule(RelativeLayout.ALIGN_BOTTOM, PROGRESSBAR_ID);
		rlBottomLayout.addView(playTimetv, playTimeParams);

		progressbar = new PlayProgressbar(mContext);
		progressbar.setId(PROGRESSBAR_ID);
		RelativeLayout.LayoutParams progressbarParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		// progressbarParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		progressbarParams.addRule(RelativeLayout.ABOVE, HINT_ID);
		progressbarParams.leftMargin = (int) DisplayUtil.dip2px(
				BAR_MARGINLEFT + 50, mContext);
		progressbarParams.rightMargin = (int) DisplayUtil.dip2px(
				BAR_MARGINLEFT + 50, mContext);
		progressbar.setPadding(progressbar.getPaddingLeft(),
				progressbar.getPaddingTop(), progressbar.getPaddingRight(),
				progressbar.getPaddingBottom() + 6);
		rlBottomLayout.addView(progressbar, progressbarParams);

		totalTimetv = new TextView(mContext);
		totalTimetv.setText("00:00:00");
		totalTimetv.setId(TOTALTIME_ID);
		RelativeLayout.LayoutParams totalTimeParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		totalTimetv.setLayoutParams(totalTimeParams);
		totalTimetv.setTextColor(Color.parseColor("#f1f1f1"));
		totalTimetv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				DisplayUtil.sp2px(INFO_TEXT_SIZE, mContext));
		totalTimeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		totalTimeParams.rightMargin = (int) DisplayUtil.dip2px(BAR_MARGINLEFT,
				mContext);
		totalTimeParams.addRule(RelativeLayout.ALIGN_BOTTOM, PROGRESSBAR_ID);
		rlBottomLayout.addView(totalTimetv, totalTimeParams);

		hinttv = new TextView(mContext);
		hinttv.setId(HINT_ID);
		hinttv.setVisibility(View.INVISIBLE);
		hinttv.setTextColor(Color.parseColor("#f1f1f1"));
		hinttv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				DisplayUtil.sp2px(INFO_TEXT_SIZE, mContext));
		RelativeLayout.LayoutParams hinttvParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		hinttvParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		hinttvParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		hinttv.setText("设备编号");
		hinttvParams.bottomMargin = (int) DisplayUtil.dip2px(BAR_MARGINBOTTOM,
				mContext);
		rlBottomLayout.addView(hinttv, hinttvParams);
	}
	
//	private void showTopLayout (boolean isVisible) {
//		if(isVisible) {
//			rlTopLayout.setVisibility(View.VISIBLE);
//			Topview.setVisibility(View.INVISIBLE);
//		} else {
//			rlTopLayout.setVisibility(View.GONE);
//			Topview.setVisibility(View.GONE);
//		}
//	}

	public void updatePlayStatus(int currentPosition, long duration,
			boolean isSeek) {
		playTimetv.setText(VoolePlayerUtil
				.secondToString(currentPosition / 1000));
		totalTimetv.setText(VoolePlayerUtil.secondToString(duration / 1000));
		progressbar.setMax(duration);
		progressbar.setProgress(currentPosition);
	}

	public double getProgressPosition() {
		return progressbar.getProgress();
	}

	public long getMax() {
		return progressbar.getMax();
	}

	public void setSeekProgress(long seekTime, boolean direction) {
		if (seekTime <= 0) {
			progressbar.setProgress(0, direction);
		} else if (seekTime >= getMax()) {
			progressbar.setProgress((getMax()-(long)(1.5*1000)), direction);
		} else {
			progressbar.setProgress(seekTime, direction);
		}
//		progressbar.setProgress(seekTime, direction);
		bufferView.setVisibility(View.GONE);
		if (direction) {
			pauseiv.setBackgroundResource(R.drawable.play_forward);
			pauseiv.setVisibility(View.VISIBLE);
		} else {
			pauseiv.setBackgroundResource(R.drawable.play_reverse);
			pauseiv.setVisibility(View.VISIBLE);
		}
	}
	
	public void showProgressBar() {
		if (getVisibility() == View.INVISIBLE) {
			setVisibility(View.VISIBLE) ;
		}
		rlTopLayout.setVisibility(View.VISIBLE);
		rlBottomLayout.setVisibility(View.VISIBLE);
	}
	
	public void hintProgressBar() {
		rlBottomLayout.setVisibility(View.INVISIBLE);
	}
	
	public boolean isVisibleProgressBar() {
		if(rlBottomLayout.getVisibility() == View.VISIBLE) {
			return true;
		} else {
			return false;
		}
	}

	public void doControllerPlay() {
		bufferView.setVisibility(View.GONE);
	}

	public boolean showPlayStatus(Status status) {
		LogVoolePlayer.i("showPlayStatus-------------------->status " + status);
		switch (status) {
		case Playing:
//			bufferView.setVisibility(View.GONE);
			pauseiv.setVisibility(View.GONE);
			rlTopLayout.setVisibility(View.INVISIBLE);
			rlBottomLayout.setVisibility(View.INVISIBLE);
			break;
		case Pause:
			bufferView.setVisibility(View.GONE);
			pauseiv.setBackgroundResource(R.drawable.play_pause);
			pauseiv.setVisibility(View.VISIBLE);
			break;
		case Preparing:
			if (dsjSelecterView.getVisibility() == VISIBLE) {
				dsjSelecterView.setVisibility(INVISIBLE) ;
			}
			if (filmSelecterView.getVisibility() == VISIBLE) {
				filmSelecterView.setVisibility(INVISIBLE) ;
			}
			bufferView.setVisibility(View.VISIBLE);
			rlTopLayout.setVisibility(View.INVISIBLE);
			bufferView.setSpeedText(getSpeedText());
			break;
		case Prepared:
			bufferView.setVisibility(View.GONE);
			break;
		default:
			break;
		}

		return false;
	}

	/**
	 * 播放正片时暂停显示的ui
	 */
	public void doPause() {
		LogVoolePlayer.d(TAG
				+ " doPause------------------------------------------------->");
		setVisibility(View.VISIBLE) ;
		bufferView.setVisibility(View.GONE);
		pauseiv.setBackgroundResource(R.drawable.play_pause);
		rlTopLayout.setVisibility(View.VISIBLE);
		rlBottomLayout.setVisibility(View.VISIBLE);
		pauseiv.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 播放广告时暂停显示的ui
	 */
	public void doADPause() {
		setVisibility(View.VISIBLE) ;
		bufferView.setVisibility(View.GONE);
		pauseiv.setBackgroundResource(R.drawable.play_pause);
		rlTopLayout.setVisibility(View.VISIBLE);
		rlBottomLayout.setVisibility(View.INVISIBLE);
		pauseiv.setVisibility(View.VISIBLE);
	}

	public void doPlay() {
		LogVoolePlayer.d(TAG
				+ " doPlay------------------------------------------------->");
//		if (getVisibility() == VISIBLE) {
//			setVisibility(INVISIBLE) ;
//		}
//		bufferView.setVisibility(View.GONE);
		pauseiv.setVisibility(View.GONE);
//		rlTopLayout.setVisibility(View.INVISIBLE);
//		rlBottomLayout.setVisibility(View.INVISIBLE);
	}

	/**
	 * 获取网口下载速度
	 * @return
	 */
	public String getSpeedText() {
		int speedReal = 0;
		long total = TrafficStats.getTotalRxBytes();
		if (mRxBytes != 0) {
			if (total > mRxBytes) {
				speedReal = (int) ((total - mRxBytes) / (float) 1024);
			} else {
				speedReal = 0;
			}
		}
		mRxBytes = total;
		return speedReal + " KB/s";
	}
	 
	private void initDSJSelecterView() {
		dsjSelecterView = new DSJSelecterView(mContext);
//		dsjSelecterView.setBackgroundColor(Color.YELLOW);
		dsjSelecterView.setVisibility(View.INVISIBLE);
		RelativeLayout.LayoutParams dsjSelecterParams = new RelativeLayout.LayoutParams(DisplayManager.GetInstance().getScreenWidth()/4, LayoutParams.MATCH_PARENT);
		dsjSelecterParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		addView(dsjSelecterView, dsjSelecterParams);
	}
	
	/**
	 * 电视剧选集列表
	 */
	public void setDsjData(List<PlayItem> contentNames, int currentIndx) {
		dsjSelecterView.getVListView().setAdapterData(contentNames, currentIndx) ;
	}
	
	public void setFilmName(String filmName) {
		fileNametv.setText(filmName); //设置影片名称
	}
	
	public void setContentName(String content) {
		indexTv.setText(content); //设置电视剧集数
	}
	
	public void setOnDSJItemClickListener(OnDSJItemClickListener l) {
		dsjSelecterView.getVListView().setOnDSJItemClickListener(l) ;
	}
	
	public void setOnFilmItemClickListener(OnFilmItemClickListener l) {
		if (l != null) {
			filmSelecterView.getVListView().setOnFilmItemClickListener(l) ;
		}
	}
	
	public void showDSJPlayList(){
		setVisibility(VISIBLE) ;
		dsjSelecterView.showDSJSelecterView() ;
	}

	public void showFilmSelecterView(){
		setVisibility(VISIBLE) ;
		filmSelecterView.showFilmSelecterView() ;
	}
	
	public void setData(List<PlayItem> items, int totalSize, int currentPosition) {
		filmSelecterView.setData(items, totalSize, currentPosition) ;
	}


	private void initFilmSelecterView() {
		DisplayManager.GetInstance().init(mContext) ;
		filmSelecterView = new FilmSelecterView(mContext);
//		filmSelecterView.setBackgroundColor(Color.parseColor("#55888888"));
		filmSelecterView.setVisibility(View.INVISIBLE);
		RelativeLayout.LayoutParams dsjListParams = new RelativeLayout.LayoutParams( 
				DisplayManager.GetInstance().getScreenWidth() / 2, LayoutParams.MATCH_PARENT);
		dsjListParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//		filmSelecterView.setPadding(80, 0, 0, 0) ;
		filmSelecterView.setLayoutParams(dsjListParams) ;
		addView(filmSelecterView);
	}
	
	public int getProgressVisibility() {
		return rlBottomLayout.getVisibility() ;
	}
	
	/**
	 * 是否显示优朋logo
	 * @param isShow
	 */
	public void isShowPlayLogo(boolean isShow) {
		if(isShow) {
			imgLogo.setVisibility(VISIBLE);
		} else {
			imgLogo.setVisibility(GONE);
		}
	}

	public void setSpeedText() {
		if(bufferView.getVisibility() == View.VISIBLE) {
			bufferView.setSpeedText(getSpeedText());
		}
	}
	
}
