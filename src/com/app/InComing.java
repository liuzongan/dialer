package com.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ServiceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.internal.telephony.ITelephony;

public class InComing extends Activity implements OnBufferingUpdateListener, OnCompletionListener,
		MediaPlayer.OnPreparedListener, SurfaceHolder.Callback
{
	public final static String KEY_PHONE = "call_phone_num";
	AudioManager mAudioManager;
	private TextView phoneNum;
	private MediaPlayer mMediaPlayer;
	private SurfaceView mPreview;
	private SurfaceHolder holder;
	private String TAG = "OutGoing";
	private int mVideoWidth;
	private int mVideoHeight;
	private ImageView speaker;
	private ImageView close;
	private ImageView square;
	private boolean isSpeaker = true;
	private WindowManager d;
	private boolean isReadLog = true;
	ContentResolver resolver;
	TelephonyManager tm;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		resolver = this.getContentResolver();
		tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		Bundle bundle = this.getIntent().getExtras();
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,     
	              WindowManager.LayoutParams. FLAG_FULLSCREEN);  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.incoming);
		
	 

		mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		phoneNum = (TextView) findViewById(R.id.phoneNum);
		speaker = (ImageView) findViewById(R.id.speaker);
		close = (ImageView) findViewById(R.id.close);
		square = (ImageView) findViewById(R.id.square);
		square.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				InComing.this.finish();
			}
		});
		tm.listen(new CallListener(), PhoneStateListener.LISTEN_CALL_STATE);
		speaker.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (isSpeaker)
				{
					speaker.setBackgroundResource(R.drawable.speaker_on_normal);
					isSpeaker = false;
					mAudioManager.setSpeakerphoneOn(true);
				} else
				{
					isSpeaker = true;
					speaker.setBackgroundResource(R.drawable.speaker_off_normal);
					mAudioManager.setSpeakerphoneOn(false);
				}
			}
		});

		close.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				// System.out.println("eventdown:" + eventdown.toString());
				// KeyEvent eventup = new KeyEvent(KeyEvent.ACTION_UP,
				// KeyEvent.KEYCODE_ENDCALL);
				// KeyEvent eventdown = new KeyEvent(KeyEvent.ACTION_DOWN,
				// KeyEvent.KEYCODE_ENDCALL);
				//
				// InComing.this.getWindow().superDispatchKeyEvent(eventdown);
				// InComing.this.getWindow().superDispatchKeyEvent(eventup);
				// // InComing.this.getWindow().setVolumeControlStream(10);
				// try
				// {
				// Runtime.getRuntime().exec(new String[]{ "radiooptions",
				// "- END_CALL" });
				// } catch (IOException e)
				// {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				new Thread()
				{
					public void run()
					{
						// KeyEvent eventdown = new
						// KeyEvent(android.os.SystemClock.uptimeMillis(),
						// android.os.SystemClock
						// .uptimeMillis(), KeyEvent.ACTION_DOWN,
						// KeyEvent.KEYCODE_ENDCALL, 0, 0, 0, 107, 8);
						// Instrumentation inst = new Instrumentation();
						// inst.sendKeySync(eventdown);
						// inst.sendCharacterSync(KeyEvent.KEYCODE_ENDCALL);
						generateKeys();
						// generateKeysWInst();
						ITelephony phone = ITelephony.Stub.asInterface(ServiceManager
								.checkService(Context.TELEPHONY_SERVICE));
						phone.endCall();
					}
				}.start();
			}
		});

		if (bundle != null)
		{
			String phone = bundle.getString(KEY_PHONE);
			if (phone != null && !phone.equals(""))
			{
				phoneNum.setText(this.getString(R.string.phone_num, phone));
			}
		}

		mPreview = (SurfaceView) findViewById(R.id.surface);
		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public boolean dispatchKeyEvent(KeyEvent event)
	{
		return super.dispatchKeyEvent(event);
	}

	public class CallListener extends PhoneStateListener
	{

		public void onCallStateChanged(int state, String incomingNumber)
		{
			switch (state)
			{

				case TelephonyManager.CALL_STATE_IDLE:
					Log.e(TAG, "CALL_STATE_IDLE");
					//InComing.this.finish();
					break;
				/* 接起电话时 */
				case TelephonyManager.CALL_STATE_OFFHOOK:
					Log.e(TAG, "CALL_STATE_OFFHOOK");
					break;
				case TelephonyManager.CALL_STATE_RINGING:
					Log.e(TAG, "CALL_STATE_RINGING");
					break;

			}
			super.onCallStateChanged(state, incomingNumber);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (event.KEYCODE_CALL == keyCode)
		{

			return super.onKeyUp(keyCode, event);
		}

		if (event.KEYCODE_ENDCALL == keyCode)
		{
			finish();
			return super.onKeyUp(keyCode, event);
		}

		return super.onKeyDown(keyCode, event);
	}

	private void playVideo()
	{
		try
		{

			// Create a new media player and set the listeners
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDataSource("/sdcard/video-2010-01-19-12-33-32.3gp");

			//mMediaPlayer.setDataSource(this, Uri.parse("rtsp://192.168.1.102:8000/sample_100kbit.mp4"));
			mMediaPlayer.setDisplay(holder);
			 
			mMediaPlayer.prepareAsync();
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnPreparedListener(this);
			// mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			new ReadLog().start();
		} catch (Exception e)
		{
			Log.e(TAG, "error: " + e.getMessage(), e);
		}
	}

	public void onBufferingUpdate(MediaPlayer arg0, int percent)
	{
		Log.d(TAG, "onBufferingUpdate percent:" + percent);

	}

	public void onCompletion(MediaPlayer arg0)
	{
		// this.finish();
	}

	public void onPrepared(MediaPlayer mediaplayer)
	{
		mVideoWidth = mMediaPlayer.getVideoWidth();
		mVideoHeight = mMediaPlayer.getVideoHeight();
		if (mVideoWidth != 0 && mVideoHeight != 0)
		{
			holder.setFixedSize(mVideoWidth, mVideoHeight);
			mMediaPlayer.start();
		}

	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k)
	{
		Log.d(TAG, "surfaceChanged called");

	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder)
	{
		Log.d(TAG, "surfaceDestroyed called");
	}

	public void surfaceCreated(SurfaceHolder holder)
	{
		Log.d(TAG, "surfaceCreated called");
		playVideo();

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		// TODO Auto-generated method stub
		if (mMediaPlayer != null)
		{
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		isReadLog = false;

	}

	private void generateKeys()
	{
		// Obtain the WindowManager system service interface
		IBinder wmbinder = ServiceManager.getService("window");
		IWindowManager wm = IWindowManager.Stub.asInterface(wmbinder);
		keyUpDown(wm, KeyEvent.KEYCODE_ENDCALL);
		// keyUpDown( wm,KeyEvent.KEYCODE_T );
		// keyUpDown( wm,KeyEvent.KEYCODE_E );
		// keyUpDown( wm,KeyEvent.KEYCODE_S );
		// keyUpDown( wm,KeyEvent.KEYCODE_T );
	}

	private void keyUpDown(IWindowManager wm, int keycode)
	{
		wm.injectKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keycode), true);
		wm.injectKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, keycode), true);
	}

	private void generateKeysWInst()
	{
		Instrumentation inst = new Instrumentation();
		inst.sendKeyDownUpSync(KeyEvent.KEYCODE_ENDCALL);
	}

	class ReadLog extends Thread
	{
		public void run()
		{
			try
			{
				Runtime.getRuntime().exec(new String[]
				{ "logcat", "-c" });
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			while (isReadLog)
			{
				Process mLogcatProc = null;
				BufferedReader reader = null;
				try
				{

					mLogcatProc = Runtime.getRuntime().exec(new String[]
					{ "logcat", "-ds", "InCallScreen:D" });

					reader = new BufferedReader(new InputStreamReader(mLogcatProc.getInputStream()));
					Log.e(TAG, "Process");
					String line;
					// final StringBuilder log = new StringBuilder();
					// String separator = System.getProperty("line.separator");

					while ((line = reader.readLine()) != null)
					{
						Log.e(TAG, line);
						if (line.indexOf("what=104") != -1 && line.indexOf("InCallScreen") != -1)
						{
							InComing.this.finish();
						}
					}

					sleep(500);

				}

				catch (IOException e)
				{
					e.printStackTrace();
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				finally
				{
					if (reader != null)
					{
						try
						{
							reader.close();
						} catch (IOException e)
						{
							e.printStackTrace();
						}
					}

				}
			}

		}
	}

}
