package com.app;

import android.app.Service;
import android.content.ContentQueryMap;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class PhoneStateService extends Service
{

	private final class ServiceHandler extends Handler
	{
		public ServiceHandler(Looper looper)
		{
			super(looper);
		}

		@Override
		public void handleMessage(Message msg)
		{

			// long endTime = System.currentTimeMillis() + 5 * 1000;
			while (true)
			{
				synchronized (this)
				{
					try
					{
						Log.e(TAG, "" + tm.getCallState());
						wait(100);
					} catch (Exception e)
					{
					}
				}
			}

			// Log.i("ServiceStartArguments", "Done with #");
			// stopSelf(msg.arg1);
		}

	};

	

	private volatile Looper mServiceLooper;
	AudioManager mAudioManager;

	private volatile ServiceHandler mServiceHandler;
	private static final String TAG = "MyService";
	ExPhoneCallListener myPhoneCallListener = new ExPhoneCallListener();
	/* 取得电话服务 */
	TelephonyManager tm = null;

	@Override
	public IBinder onBind(Intent intent)
	{

		return null;
	}

	@Override
	public void onCreate()
	{
		/* 添加自己实现的PhoneStateListener */
		tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

		// setResultData("18004664411");
		/* 注册电话通信Listener */
		// tm.listen(myPhoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);
		// tm.listen(myPhoneCallListener,
		// PhoneStateListener.LISTEN_CELL_LOCATION);
		// tm.listen(myPhoneCallListener,
		// PhoneStateListener.LISTEN_DATA_ACTIVITY);
		// tm.listen(myPhoneCallListener,
		// PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
		// tm.listen(myPhoneCallListener,
		// PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR);
		// tm.listen(myPhoneCallListener,
		// PhoneStateListener.LISTEN_SERVICE_STATE);
		// tm.listen(myPhoneCallListener,
		// PhoneStateListener.LISTEN_SIGNAL_STRENGTH);
		// tm.listen(myPhoneCallListener,
		// PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR);
		tm.listen(myPhoneCallListener, PhoneStateListener.LISTEN_CALL_STATE | PhoneStateListener.LISTEN_CELL_LOCATION
				| PhoneStateListener.LISTEN_DATA_ACTIVITY | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE
				| PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR | PhoneStateListener.LISTEN_SERVICE_STATE
				| PhoneStateListener.LISTEN_SIGNAL_STRENGTH | PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR);
		// Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		// HandlerThread thread = new HandlerThread("ServiceStartArguments");
		// thread.start();
		//
		// mServiceLooper = thread.getLooper();
		// mServiceHandler = new ServiceHandler(mServiceLooper);
		// new Thread()
		// {
		// public void run()
		// {
		// while (true)
		// {
		//
		// try
		// {
		// if (mAudioManager.getMode() == mAudioManager.MODE_IN_CALL)
		// {
		// Log.e(TAG, "MODE_IN_CALL");
		// } else if (mAudioManager.getMode() == mAudioManager.MODE_NORMAL)
		// {
		// Log.e(TAG, "MODE_NORMAL");
		// }else if (mAudioManager.getMode() == mAudioManager.MODE_RINGTONE)
		// {
		// Log.e(TAG, "MODE_RINGTONE");
		// }else
		// {
		// Log.e(TAG, "other");
		// }
		//						
		// this.sleep(1000);
		// } catch (Exception e)
		// {
		// }
		//
		// }
		// }
		// }.start();

	}

	@Override
	public void onDestroy()
	{
		mServiceLooper.quit();
		// Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
		Log.e(TAG, "onDestroy");

	}

	@Override
	public void onStart(Intent intent, int startid)
	{
		Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
		Log.e(TAG, "onStart");

	}
}
