package org.nhnnext.JaeBong.nextagram;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class CustomAdapter_commentList extends ArrayAdapter<Comment> implements OnClickListener{
	
	private Context context;
	private int layoutResourceId;
	private ArrayList<Comment> commentList;
	private int position;

	
	public CustomAdapter_commentList(Context context, int layoutResourceId, ArrayList<Comment> commentList) {
		super(context, layoutResourceId, commentList);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.commentList = commentList;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View row = convertView;
		this.position = position;
		if(convertView == null)
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent,false);
		}
		
		TextView commentWriter = (TextView)row.findViewById(R.id.comment_writerName);
		TextView commentDate = (TextView)row.findViewById(R.id.comment_writeDate);
		 
		TextView comment = (TextView)row.findViewById(R.id.comment_comment);
		
		
		commentWriter.setText(commentList.get(position).getCommentWriter());
		commentDate.setText(commentList.get(position).getCommentDate());
		comment.setText(commentList.get(position).getComment());
		
		ImageButton deleteButton = (ImageButton)row.findViewById(R.id.comment_deleteButton);
		deleteButton.setOnClickListener(this);
		
		return row;
	}

	private final Handler handler = new Handler();
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.comment_deleteButton:
			CommentDao dao = new CommentDao(context.getApplicationContext());
			dao.deleteComment(commentList.get(position).getArticleNumber(),
											commentList.get(position).getCommentNumber());
			new Thread(){
				public void run(){
				Proxy proxy = new Proxy();
				proxy.deleteComment(commentList.get(position).getArticleNumber(),
										commentList.get(position).getCommentNumber());
				
				handler.post(new Runnable(){
					public void run(){
						notifyDataSetChanged();
					}
				});
				};
			}.start();
			
		}
		
	}

}
