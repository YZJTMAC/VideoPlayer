package com.voole.playerlib.broadcast;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetStateReceiver extends BroadcastReceiver {
	private AlertDialog alertDialog = null;
	private boolean isConnected = true;
	private int noNetCount = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (context == null) {
			return;
		}
		if (!isNetWorkAvailable(context)) {
			Log.d("NetStateReceiver", "The network is not available.........");
			isConnected = false;
			showDialog(context);
		} else {
			Log.d("NetStateReceiver", "The network is available.........");
			isConnected = true;
			cancelDialog();
		}
	}

	public static boolean isNetWorkAvailable(Context context) {
		ConnectivityManager connec = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connec == null)
			return false;

		NetworkInfo[] allinfo = connec.getAllNetworkInfo();

		if (allinfo != null) {
			for (int i = 0; i < allinfo.length; i++) {
				if (allinfo[i].isAvailable() && allinfo[i].isConnected()) {
					return true;
				}
			}
		}

		return false;
	}

	
	
	
	private void showDialog(Context context) {
		if (!isConnected && noNetCount == 0) {
			if (alertDialog == null) {
				alertDialog = new AlertDialog.Builder(context)
						.setTitle("网络异常，请检查网络连接！")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										alertDialog = null;
									}
								}).create();
				alertDialog.show();
				noNetCount = 1;
			}
		}
	}

	private void cancelDialog() {
		noNetCount = 0;
		if (alertDialog != null && alertDialog.isShowing()) {
			alertDialog.dismiss();
			alertDialog = null;
		}
	}

}
