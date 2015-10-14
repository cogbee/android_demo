package eachdemo;

import com.example.demo.MainActivity;
import com.example.demo.R;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class login extends Activity {
	EditText login_name;
	EditText login_pass;
	Button login_now;
	String login_name_str;
	String login_pass_str;
	final String Tag = "jaffer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login_name = (EditText)findViewById(R.id.login_name_edt);             
        login_pass = (EditText)findViewById(R.id.login_pwd_edt);       
        login_now = (Button)findViewById(R.id.login_login_btn);
        login_now.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				login_pass_str = login_pass.getText().toString();
				login_name_str = login_name.getText().toString();
		        Toast.makeText(getApplicationContext(), "user:"+ login_name_str + "pass:" + login_pass_str,Toast.LENGTH_SHORT).show();  
			}
        	
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
