package eachdemo;

import java.io.File;

import com.example.demo.MainActivity;
import com.example.demo.R;
import com.example.util.SelectPicPopupWindow;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class head extends Activity {
	Button head;
	//�Զ���ĵ�������
	SelectPicPopupWindow menuWindow;
	final String Tag = "jaffer";
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESIZE_REQUEST_CODE = 2;
	
	private ImageView mImageHeader;
	
	private static final String IMAGE_FILE_NAME = "family_header.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.head_main);
        mImageHeader = (ImageView)findViewById(R.id.myPage_headview);
        head = (Button)findViewById(R.id.get_head);
        mImageHeader.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(head.this,
						SelectPicPopupWindow.class), 1);      
			}
        	
        });
    }
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (resultCode) {
		case 1:
			if (data != null) {
				//ȡ�÷��ص�Uri,������ѡ����Ƭ��ʱ�򷵻ص�����Uri��ʽ���������������еû�����Uri�ǿյģ�����Ҫ�ر�ע��
				Uri mImageCaptureUri = data.getData();
				//���ص�Uri��Ϊ��ʱ����ôͼƬ��Ϣ���ݶ�����Uri�л�á����Ϊ�գ���ô���Ǿͽ�������ķ�ʽ��ȡ
				if (mImageCaptureUri != null) {
					Bitmap image;
					try {
						Log.d("jaffer","1");
						//��������Ǹ���Uri��ȡBitmapͼƬ�ľ�̬����
						image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);
						if (image != null) {
							Log.d("jaffer","3");
							mImageHeader.setImageBitmap(image);
						}
					} catch (Exception e) {
						Log.d("jaffer","4");
						e.printStackTrace();
					}
				} else {
					Log.d("jaffer","2");
					Bundle extras = data.getExtras();
					if (extras != null) {
						//��������Щ���պ��ͼƬ��ֱ�Ӵ�ŵ�Bundle�е��������ǿ��Դ��������ȡBitmapͼƬ
						Bitmap image = extras.getParcelable("data");
						if (image != null) {
							mImageHeader.setImageBitmap(image);
						}
					}
				}

			}
			break;
		default:
			break;

		}
	}


 
}
