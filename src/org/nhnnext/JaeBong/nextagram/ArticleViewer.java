package org.nhnnext.JaeBong.nextagram;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleViewer extends ActionBarActivity implements OnClickListener{
	private String articleNumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_viewer);
		
		TextView articleTitle = (TextView)findViewById(R.id.article_viewer_articleTitle);
		TextView articleWriter = (TextView)findViewById(R.id.article_viewer_articleWriter);
		TextView articleContent = (TextView)findViewById(R.id.article_viewer_articleContent);
		TextView uploadDate = (TextView)findViewById(R.id.article_viewer_uploadDate);
	
		ImageView articleImg = (ImageView)findViewById(R.id.article_viewer_articleImg);
		
		articleNumber = getIntent().getExtras().getString("ArticleNumber");
		
		Button viewComment = (Button)findViewById(R.id.article_viewer_viewComment);
		viewComment.setOnClickListener(this);
		
		Dao dao = new Dao(getApplicationContext());
	
		Article article = dao.getArticleByArticleNumber(Integer.parseInt(articleNumber));
	
		articleTitle.setText(article.getTitle());
		articleWriter.setText(article.getWriter());
		articleContent.setText(article.getContent());
		uploadDate.setText(article.getWriteDate());
	
		String img_path = getApplicationContext().getFilesDir().getPath() + "/" + article.getImgName();
		File img_load_path = new File(img_path);
		
		if(img_load_path.exists()){
			Bitmap bitmap = BitmapFactory.decodeFile(img_path);
			articleImg.setImageBitmap(bitmap);
		}
		
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.article_viewer_viewComment:
			Intent commentView = new Intent(this,CommentList.class);
			commentView.putExtra("ArticleNumber", articleNumber);
			startActivity(commentView);
			break;
		}
		
	}
}
