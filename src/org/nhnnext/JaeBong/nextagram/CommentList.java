package org.nhnnext.JaeBong.nextagram;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class CommentList extends Activity implements OnClickListener{

	private ListView commentListView;
	private EditText commentWriter;
	private Button sendComment;
	private EditText commentContent;
	private ArrayList<Comment> commentList;
	private ArrayList<Comment> commentListForEachArticle;
	private CommentDao dao;
	
	String articleNumber; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_comment_list);
	    
	    articleNumber = getIntent().getExtras().getString("ArticleNumber");
	    dao = new CommentDao(getApplicationContext());
	    commentWriter = (EditText)findViewById(R.id.comment_writer_input);
	    sendComment = (Button)findViewById(R.id.comment_send_button);
	    commentContent = (EditText)findViewById(R.id.comment_comment_edit);
	    
	    sendComment.setOnClickListener(this);
	    refreshComment();
	    
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.comment_send_button:
			
		    String DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.KOREA).format(new Date());
		    Comment comment = new Comment(
					 Integer.parseInt(articleNumber),
					 commentWriter.getText().toString(),
					 DATE,
					 commentContent.getText().toString()
					 );
			commentWriter.setText("");
			commentContent.setText("");
			Proxy proxy = new Proxy();
			proxy.uploadComment(Integer.parseInt(articleNumber), comment);
			
			refreshComment();
			
			finish();
			startActivity(getIntent());
		}
		
	}
	
	private final Handler handler = new Handler(); 
	public void refreshComment(){
		try{
			new Thread(){
				public void run(){
					Proxy proxy = new Proxy();
					String commentJson = proxy.getCommentJson();
					dao.insertComment(commentJson);
					
					handler.post(new Runnable(){
						public void run(){
							commentView();
						}
					});
				}
			}.start();
			
			
		}catch(Exception e){
			Log.e("test","Getting comment ERROR : " + e);
			e.printStackTrace();
		}		
	}
	
	private void commentView(){
		try{
			commentList = dao.getCommentList();

			for(int i = 0 ; i < commentList.size(); i++){
				if(commentList.get(i).getArticleNumber() == getIntent().getExtras().getInt("articleNumber")){
					commentListForEachArticle.add(commentList.get(i));
				}
			}
			commentListView = (ListView)findViewById(R.id.comment_comment_listView);
			 CustomAdapter_commentList adapter = new CustomAdapter_commentList(
			    		this, R.layout.activity_comment_list_row, commentList);
			commentListView.setAdapter(adapter);
		}catch(Exception e){
			Log.i("test","commentView ERROR :" + e);
			e.printStackTrace();
		}
	}

	

	
}
