package org.nhnnext.JaeBong.nextagram;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter_articleList extends ArrayAdapter<Article>
{
	private Context context;
	private int layoutResourceId;
	private ArrayList<Article> Article;
	
	public CustomAdapter_articleList(Context context ,int layoutResourceId, ArrayList<Article> Article)
	{
		super(context,layoutResourceId,Article);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.Article = Article;
	}
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
			
		if(row == null)
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent,false);
		}
		
		ImageView articleImg = (ImageView)row.findViewById(R.id.custom_list_row_articleImg);
		TextView articleTitle = (TextView)row.findViewById(R.id.custom_list_row_articleTitle);
		TextView articleContent = (TextView)row.findViewById(R.id.custom_list_row_articleContent);
		
		articleTitle.setText(Article.get(position).getTitle());
		articleContent.setText(Article.get(position).getContent());
		
		String img_path = context.getFilesDir().getPath() + "/" + Article.get(position).getImgName();
		File img_load_path = new File(img_path);
		
		if(img_load_path.exists()){
			Bitmap bitmap = BitmapFactory.decodeFile(img_path);
			articleImg.setImageBitmap(bitmap);
		}
		
		
		return row;
	}

	
}
