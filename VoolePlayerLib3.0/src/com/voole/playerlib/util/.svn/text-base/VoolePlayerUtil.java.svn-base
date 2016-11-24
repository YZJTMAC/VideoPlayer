package com.voole.playerlib.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.text.TextUtils;
import android.text.format.DateFormat;

public class VoolePlayerUtil {
	/**
	 * 时间转换  秒值转换成时分秒
	 * @param second
	 * @return
	 */
	public static String secondToString(long second){
		int s = (int) (second % 60);
		int m = (int) (second / 60 % 60);
		int h = (int) (second / 60 / 60);
		return (h<10 ? "0" + h : h)  + ":" + (m<10 ? "0" + m : m ) + ":" + (s<10 ? "0" + s : s);  
	}
	/**
	 * 播放器静音设置
	 * @param context
	 */
	public static void setCurrentPlayerMute(Context context){
		
		AudioManager audioManager = (AudioManager)context.getApplicationContext().
		getSystemService(Context.AUDIO_SERVICE);
	 
		if(getCurrentPlayerVolume(context) <= 0){
			audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
		}else{
			audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
		}
	}
	/**
	 * 获得当前声音
	 * @param context
	 * @return
	 */
	public static int getCurrentPlayerVolume(Context context){
		AudioManager audioManager = (AudioManager)context.getApplicationContext().
		getSystemService(Context.AUDIO_SERVICE);
		return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	}
	/**
	 * 设置音量
	 * @param context
	 * @param v
	 */
	public static void setPlayerVolume(Context context, int v){
		AudioManager audioManager = (AudioManager)context.getApplicationContext().
		getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, v, 0);
	}
	/**
	 * 音量增加
	 * @param context
	 */
	public static void increasePlayerVolume(Context context){
		AudioManager audioManager = (AudioManager)context.getApplicationContext().
		getSystemService(Context.AUDIO_SERVICE);
		if(getCurrentPlayerVolume(context) <= 0){
			audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
		}
		audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FX_FOCUS_NAVIGATION_UP);
	}
	/**
	 * 音量降低
	 * @param context
	 */
	public static void decreasePlayerVolume(Context context){
		AudioManager audioManager = (AudioManager)context.getApplicationContext().
		getSystemService(Context.AUDIO_SERVICE);
		if(getCurrentPlayerVolume(context) <= 0){
			audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
		}
		audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FX_FOCUS_NAVIGATION_UP);
	}
	/**
	 * 获得最大音量
	 * @param context
	 * @return
	 */
	public static int getPlayerMaxVolume(Context context){
		AudioManager audioManager = (AudioManager)context.getApplicationContext().
		getSystemService(Context.AUDIO_SERVICE);
		return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	}
	
	
	
	
	/**
	 * 判断当前界面是否是桌面
	 */
	public static  boolean isHome(Context context) {
		ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
		return getHomes(context).contains(rti.get(0).topActivity.getPackageName());
	}

	/**
	 * 获得属于桌面的应用的应用包名称
	 * 
	 * @return 返回包含所有包名的字符串列表
	 */
	public static  List<String> getHomes(Context context) {
		List<String> names = new ArrayList<String>();
		PackageManager packageManager = context.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		for (ResolveInfo ri : resolveInfo) {
			names.add(ri.activityInfo.packageName);
		}
		return names;
	}
	
	
	
	public static Properties getDlProp(Context context){
		try {
			Properties prop = new Properties();
			prop.load(context.getAssets().open("voolert.conf"));
			return prop;
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 获取下载proxy的端口
	 * @param key  local_http_port 
	 * @return 
	 */
	public static String getProxyPort(Context context){
		String path = getDlPropFromKey(context,"local_agent_http_port");
		if (TextUtils.isEmpty(path)) {
			return "5656";
		}
		path = trimQuotationMark(path);
		return path;
	}
	
	/**
	 * 获取vooleauth.conf中的值
	 * @param key
	 * @return
	 */
	public synchronized static String getDlPropFromKey(Context context,String key){
		if (getDlProp(context)!=null) {
			Properties prop = getDlProp(context);
			return (String) prop.get(key);
		}
		return null;
	}
	
	/**
	 * 去掉双引号
	 * @param path
	 * @return
	 */
	private static String trimQuotationMark(String path) {
		if (TextUtils.isEmpty(path)) {
			return path;
		}
		if (path.contains("\"")) {
			path  = path.replace("\"", "");
			if (!TextUtils.isEmpty(path)) {
				path = path.trim();
			}
		}
		return path;
	}
	
	public static String getProxyServer(Context context){
		return "http://127.0.0.1:"+getProxyPort(context);
	}
	
	public static String getBufferSpeedUrl(Context context){
		return getProxyServer(context)+"/info";
	}
	
	public static String formartTime(String pattern) {
   	 return DateFormat.format(pattern, System.currentTimeMillis()).toString();
   }
}
