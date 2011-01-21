package com.android.internal.telephony;

import android.os.IBinder;

public interface ITelephony
{
	public static class Stub
	{
		public static ITelephony asInterface(IBinder binder)
		{
			return null;
		}
	}

	public boolean endCall();
  
}