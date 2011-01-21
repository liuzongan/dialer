package com.app;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallReceiver extends BroadcastReceiver
{
	private String tag = "CallReceiver";
	private static String phoneNum = null;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL) || intent.getAction().equals(Intent.ACTION_CALL))
		{
			phoneNum = this.getResultData();
			Log.v("PhoneStateReciver", "new outgoing number" + this.getResultData());
		} else
		{
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
			if (tm.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK)
			{
				Log.v("PhoneStateReciver", "new incoming number" + intent.getStringExtra("incoming_number"));
				intent.setClass(context, InComing.class);
				Bundle bundle = new Bundle();
				bundle.putString(InComing.KEY_PHONE, phoneNum);
				intent.putExtras(bundle);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);

			}
		 
		}

	}
}
