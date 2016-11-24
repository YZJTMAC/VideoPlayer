package com.voole.playerlib.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gntv.tv.common.utils.DisplayManager;
import com.gntv.tv.common.utils.LocalManager;
import com.gntv.tv.common.utils.LogTimeUtil;
import com.gntv.tv.common.utils.LogUtil;
import com.vad.sdk.core.base.AdEvent;
import com.vad.sdk.core.base.AdEvent.AdStatus;
import com.vad.sdk.core.base.AdEvent.AdType;
import com.voole.player.lib.core.VooleMediaPlayer;
import com.voole.player.lib.core.VooleMediaPlayer.PlayType;
import com.voole.player.lib.core.VooleMediaPlayerListener;
import com.voole.player.lib.core.interfaces.IPlayer.Status;
import com.voole.playerlib.R;
import com.voole.playerlib.util.DisplayUtil;
import com.voole.playerlib.util.LogVoolePlayer;
import com.voole.playerlib.view.standard.ControllerView;
import com.voole.playerlib.view.standard.DSJListView.OnDSJItemClickListener;
import com.voole.playerlib.view.standard.PlaySeriesListView.OnFilmItemClickListener;
import com.voole.statistics.report.ReportManager;

public class VooleVideoView extends RelativeLayout {

	private String TAG = VooleVideoView.class.getName();
	private Context mContext;
	private VooleMediaPlayer vooleMediaPlayer = null;
	private ControllerView controlllerView = null;
	private TextView beginPlaytv = null;
	private TextView hintJumptv = null;
	private ExecutorService exec;
	private CountDownTimerTask countDownTimerTask = null;
	private Timer countDownTimer = null;
	private Timer updateProgressTimer = null;
	private TimerTask updateProgressTimerTask = null;
	private Timer onKeyUpTimer = null;
	private TimerTask onKeyUpTimerTask = null;

	private IPlayItemListener mPlayItemListener = null;
	private IVideoViewListener mVideoViewListener = null;
	private IVideoViewExitListener mVideoViewExitListener = null;

	private static final int START_PLAY = 1; // 开始播放
	private static final int SEEK_START_LEFT = 2;
	private static final int SEEK_START_RIGHT = 3;
	private static final int SEEK_END = 4;
	private static final int BEGIN_PLAYER_TIMER = 5;
	private static final int BEGIN_COUNTDOWN_TIMERTASK = 6;
	private static final int UPDATE_PROGRESS = 7;
	private static final int PLAY_ITEM_SUCCESS = 8;
	private static final int PLAY_ITEM_FAIL = 9;
	private static final int GET_PLAY_ITEM_SUCCESS = 10;
	private static final int GET_PLAY_ITEM_FAIL = 11;
	private static final int HIND_SEEKBAR = 12;
	private static final int SET_SPEED = 13;
	private static final int HIDE_HINTJUMP_VIEW = 14;
	private static final int MEDIA_INFO_BUFFERING_START = 701;
	private static final int MEDIA_INFO_BUFFERING_END = 702;
	private static final int FLAG_CHANGE_LIST_PLAYITEM = 100;
	private static final int FLAG_CHANGE_LIST_SERIESITEM = 101;

	public static final int PLAY_FILM_TYPE_SINGLE = 0;
	public static final int PLAY_FILM_TYPE_MULTI = 1;

	//	private int currentPlayTime ;
	// 快进递增时间
	private static final int FAST_TO_TIME = 10;
	/** 获取速度的间隔 */
	private static final int pagerItemSize = 24 ;

	private boolean isSeek = false;
	private boolean isFirstBeginPlay = true;
	private boolean isBeginPlay = true;
	private boolean isPlayItem = false;
	private boolean isSeriesItem = false;
	private boolean canSeek = true ;
	private boolean isPrepared = false ; 
	private boolean isFirst = true ;
	private boolean loop = true;
	private boolean isShowJump = true;

	private static int seek_time = 0;
	private int currentPosition, duration;
	private int playType ;
	private int recLen = 11;
	private int mPlayIndex;
	private int totalSize;
	private int seriesIndex;

	private String jumpType = "3";
	private String isJumpPlay ="0";
	private String isLiveShow ="0";
	private PlayItem mCurrentPlayItem;
	private List<PlayItem> mPlayItemList;
	private List<PlayItem> totalPlayItemList;
	private List<PlayItem> seriesItemList = new ArrayList<PlayItem>();

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case START_PLAY:
				Log.e(TAG, "handleMessage ------> START_PLAY：" +mCurrentPlayItem.continuePlayTime) ;
				if( vooleMediaPlayer != null ){
					cancelUpdateProgressTimer() ;
					startUpdateProgressTimer();
					if(mCurrentPlayItem.continuePlayTime > 0 ) {
						vooleMediaPlayer.start(mCurrentPlayItem.continuePlayTime * 1000);
						LogTimeUtil.logTime("onstart----seek----");
					} else {
						LogTimeUtil.logTime("onstart---not seek-----");
						vooleMediaPlayer.start(0);
					}
					controlllerView.showPlayStatus(Status.Playing);
					if (mCurrentPlayItem.continuePlayTime > 0) {
						notifyOnStart(mCurrentPlayItem.continuePlayTime) ;
					} else {
						notifyOnStart(0) ;
					}
					ReportManager.getInstance().onAppLiveRosuem();
				}
				break;
			case SEEK_START_LEFT:
			case SEEK_START_RIGHT:
				if(controlllerView.getProgressVisibility() == VISIBLE) {
					isSeek = true;
					setSeekDiaplayTime(FAST_TO_TIME + seek_time, msg.what);
					seek_time += 10;
					if (seek_time >= 60 * 2) {
						seek_time = 60 * 2;
					}
				} else {
					controlllerView.showProgressBar();
				}
				cancelonKeyUpTimer();
				//				if(seekCount != 0) {
				//					
				//				} 
				break;
			case SEEK_END:
				isSeek = false;
				seek_time = 0;
				LogVoolePlayer.i("--------SEEK_END----------"+(int) controlllerView
						.getProgressPosition());
				LogTimeUtil.logTime("start seek--------");
				if(vooleMediaPlayer.getCurrentStatus() == Status.Pause){
					vooleMediaPlayer.start();
				}
				vooleMediaPlayer.seekTo((int) controlllerView
						.getProgressPosition());
				controlllerView.doPlay();
				handler.sendEmptyMessage(MEDIA_INFO_BUFFERING_START);
				break;
			case HIND_SEEKBAR:
				if(vooleMediaPlayer != null && vooleMediaPlayer.getCurrentStatus() != Status.Pause) {
					controlllerView.showPlayStatus(Status.Playing);
				}
				break;
			case MEDIA_INFO_BUFFERING_START:
				controlllerView.showPlayStatus(Status.Preparing);
				notifyOnBufferStart() ;
				break;
			case MEDIA_INFO_BUFFERING_END:
				controlllerView.showPlayStatus(Status.Prepared);
				notifyOnBufferEnd() ;
				break;
			case BEGIN_PLAYER_TIMER:
				cancelCountDownTimer() ;
				startCountDownTimer();
				break;
			case BEGIN_COUNTDOWN_TIMERTASK:
				recLen--;
				if (mContext != null) {
					beginPlaytv.setText(mContext.getString(R.string.begin_play)
							+ " " + recLen);
				}
				if (recLen < 1) {
					cancelCountDownTimer();
					beginPlaytv.setVisibility(View.GONE);
				}
				break;
			case UPDATE_PROGRESS:
				if (vooleMediaPlayer != null && !isSeek) {
					if (duration <= 0) {
						duration = vooleMediaPlayer.getDuration();
					}
					currentPosition = vooleMediaPlayer.getCurrentPosition();
					LogVoolePlayer
					.i("--------UPDATE_PROGRESS----------currentPosition: "
							+ currentPosition + " duration :" + duration);
					controlllerView.updatePlayStatus(currentPosition, duration,
							isSeek);

				}
				break;
			case PLAY_ITEM_SUCCESS:
				doPlay((PlayType)msg.obj) ;
				break;
			case PLAY_ITEM_FAIL:
				//				new AlertDialog.Builder(mContext)
				//				.setMessage((String)msg.obj)
				//				.setPositiveButton("确定",
				//						new DialogInterface.OnClickListener() {
				//					@Override
				//					public void onClick(DialogInterface dialog,
				//							int which) {
				//						if (mVideoViewExitListener != null) {
				//							mVideoViewExitListener.onExit() ;
				//						}
				//					}
				//				}).create().show();
				if (mVideoViewListener != null) {
					Bundle bundle = msg.getData();
					String errorCode = bundle.getString("errorCode");
					String errorMsg = bundle.getString("errorMsg");
					mVideoViewListener.onError(errorCode, "", errorMsg);
				}
				break;
			case GET_PLAY_ITEM_SUCCESS:
				if (playType == PLAY_FILM_TYPE_MULTI) {
					controlllerView.setData(seriesItemList, totalSize, seriesIndex ) ;
				}
				break ;
			case GET_PLAY_ITEM_FAIL:
				Toast.makeText(mContext, "获取列表信息失败", Toast.LENGTH_SHORT).show();
				break;
			case SET_SPEED:
				if(controlllerView != null) {
					controlllerView.setSpeedText();
				}
				break;
			case HIDE_HINTJUMP_VIEW:
				hintJumptv.setVisibility(View.INVISIBLE);
				break;
			default:
				break;
			}

		};
	};

	private synchronized void assemblyList(int flag){
		if(totalPlayItemList==null){
			totalPlayItemList = new ArrayList<PlayItem>();
			if (flag == FLAG_CHANGE_LIST_PLAYITEM) {
				isPlayItem = true ;
				totalPlayItemList.addAll(mPlayItemList) ;
			} else {
				isSeriesItem = true ;
				totalPlayItemList.addAll(seriesItemList) ;
			}
		} else {
			if (flag == FLAG_CHANGE_LIST_SERIESITEM) {
				isSeriesItem = true ;
				totalPlayItemList.clear() ;
				totalPlayItemList.addAll(seriesItemList) ;
			} else {
				isPlayItem = true ;
			}
			Collections.replaceAll(totalPlayItemList, totalPlayItemList.get(seriesIndex), mPlayItemList.get(0));
		}
		if (isPlayItem && isSeriesItem) {
			mPlayItemList.clear() ;
			mPlayItemList.addAll(totalPlayItemList) ;
		}
	}

	protected void setSeekDiaplayTime(int seekTime, int orientation) {
		double progress = controlllerView.getProgressPosition() / 1000;
		double max = controlllerView.getMax() / 1000;
		boolean direction = true;
		switch (orientation) {
		case SEEK_START_LEFT:
			direction = false;
			progress = (int) (progress - seekTime);
			break;
		case SEEK_START_RIGHT:
			direction = true;
			progress = (int) (progress + seekTime);
			break;
		default:
			break;
		}
		LogVoolePlayer.i("--------setSeekDiaplayTime----------progress: "
				+ progress + "   max : " + max);
		controlllerView.setSeekProgress((int)(progress * 1000), direction);
	}

	public VooleVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public VooleVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public VooleVideoView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		setFocusable(true);
		setFocusableInTouchMode(true);
		setClickable(true);
		DisplayManager.GetInstance().init(context);
		initMeidaPlayerView(context);
		initControlView(context);
		initBeginPlayView(context);
		initHintJumpView(context);
	}

	private void initHintJumpView(Context context) {
		hintJumptv = new TextView(mContext);
		hintJumptv.setBackgroundResource(R.drawable.cs_play_tv_begin_bg);
		hintJumptv.setPadding(14, 5, 14, 5);
		RelativeLayout.LayoutParams beginPlayParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		hintJumptv.setTextColor(Color.parseColor("#f1f1f1"));
		hintJumptv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				DisplayUtil.sp2px(24, mContext));
		beginPlayParams.bottomMargin = (int) DisplayUtil.dip2px(150, mContext);
		beginPlayParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		beginPlayParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		hintJumptv.setText("已为您跳过片头，请到设置中设置");
		addView(hintJumptv, beginPlayParams);
		hintJumptv.setVisibility(View.INVISIBLE) ;
	}

	private void initMeidaPlayerView(Context context) {
		vooleMediaPlayer = new VooleMediaPlayer(context);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		vooleMediaPlayer.setLayoutParams(params);
		addView(vooleMediaPlayer);
		initMediaPlayer();
		startGetDownspeed();
	}

	private void initMediaPlayer() {
		vooleMediaPlayer.setMediaPlayerListener(new VooleMediaPlayerListener() {

			@Override
			public void onSeekComplete() {
				handler.sendEmptyMessage(MEDIA_INFO_BUFFERING_END);
				Log.e("TAG", "onSeekComplete----------->" ) ;
				LogTimeUtil.logTime("onSeekComplete--------");
			}

			@Override
			public void onSeek(int pos) {
				Log.e("TAG", "ONSEEK----------->" +pos ) ;
				//				cancelUpdateProgressTimer() ;
				//				startUpdateProgressTimer();
				LogTimeUtil.logTime("onSeek--------");
				isShowJump = false;
				if (vooleMediaPlayer != null) {
					vooleMediaPlayer.seekTo(pos) ;
				}
				if (mCurrentPlayItem.continuePlayTime > 0) {
					beginPlaytv.setVisibility(View.VISIBLE) ;
					handler.sendEmptyMessageDelayed(BEGIN_PLAYER_TIMER, (long) (1*1000));
				}
			}

			@Override
			public void onPrepared(boolean isPreview, int previewTime,String isLiveShow,String isJumpPlay) {
				LogTimeUtil.logTime("onPrepared----");
				LogVoolePlayer.i(TAG + "---------------->onPrepared ");
				Log.e(TAG , "onPrepared----------------> ");
				handler.sendEmptyMessage(START_PLAY);
				//				onProgress = true;
				//				upprogressTask = new UpprogressTask();
				//				upprogressTask.execute("Begin", "End");
				controlllerView.showPlayStatus(Status.Prepared);
				duration = vooleMediaPlayer.getDuration();
				isPrepared = true ;
				VooleVideoView.this.isLiveShow = isLiveShow;
				if (!TextUtils.isEmpty(isJumpPlay)) {
					VooleVideoView.this.isJumpPlay = isJumpPlay;
				}
				LogUtil.d("VooleVideoView------>onPrepared-----------isLiveShow-----> " + isLiveShow);
				LogUtil.d("VooleVideoView------>onPrepared-----------isJumpPlay-----> " + isJumpPlay);

				notifyOnPrepared(isPreview,previewTime);

			}

			@Override
			public boolean onInfo(int what, int extra) {
				switch (what) {
				case MediaPlayer.MEDIA_INFO_BUFFERING_START:// 701
				case 722:
				case 32773:
					handler.sendEmptyMessage(MEDIA_INFO_BUFFERING_START);
					return true;
				case MediaPlayer.MEDIA_INFO_BUFFERING_END:// 702
				case 32774:
					handler.sendEmptyMessage(MEDIA_INFO_BUFFERING_END);
					return true;
				}
				return false;
			}

			@Override
			public void onExit() {
				Log.e(TAG, "ONEXIT") ;
				if (mVideoViewExitListener != null) {
					mVideoViewExitListener.onExit() ;
				}
			}

			@Override
			public boolean onError(int what, int extra, String errorCode,
					String errorCodeOther, String errorMsgOther) {
				Log.d(TAG, "--------onError ----------what " + what + "extra "
						+ extra +" errorCodeOther: "+errorCodeOther +" errorMsgOther: "+errorMsgOther);
				// PLAY_AUTH_ERROR_NOT_RUN = -1001 // 鉴权失败 播放失败 状态不对
				if(!"0".equals(errorCode)) {
					if (mVideoViewListener != null) {
						mVideoViewListener.onError(errorCode, errorCodeOther, errorMsgOther);
					}					
					//					
				}
				return false;

			}

			@Override
			public void onCompletion() {
				notifyOnPlayCompleted() ; 
				mPlayIndex += 1 ;
				mCurrentPlayItem.continuePlayTime = 0 ;
				releaseUITime () ;
				if (mPlayIndex < mPlayItemList.size()) {
					Toast.makeText(getContext(), "即将播放下一个节目", duration).show() ;
					vooleMediaPlayer.reset();
					doPlayItem(mPlayIndex, PlayType.Preview) ;
				} else {
					if (mVideoViewExitListener != null) {
						Log.e(TAG, "onCompletion-------》onexit") ;
						stopSpeedThreadPool();
						mPlayIndex -= 1 ;
						mVideoViewExitListener.onCompleted() ;
					}
				}
			}

			@Override
			public void onBufferingUpdate(int percent) {

			}

			@Override
			public void onAdEvent(AdEvent e) {
				if (e.getAdType() == AdType.LOADING) {
					if (e.getAdStatus() == AdStatus.AD_START) {
						handler.sendEmptyMessage(MEDIA_INFO_BUFFERING_END);
					} else {
						handler.sendEmptyMessage(MEDIA_INFO_BUFFERING_START);
					}
				}
			}

			@Override
			public void canSeek(boolean canSeek) {
				Log.e(TAG, "canSeek----->" + canSeek) ;
				VooleVideoView.this.canSeek = canSeek ;
			}

			@Override
			public void canExit(boolean canExit) {
				if (mVideoViewExitListener != null) {
					mVideoViewExitListener.canExit(canExit) ;
				}
			}

			@Override
			public void onMovieStart() {
				// TODO Auto-generated method stub
				if(mCurrentPlayItem.continuePlayTime <= 0 && isShowJump && "1".equals(isJumpPlay) && "3".equals(jumpType)) {
					LogTimeUtil.logTime("onSeekComplete--------");
					hintJumptv.setVisibility( View.VISIBLE);
					handler.sendEmptyMessageDelayed(HIDE_HINTJUMP_VIEW, 5000);
					isShowJump = false;
				}
			}
		});
	}

	//	public void play(String mid, String sid, String fid) {
	//		LogVoolePlayer.i(TAG + "----------->play mid: " + mid + " sid: " + sid
	//				+ " fid: " + fid);
	//		//		String pid = "101002";
	//		//		String playtype = "0";
	//		//		String ispid = "20120629";
	//		//		String mediumtype = "m3u8";
	//		//		String coderate = "1300";
	//		//		String epgid = "100895";
	//		//		String cpid = "100010";
	//		//		vooleMediaPlayer.reset();
	//		//		vooleMediaPlayer.prepare(mid, sid, fid, pid, playtype, "", "", ispid, coderate, mediumtype,
	//		//				epgid, cpid, "");
	//
	//		StringBuffer httpUrl = new StringBuffer();
	//		String adUrl = "http://172.16.10.179/bian.mp4";
	//		httpUrl.append("http://127.0.0.1:5656/play?url='").append(adUrl + "'")
	//		.append("&proto=7");
	//
	//		vooleMediaPlayer.prepareNormal(httpUrl.toString());
	//	}

	public void release() {
		if (vooleMediaPlayer != null) {
			vooleMediaPlayer.release() ;
			vooleMediaPlayer = null ;
			mContext = null;
		}
	}

	public void onStop() {
		cancelCountDownTimer() ;
		cancelonKeyUpTimer() ;
		cancelUpdateProgressTimer() ;
		if(mCurrentPlayItem != null && vooleMediaPlayer != null){
			mCurrentPlayItem.continuePlayTime = vooleMediaPlayer.getCurrentPosition() / 1000;
		}
		if (vooleMediaPlayer != null) {
			vooleMediaPlayer.release();
		}
	}

	public void onStart(PlayType chargeType){
		doPlayItem(mPlayIndex, chargeType) ;
	}

	public void refreshCurrent(PlayType chargeType){
		Log.e(TAG, "VooleVideoView------> restart");
		releaseUITime () ;
		vooleMediaPlayer.reset();
		doPlayItem(mPlayIndex, chargeType) ;	
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//		LogVoolePlayer.i(TAG + "onKeyDown-------------->keyCode " + keyCode
		//				+ " playStatus " + playStatus);
		switch (keyCode) {
		case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
		case KeyEvent.KEYCODE_ENTER:
		case KeyEvent.KEYCODE_DPAD_CENTER:
			if (vooleMediaPlayer != null && (vooleMediaPlayer.getCurrentStatus() == Status.Pause
			||	vooleMediaPlayer.getCurrentStatus() == Status.Playing)) {
				if (!"2".equals(isLiveShow)) {
					doPausePlay();
				}
			} else if (vooleMediaPlayer != null && vooleMediaPlayer.getCurrentStatus() == Status.Error) {
				mVideoViewExitListener.onExit() ;
			} else {
				return true ;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			if (canSeek && isPrepared) {
				if (!"2".equals(isLiveShow)) {
				handler.sendEmptyMessage(SEEK_START_LEFT);
				}
			}
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			if (canSeek && isPrepared) {
				if (!"2".equals(isLiveShow)) {
					handler.sendEmptyMessage(SEEK_START_RIGHT);
				}
			}
			break;
		case KeyEvent.KEYCODE_DPAD_UP:
		case KeyEvent.KEYCODE_DPAD_DOWN:

			if (canSeek && isPrepared) {
				if (playType == PLAY_FILM_TYPE_MULTI) {
					controlllerView.showFilmSelecterView() ;
				} else {
					if (mPlayItemList.size() > 1) {
						controlllerView.showDSJPlayList();
					}
				}
			}
			break;
		case KeyEvent.KEYCODE_BACK:
			cancelonKeyUpTimer() ;
			cancelUpdateProgressTimer() ;
			stopSpeedThreadPool();
		default:
			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//		LogVoolePlayer.i(TAG + "onKeyUp-------------->keyCode " + keyCode);
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_LEFT:
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			if (isPrepared && canSeek) {
				if (!"2".equals(isLiveShow)) {
					startonKeyUpTimer();
					handler.sendEmptyMessage(SEEK_END);
				} 
				//				else {
				//					Toast.makeText(mContext, "暂不支持此功能！", 0).show();
				//				}
			}
			break;

		default:
			break;
		}

		return false;
	};

	/**
	 * 播放/暂停
	 */
	private void doPausePlay() {
		Status status = vooleMediaPlayer.getCurrentStatus();
		LogUtil.d(TAG+"doPausePlay---------------status---->"+status);
		if (status != Status.IDLE) {
			if (status == Status.Pause) {
				vooleMediaPlayer.start();
				controlllerView.doPlay();
				controlllerView.showPlayStatus(Status.Playing);
				notifyOnResume() ;
			} else {
				if (isFirstBeginPlay && mCurrentPlayItem.continuePlayTime > 0 && isBeginPlay && recLen > 0 && isPrepared && canSeek) {
					doPlaySeekTo(0);
					isFirstBeginPlay = false;
				} else {
					if(status != Status.Pause) {
						vooleMediaPlayer.pause();
					}
					//区别播放广告和正片暂停显示
					if (canSeek) {
						controlllerView.doPause();
					} else {
						controlllerView.doADPause();
					}
					notifyOnPause() ;
				}
			}
		}
	}

	public void reset(){
		//		notifyOnStop() ;
		//		vooleMediaPlayer.release();
		doStop();
		vooleMediaPlayer.reset();
		isSeek = false;
		isFirstBeginPlay = true;
		isBeginPlay = true;
		isPlayItem = false;
		isSeriesItem = false;
		canSeek = true ;
		isPrepared = false ; 
		isFirst = true ;
		loop = true;
		seek_time = 0;
		currentPosition = 0;
		duration = 0;
		playType = 0;
		recLen = 11;
		mPlayIndex = 0;
		totalSize = 0;
		seriesIndex = 0;
		isLiveShow ="0";
		mCurrentPlayItem = null;
		mPlayItemList = null;
		totalPlayItemList = null;
		seriesItemList.clear();
		if (beginPlaytv.getVisibility() == View.VISIBLE) {
			beginPlaytv.setVisibility(View.GONE);
		}
		//		cancelCountDownTimer() ;
		//		cancelonKeyUpTimer() ;
		//		cancelUpdateProgressTimer() ;
	}

	/**
	 * 停止
	 */
	public void doStop() {
		if (vooleMediaPlayer != null && isPrepared) {
			vooleMediaPlayer.stop() ;
		}
		notifyOnStop() ;
		releaseUITime() ;
	}

	private void releaseUITime() {
		isPrepared = false ;
		canSeek = true ;
		cancelCountDownTimer() ;
		cancelonKeyUpTimer() ;
		LogUtil.d("VooleViedoView------------------->releaseUITime");
		cancelUpdateProgressTimer() ;
	}
	private void doPlaySeekTo(int msec) {
		controlllerView.doPlay();
		vooleMediaPlayer.seekTo(msec);
		beginPlaytv.setVisibility(View.GONE);
		if("1".equals(isJumpPlay) && "3".equals(jumpType)) {
			hintJumptv.setVisibility( View.VISIBLE);
			handler.sendEmptyMessageDelayed(HIDE_HINTJUMP_VIEW, 5000);
			isShowJump = false;
		}
	}

	public void setOnkeyDownMediaPlay(int keyCode) {
		if (controlllerView != null) {
			controlllerView.showPlayStatus(Status.Playing) ;
		}
		if (beginPlaytv.getVisibility() == View.VISIBLE) {
			cancelCountDownTimer();
			beginPlaytv.setVisibility(View.GONE) ;
		}
		vooleMediaPlayer.onKeyDown(keyCode) ;
	}


	/**
	 * @param context
	 * 从头播放View
	 */
	private void initBeginPlayView(Context context) {
		beginPlaytv = new TextView(mContext);
		beginPlaytv.setBackgroundResource(R.drawable.cs_play_tv_begin_bg);
		beginPlaytv.setPadding(14, 5, 14, 5);
		RelativeLayout.LayoutParams beginPlayParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		beginPlaytv.setTextColor(Color.parseColor("#f1f1f1"));
		beginPlaytv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				DisplayUtil.sp2px(24, mContext));
		beginPlayParams.bottomMargin = (int) DisplayUtil.dip2px(150, mContext);
		beginPlayParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		beginPlayParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		addView(beginPlaytv, beginPlayParams);
		beginPlaytv.setVisibility(View.INVISIBLE) ;
	}

	/**
	 * 从头播放动画
	 * 倒计时Timer
	 */
	@SuppressLint("NewApi")
	private void startCountDownTimer() {
		PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(
				"translationX", 300, 0, 5, 0, 5, 0);
		ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
				beginPlaytv, pvhX);
		objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
		objectAnimator.setDuration(1500);
		objectAnimator.start();

		countDownTimer = new Timer();
		countDownTimerTask = new CountDownTimerTask();
		countDownTimer.schedule(countDownTimerTask, 1000, 1000);
	}

	private void cancelCountDownTimer() {
		if (countDownTimer != null) {
			countDownTimer.cancel();
			countDownTimer = null;
		}
		if (countDownTimerTask != null) {
			countDownTimerTask.cancel();
			countDownTimerTask = null;
		}
	}

	private class CountDownTimerTask extends TimerTask {

		@Override
		public void run() {
			handler.sendEmptyMessage(BEGIN_COUNTDOWN_TIMERTASK);
		}
	}

	/**
	 * @param context
	 * 播放控制View
	 */
	private void initControlView(Context context) {
		controlllerView = new ControllerView(context);
		RelativeLayout.LayoutParams param_controller = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		controlllerView.setLayoutParams(param_controller);
		addView(controlllerView);
		controlllerView.isShowPlayLogo(false);
		controlllerView.showPlayStatus(Status.Preparing);
		controlllerView.setOnDSJItemClickListener(new OnDSJItemClickListener() {

			@Override
			public void onDsjItemClick(int positionIndex) {
				if (positionIndex != mPlayIndex) {
					doStop() ;
					mCurrentPlayItem.continuePlayTime = 0 ;
					vooleMediaPlayer.reset();
					if (beginPlaytv.getVisibility() == View.VISIBLE) {
						cancelCountDownTimer();
						beginPlaytv.setVisibility(View.GONE);
					}
					doPlayItem(positionIndex, PlayType.Preview) ;
				}
			}
		});
		controlllerView.setOnFilmItemClickListener( new OnFilmItemClickListener() {

			@Override
			public void onFilmItemClick(PlayItem item, int positionIndex) {
				if (positionIndex != mPlayIndex) {
					doStop() ;
					vooleMediaPlayer.reset();
					if (beginPlaytv.getVisibility() == View.VISIBLE) {
						cancelCountDownTimer();
						beginPlaytv.setVisibility(View.GONE);
					}
					doPlayItem(positionIndex, PlayType.Preview) ;
				}
			}
		}) ;
		controlllerView.hintProgressBar() ;
	}


	private class UpdateProgressTimeTask extends TimerTask{
		@Override
		public void run() {
			if (vooleMediaPlayer != null) {
				handler.sendEmptyMessage(UPDATE_PROGRESS);
			}
		}
	}

	private void startUpdateProgressTimer(){
		updateProgressTimer = new Timer();
		updateProgressTimerTask = new UpdateProgressTimeTask();
		updateProgressTimer.schedule(updateProgressTimerTask, 0, 1*1000);
	}

	private void cancelUpdateProgressTimer(){
		if (updateProgressTimer != null) {
			updateProgressTimer.cancel();
			updateProgressTimer = null;
		}
		if (updateProgressTimerTask != null) {
			updateProgressTimerTask.cancel();
			updateProgressTimerTask = null;
		}

	}

	public void playItems(List<PlayItem> items, int index){
		this.mPlayItemList = items ;
		this.mPlayIndex = index ;
		if(mPlayItemList.size() > 1) {
			controlllerView.setDsjData(mPlayItemList, mPlayIndex);
		}
		//TODO KEY
		jumpType = LocalManager.GetInstance().getLocal("jumpType","3");
		Log.e("TAG", "VooleVideoView----playItems------>jumpType:" + jumpType);
		doPlayItem(index, PlayType.Preview);
	}

	public void playItems(List<PlayItem> items, int index, int totalSize) {
		if (items != null && items.size() > 0) {
			this.mPlayItemList = items ;
			this.totalSize = totalSize ;
			this.seriesIndex = index ;
			this.mPlayIndex = index % items.size() ;
			//TODO KEY
			jumpType = LocalManager.GetInstance().getLocal("jumpType","3");
			Log.e("TAG", "VooleVideoView----playItems------>jumpType:" + jumpType);
			doPlayItem(mPlayIndex, PlayType.Preview) ;
			//			getPlayItems() ;
			if (items.size() == 1) {
				getPlayItems() ;
			} else if(items.size() > 1) {
				seriesItemList.clear();
				seriesItemList.addAll(items);
				mPlayIndex = seriesIndex % seriesItemList.size() ;
				handler.sendEmptyMessage(GET_PLAY_ITEM_SUCCESS);
			}
		}
	}

	private void getPlayItems() {
		new Thread(){
			public void run() {
				if (mPlayItemListener != null) {
					int number = 0 ;
					if (totalSize % pagerItemSize == 0) {
						number = totalSize / pagerItemSize ;
					} else {
						number = totalSize / pagerItemSize + 1 ;
					}
					for(int i = 1; i <= number; i++) {
						List<PlayItem> firstItemList = mPlayItemListener.getCurrentPlayItems(i, pagerItemSize) ;
						seriesItemList.addAll(firstItemList) ;
					}
					if (seriesItemList.size() > 0 ) {
						assemblyList(FLAG_CHANGE_LIST_SERIESITEM) ;
						mPlayIndex = seriesIndex % seriesItemList.size() ;
						handler.sendEmptyMessage(GET_PLAY_ITEM_SUCCESS);
						//						handler.sendEmptyMessageDelayed(GET_PLAY_ITEM_SUCCESS, 2000) ;
						return ;
					}
				}
				handler.sendEmptyMessage(GET_PLAY_ITEM_FAIL);
			};
		}.start() ;
	}

	private void doPlay(PlayType chargeType) {
		//		List<PlayItem> items1 = new ArrayList<PlayItem>();
		//		PlayItem[] item = new PlayItem[10];
		if (mCurrentPlayItem != null && vooleMediaPlayer != null) {
			LogTimeUtil.logTime("getplayItem----");
			Log.d("vv", "---------doPlay------->item ");
			controlllerView.setFilmName(mCurrentPlayItem.filmName);
			if (playType == PLAY_FILM_TYPE_SINGLE) {
				if (mPlayItemList.size() > 1) {
					controlllerView.setContentName(mCurrentPlayItem.contentName);
				}
			}
			controlllerView.showPlayStatus(Status.Preparing);
			//TODO jumpType
			if (mContext != null) {
				vooleMediaPlayer.prepare(mCurrentPlayItem.mid, mCurrentPlayItem.sid, mCurrentPlayItem.fid, mCurrentPlayItem.pid, 
						mCurrentPlayItem.playtype, mCurrentPlayItem.downUrl, mCurrentPlayItem.mtype, 
						mCurrentPlayItem.ispid, mCurrentPlayItem.coderate, mCurrentPlayItem.mediumtype,	mCurrentPlayItem.epgid,
						mCurrentPlayItem.cpid, mCurrentPlayItem.cdnType, chargeType,jumpType);
				Log.e("TAG", mCurrentPlayItem.mid +","+ mCurrentPlayItem.sid+","+ mCurrentPlayItem.fid+","+ mCurrentPlayItem.pid+","+ 
						mCurrentPlayItem.playtype+","+ mCurrentPlayItem.downUrl+","+ mCurrentPlayItem.mtype+","+ 
						mCurrentPlayItem.ispid+","+ mCurrentPlayItem.coderate+","+ mCurrentPlayItem.mediumtype+","+ mCurrentPlayItem.epgid+","+ 
						mCurrentPlayItem.cpid+","+ mCurrentPlayItem.cdnType+","+ chargeType);
			}
		}
	}


	public void setMulitPlayType(int playType){
		this.playType = playType ;
	}


	private void doPlayItem(final int index, final PlayType chargeType) {
		ReportManager.getInstance().onAppLivePause();
		new Thread() {
			@Override
			public void run() {
				super.run();
				if(mPlayItemListener != null) { 
					LogTimeUtil.logTime("doplayItem----");
					Log.d("vv", "mPlayItemListener != null =--------------->");
					PlayMessageInfo nextPlay = mPlayItemListener.getPlayItemData(mPlayItemList, index);
					Log.d("vv", "---------------->item "+nextPlay.status);
					if("1".equals(nextPlay.status)) {
						mPlayIndex = index ;
						mCurrentPlayItem = mPlayItemList.get(index);
						if(playType == PLAY_FILM_TYPE_MULTI && isFirst) {
							assemblyList(FLAG_CHANGE_LIST_PLAYITEM) ;
							isFirst = false ;
						}
						Message msg = Message.obtain();
						msg.what = PLAY_ITEM_SUCCESS;
						msg.obj = chargeType;
						handler.sendMessage(msg);
						//						handler.sendEmptyMessage(PLAY_ITEM_SUCCESS);
					} else {
						ReportManager.getInstance().onAppLiveRosuem();
						Message msg = handler.obtainMessage();
						msg.what = PLAY_ITEM_FAIL;
						Bundle bundle = new Bundle();
						bundle.putString("errorCode", nextPlay.status);
						bundle.putString("errorMsg", nextPlay.message);
						msg.setData(bundle);
						msg.sendToTarget();
						//						handler.sendEmptyMessage(PLAY_ITEM_FAIL);
					}
				}
			}
		}.start();

	}

	private synchronized void startGetDownspeed() {
		if (exec == null) {
			exec = Executors.newSingleThreadExecutor();
		}
		if (!loop) {
			return;
		}

		Runnable command = new Runnable() {
			public void run() {
				while (loop) {
					handler.sendEmptyMessage(SET_SPEED);
					SystemClock.sleep(1000);
				}
			};
		};
		if (!exec.isShutdown() || !exec.isTerminated()) {
			try {
				exec.execute(command);
			} catch (Exception e) {
				System.out.println("PlayControlView----------->e.printStackTrace(); " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void stopSpeedThreadPool() {
		if (exec != null) {
			exec.shutdown();
		}
	}

	public void setAppVersionCodeAndId(String versionCode,String appid) {
		vooleMediaPlayer.setApkVersionCode(versionCode) ;
		vooleMediaPlayer.setAppid(appid) ;
	}

	public void setPlayItemListener(IPlayItemListener l){
		this.mPlayItemListener = l;
	}

	public void setVideoViewListener(IVideoViewListener l){
		this.mVideoViewListener = l;
	}

	public void setVideoViewExitListener(IVideoViewExitListener l) {
		this.mVideoViewExitListener = l ;
	}

	public interface IVideoViewExitListener {
		void canExit(boolean canExit);

		void onExit();

		void onCompleted();
	}

	private void notifyOnPrepared(boolean isPreview, int previewTime){
		if(mVideoViewListener != null && vooleMediaPlayer != null){
			mVideoViewListener.onPrepared(mPlayItemList, mPlayIndex, 0, duration, getContext(), isPreview, previewTime);
		}
	}

	private void notifyOnStart(int currentTime){
		if(mVideoViewListener != null && vooleMediaPlayer != null){
			mVideoViewListener.onStartPlay(mPlayItemList, mPlayIndex, currentTime, duration, getContext(), false);
		}
	}
	private void notifyOnPause(){
		if(mVideoViewListener != null && vooleMediaPlayer != null){
			mVideoViewListener.onPausePlay(mPlayItemList, mPlayIndex, currentPosition, duration, getContext(), false);
		}
	}

	private void notifyOnResume(){
		if(mVideoViewListener != null && vooleMediaPlayer != null){
			mVideoViewListener.onResumePlay(mPlayItemList, mPlayIndex, currentPosition, duration, getContext(), false);
		}
	}
	private void notifyOnStop(){
		if (mCurrentPlayItem  != null) {
			if ("2".equals(isLiveShow)) {
				mVideoViewListener.onStopPlay(mPlayItemList, mPlayIndex, 0, duration, getContext(), false);
			} else {
				if(mVideoViewListener != null && vooleMediaPlayer != null && mCurrentPlayItem != null){
					if (currentPosition == 0 && mCurrentPlayItem.continuePlayTime > 0 && !canSeek) {
						Log.e(TAG, "notifyOnStop------>not canseek" + mCurrentPlayItem.continuePlayTime) ;
						mVideoViewListener.onStopPlay(mPlayItemList, mPlayIndex, mCurrentPlayItem.continuePlayTime * 1000, duration, getContext(), false);
					} else {
						Log.e(TAG, "notifyOnStop------> canseek" + currentPosition) ;
						mVideoViewListener.onStopPlay(mPlayItemList, mPlayIndex, currentPosition, duration, getContext(), false);
					}
				}
			}
		}
	}
	private void notifyOnPlayCompleted(){
		if(mVideoViewListener != null && vooleMediaPlayer != null){
			mVideoViewListener.onPlayCompleted(mPlayItemList, mPlayIndex, 0, duration, getContext(), false);
		}
	}
	private void notifyOnBufferStart(){
		if(mVideoViewListener != null && vooleMediaPlayer != null){
			mVideoViewListener.bufferStart(mPlayItemList, mPlayIndex, currentPosition, duration, getContext(), false);
		}
	}
	private void notifyOnBufferEnd(){
		if(mVideoViewListener != null && vooleMediaPlayer != null){
			mVideoViewListener.bufferEnd(mPlayItemList, mPlayIndex, currentPosition, duration, getContext(), false);
		}
	}

	private class onKeyUpTimerTask extends TimerTask {

		@Override
		public void run() {
			handler.sendEmptyMessage(HIND_SEEKBAR);
		}

	};

	private void startonKeyUpTimer() {
		onKeyUpTimer = new Timer();
		onKeyUpTimerTask = new onKeyUpTimerTask();
		onKeyUpTimer.scheduleAtFixedRate(onKeyUpTimerTask, 2000, 2000);
	}

	private void cancelonKeyUpTimer() {
		if (onKeyUpTimer != null) {
			onKeyUpTimer.cancel();
			onKeyUpTimer = null ;
		}
		if (onKeyUpTimerTask != null) {
			onKeyUpTimerTask.cancel();
			onKeyUpTimerTask = null ;
		}
	}
	/**
	 * 是否显示优朋logo
	 * @param isShow
	 */
	public void isShowPlayLogo(boolean isShow) {
		controlllerView.isShowPlayLogo(isShow);
	}

	/*private void registerNetReceiver(){
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		mNetStateReceiver = new NetStateReceiver();
		mContext.registerReceiver(mNetStateReceiver, filter);
	}

	private void unRegisterNetReceiver(){
		if(mNetStateReceiver != null){
			mContext.unregisterReceiver(mNetStateReceiver);
			mNetStateReceiver = null;
		}
	}*/
}
