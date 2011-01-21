package com.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PhoneStateReciver extends BroadcastReceiver
{
	private String tag = "PhoneStateReciver";

	public void onReceive(Context context, Intent intent)
	{

		context.startService(new Intent(context, PhoneStateService.class));
	}

}
