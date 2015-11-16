package eachdemo;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.demo.R;
import com.example.util.SelectPicPopupWindow;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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

	public static final float DISPLAY_WIDTH = 200;
	public static final float DISPLAY_HEIGHT = 200;
	
	private ImageView mImageHeader;
	
	private static String IMAGE_FILE_NAME = "family_header.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.head_main);
        mImageHeader = (ImageView)findViewById(R.id.myPage_headview);
        mImageHeader.setScaleType(ImageView.ScaleType.FIT_XY);
        head = (Button)findViewById(R.id.get_head);
        init(mImageHeader, IMAGE_FILE_NAME);
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
    
    //file to bitmap
    public void init(ImageView mImageHeader, String location){
    	String abLocation = getApplicationContext().getFilesDir().getAbsolutePath() + "/" + location;
    	Log.d("test", abLocation);
    	Bitmap bitmap = null;  
    	File file = new File(abLocation);  
		if(file.exists())  
		{  
		    bitmap = BitmapFactory.decodeFile(abLocation);
		    mImageHeader.setImageBitmap(bitmap);
		}
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0:
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
							save(image);
						}
					} catch (Exception e) {
						Log.d("jaffer","4");
						e.printStackTrace();
					}
				}
			}
			break;
		case 1:
			if (data != null){
			Uri uri = data.getData();  
		    if(uri == null){  
		           //use bundle to get data  
		       Bundle bundle = data.getExtras();    
               if (bundle != null) {                 
            	   Bitmap  photo = (Bitmap) bundle.get("data"); //get bitmap  
            	   //spath :生成图片取个名字和路径包含类型                              
            	   String loc = save(photo);
            	   mImageHeader.setImageBitmap(decodeBitmap(loc));
               } else {           
                   Toast.makeText(getApplicationContext(), "err****", Toast.LENGTH_LONG).show();           
                return;        
                }    
		       }else{  
		    	   Bitmap image;
					try {
						Log.d("jaffer","1");
						//这个方法是根据Uri获取Bitmap图片的静态方法
						image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
						if (image != null) {
							Log.d("jaffer","3");
							String loc = save(image);
							mImageHeader.setImageBitmap(decodeBitmap(loc));
						}
					} catch (Exception e) {
						Log.d("jaffer","4");
						e.printStackTrace();
					}
		       } 
			}
			break;
		default:
			break;

		}
	}
    
    //bitmap to file
    public String save(Bitmap bitmap){
    	String abLocation = getApplicationContext().getFilesDir().getAbsolutePath() + "/" + IMAGE_FILE_NAME;
    	File file=new File(abLocation);
    	try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
    	} catch (IOException e) {
            e.printStackTrace();
    	}
    	return abLocation;
    }
    
    private Bitmap decodeBitmap(String path){
    	  BitmapFactory.Options op = new BitmapFactory.Options();
    	  //inJustDecodeBounds 
    	  //If set to true, the decoder will return null (no bitmap), but the out…
    	  op.inJustDecodeBounds = true;
    	  Bitmap bmp = BitmapFactory.decodeFile(path, op); //获取尺寸信息
    	  //获取比例大小
    	  int wRatio = (int)Math.ceil(op.outWidth/DISPLAY_WIDTH);
    	  int hRatio = (int)Math.ceil(op.outHeight/DISPLAY_HEIGHT);
    	  //如果超出指定大小，则缩小相应的比例
    	  if(wRatio > 1 && hRatio > 1){
    	    if(wRatio > hRatio){
    	      op.inSampleSize = wRatio;
    	    }else{
    	      op.inSampleSize = hRatio;
    	    }
    	  }
    	  op.inJustDecodeBounds = false;
    	  bmp = BitmapFactory.decodeFile(path, op);
    	  return bmp;
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
				Log.d("test","sdcard");
				if (isSdcardExisting()) {
					Log.d("test","sdcard");
					Intent cameraIntent = new Intent(
							"android.media.action.IMAGE_CAPTURE");
					cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
					cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
					startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
					Log.d("test","sdcard");
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
