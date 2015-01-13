package org.nhnnext.JaeBong.nextagram;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit.mime.TypedFile;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ArticleWriter extends ActionBarActivity implements OnClickListener{
	private EditText etWriter;
	private EditText etTitle;
	private EditText etContent;
	private ImageButton ibPhoto;
	private Button buUpload;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_writer);
		
		etWriter = (EditText) findViewById(R.id.article_writer_WriterName);
		etTitle = (EditText) findViewById(R.id.article_writer_airticleTitle);
		etContent = (EditText)findViewById(R.id.article_writer_articleContent);
		
		ibPhoto = (ImageButton)findViewById(R.id.article_writer_inputImgBtn);
		ibPhoto.setOnClickListener(this);
		
		buUpload = (Button)findViewById(R.id.article_writer_writeBtn);
		buUpload.setOnClickListener(this);
		
		
		
	}

	private static final int REQUEST_PHOTO_ALBUM = 1;
	
	private String filePath;
	private String fileName;
	private Uri fileUri;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		try{
		if(requestCode == REQUEST_PHOTO_ALBUM){
			fileUri = getRealPathUrl(data.getData());
			filePath = fileUri.toString();
			fileName = fileUri.getLastPathSegment();
			
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			ibPhoto.setImageBitmap(bitmap);
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private Uri getRealPathUrl(Uri uri){
		Uri filePathUri = uri;
		if(uri.getScheme().toString().compareTo("content") == 0){
			Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
			if(cursor.moveToFirst()){
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				filePathUri = Uri.parse(cursor.getString(column_index));
			}
		}
		return filePathUri;
	}
	
	
	private ProgressDialog progressDialog;
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.article_writer_inputImgBtn:
			Intent intent = new Intent(Intent.ACTION_PICK);
			
			intent.setType(Images.Media.CONTENT_TYPE);
			intent.setData(Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, REQUEST_PHOTO_ALBUM);
			
			break;
		case R.id.article_writer_writeBtn:
			final Handler handler = new Handler();
			new Thread(){
				public void run(){
					
					handler.post(new Runnable(){
						public void run(){
							progressDialog = ProgressDialog.show(ArticleWriter.this,"","업로드 중입니다.");
						}
					});
					
					
					String ID = Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
					String DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.KOREA).format(new Date());
					Article article = new Article(
					0,
					etTitle.getText().toString(),
					etWriter.getText().toString(),
					ID,
					etContent.getText().toString(),
					DATE,
					fileName);
					
				TypedFile imageTypedFile = new TypedFile("image/jpeg", new File(fileUri.getPath()));
				Proxy proxy = new Proxy();
				proxy.uploadData(article, imageTypedFile);
				
				
				
				handler.post(new Runnable(){
					public void run(){
						progressDialog.cancel();
						
						finish();
					}
				});
				Log.i("test", imageTypedFile.toString());
				}
				
			}.start();
			

			break;
		}
	}

	

}
