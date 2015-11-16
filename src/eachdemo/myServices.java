package eachdemo;

import com.example.demo.MainActivity;
import com.example.demo.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class myServices extends Activity{
	Button startS;
	Button stopS;
	Button bindS;
	Button unbindS;
	private myService testService;
	
	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
		testService = ((myService.MyBinder) service).getService();
		//textView.setText("i am from service:" + myService.getSystemTime());
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

	};
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service);
        startS = (Button)findViewById(R.id.startS);
        startS.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(myServices.this, myService.class);
				startService(i);
			}
        	
        });
        
        stopS = (Button)findViewById(R.id.stopS);
        stopS.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(myServices.this, myService.class);
				stopService(i);
			}
        });
        
        bindS = (Button)findViewById(R.id.bindS);
        bindS.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(myServices.this, myService.class);
				bindService(i, mServiceConnection, BIND_AUTO_CREATE);
			}
        	
        });
        
        unbindS = (Button)findViewById(R.id.unbindS);
        unbindS.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				unbindService(mServiceConnection);
			}
        });
        
	}

}
