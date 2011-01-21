package com.app;

import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Log;

/* 内部class继承PhoneStateListener */
public class ExPhoneCallListener extends PhoneStateListener
{
	private String tag="ExPhoneCallListener";
	/*
	 * 重写onCallStateChanged 当状态改变时改变myTextView1的文字及颜色
	 */
	public void onCallStateChanged(int state, String incomingNumber)
	{
		switch (state)
		{
			/* 无任何状态时 */
			case TelephonyManager.CALL_STATE_IDLE:
				Log.e(tag, "CALL_STATE_IDLE");
				// myTextView1.setTextColor(getResources().getColor(R.drawable.red));
				// myTextView1.setText("CALL_STATE_IDLE");
				break;
			/* 接起电话时 */
			case TelephonyManager.CALL_STATE_OFFHOOK:
				Log.e(tag, "CALL_STATE_OFFHOOK");
				// myTextView1.setTextColor(getResources().getColor(R.drawable.green));
				// myTextView1.setText("CALL_STATE_OFFHOOK");
				break;
			/* 电话进来时 */
			case TelephonyManager.CALL_STATE_RINGING:
				Log.e(tag, "CALL_STATE_RINGING");
				// getContactPeople(incomingNumber);
				break;
			default:
				Log.e(tag, "NO STATE: " + state);
				break;
		}
		super.onCallStateChanged(state, incomingNumber);
	}

	@Override
	public void onCallForwardingIndicatorChanged(boolean cfi)
	{
		Log.e(tag, "onCallForwardingIndicatorChanged : " + cfi);
	}

	@Override
	public void onCellLocationChanged(CellLocation location)
	{
		Log.e(tag, "onCellLocationChanged : " + location);
	}

	@Override
	public void onDataActivity(int direction)
	{
		/**
		 * DATA_ACTIVITY_NONE DATA_ACTIVITY_IN DATA_ACTIVITY_OUT
		 * DATA_ACTIVITY_INOUT
		 **/
		switch (direction)
		{
			case TelephonyManager.DATA_ACTIVITY_NONE:
				Log.e(tag, "DATA_ACTIVITY_NONE");
				break;
			case TelephonyManager.DATA_ACTIVITY_IN:
				Log.e(tag, "DATA_ACTIVITY_IN");
				break;
			case TelephonyManager.DATA_ACTIVITY_OUT:
				Log.e(tag, "DATA_ACTIVITY_OUT");
				break;
			case TelephonyManager.DATA_ACTIVITY_INOUT:
				Log.e(tag, "DATA_ACTIVITY_INOUT");
				break;
			default:
				Log.e(tag, "onDataActivity: " + direction);
				break;
		}

	}

	@Override
	public void onDataConnectionStateChanged(int state)
	{
		/**
		 * * DATA_DISCONNECTED DATA_CONNECTING DATA_CONNECTED DATA_SUSPENDED
		 */
		switch (state)
		{
			case TelephonyManager.DATA_DISCONNECTED:
				Log.e(tag, "DATA_DISCONNECTED");
				break;
			case TelephonyManager.DATA_CONNECTING:
				Log.e(tag, "DATA_CONNECTING");
				break;
			case TelephonyManager.DATA_CONNECTED:
				Log.e(tag, "DATA_CONNECTED");
				break;
			case TelephonyManager.DATA_SUSPENDED:
				Log.e(tag, "DATA_SUSPENDED");
				break;
			default:
				Log.e(tag, "onDataConnectionStateChanged: " + state);
				break;
		}
	}

	@Override
	public void onMessageWaitingIndicatorChanged(boolean mwi)
	{
		Log.e(tag, "onMessageWaitingIndicatorChanged : " + mwi);
	}

	@Override
	public void onServiceStateChanged(ServiceState serviceState)
	{
		/**
		 * * STATE_EMERGENCY_ONLY STATE_IN_SERVICE STATE_OUT_OF_SERVICE
		 * STATE_POWER_OFF
		 */

		switch (serviceState.getState())
		{
			case ServiceState.STATE_EMERGENCY_ONLY:
				Log.e(tag, "STATE_EMERGENCY_ONLY");
				break;
			case ServiceState.STATE_IN_SERVICE:
				Log.e(tag, "STATE_IN_SERVICE");
				break;
			case ServiceState.STATE_OUT_OF_SERVICE:
				Log.e(tag, "STATE_OUT_OF_SERVICE");
				break;
			case ServiceState.STATE_POWER_OFF:
				Log.e(tag, "STATE_POWER_OFF");
				break;
			default:
				Log.e(tag, "onServiceStateChanged: " + serviceState.getState());
				break;
		}
	}

	@Override
	public void onSignalStrengthChanged(int asu)
	{
		/**
		 * * STATE_EMERGENCY_ONLY STATE_IN_SERVICE STATE_OUT_OF_SERVICE
		 * STATE_POWER_OFF
		 */

		switch (asu)
		{
			case ServiceState.STATE_EMERGENCY_ONLY:
				Log.e(tag, "STATE_EMERGENCY_ONLY");
				break;
			case ServiceState.STATE_IN_SERVICE:
				Log.e(tag, "STATE_IN_SERVICE");
				break;
			case ServiceState.STATE_OUT_OF_SERVICE:
				Log.e(tag, "STATE_OUT_OF_SERVICE");
				break;
			case ServiceState.STATE_POWER_OFF:
				Log.e(tag, "STATE_POWER_OFF");
				break;
			default:
				Log.e(tag, "onSignalStrengthChanged: " + asu);
				break;
		}
	}
}