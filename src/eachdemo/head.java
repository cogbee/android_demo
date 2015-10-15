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
	//自定义的弹出框类
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
				//实例化SelectPicPopupWindow
				menuWindow = new SelectPicPopupWindow(head.this, itemsOnClick);
				//显示窗口
				menuWindow.showAtLocation(head.this.findViewById(R.id.header), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
				//设置layout在PopupWindow中显示的位置               
			}
        	
        });
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Log.d("jaffer","-1:"+resultCode+""+"::::"+requestCode+"");
		switch (requestCode) {
		case 0:
		case 1:
			if (data != null) {
				//取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
				Uri mImageCaptureUri = data.getData();
				Log.d("jaffer","0");
				//返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
				if (mImageCaptureUri != null) {
					Bitmap image;
					try {
						Log.d("jaffer","1");
						//这个方法是根据Uri获取Bitmap图片的静态方法
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
						//这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
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
	
    
  //为弹出窗口实现监听类
    private OnClickListener itemsOnClick = new OnClickListener(){
		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {			
			case R.id.btn_pick_photo:
				Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
				galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
				galleryIntent.setType("image/*");
				startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
				break;
			case R.id.btn_take_photo:
				if (isSdcardExisting()) {
					Intent cameraIntent = new Intent(
							"android.media.action.IMAGE_CAPTURE");
					cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
					cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
					startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
				} else {
					Toast.makeText(v.getContext(), "请插入sd卡", Toast.LENGTH_LONG)
							.show();
				}
				break;
			default:
				break;
			}	
				
		}
    	
    };
    
    private boolean isSdcardExisting() {
		final String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public void resizeImage(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESIZE_REQUEST_CODE);
	}

	private void showResizeImage(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			mImageHeader.setImageDrawable(drawable);
		}
	}

	private Uri getImageUri() {
		return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
				IMAGE_FILE_NAME));
	}


 
}
