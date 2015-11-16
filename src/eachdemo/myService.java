package eachdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class myService extends Service{

	private static final String TAG = "MySerice";
	private MyBinder mBinder = new MyBinder();
	@Override
	public IBinder onBind(Intent intent) {
		Log.e(TAG, "start IBinder"); 
        Toast.makeText(getApplicationContext(), "start IBinder",Toast.LENGTH_SHORT).show();  
		return mBinder;
	}
	@Override
	public void onCreate() {
		Log.e(TAG, "start onCreate");
		Toast.makeText(getApplicationContext(), "start onCreate",Toast.LENGTH_SHORT).show(); 
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.e(TAG, "start onStart");
		Toast.makeText(getApplicationContext(), "start onStart",Toast.LENGTH_SHORT).show(); 
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		Log.e(TAG, "start onDestroy");
		Toast.makeText(getApplicationContext(), "start onDestory",Toast.LENGTH_SHORT).show(); 
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.e(TAG, "start onUnbind");
		Toast.makeText(getApplicationContext(), "start unBind",Toast.LENGTH_SHORT).show(); 
		return super.onUnbind(intent);
	}

	// ∑µªÿ ±º‰
	public static String getSystemTime() {
		Time t = new Time();
		t.setToNow();
		return t.toString();
	}
	
	public class MyBinder extends Binder {
		myService getService() {
		return myService.this;
		}
	}


}
