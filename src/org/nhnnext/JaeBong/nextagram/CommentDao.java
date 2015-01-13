package org.nhnnext.JaeBong.nextagram;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CommentDao {
	private Context context;
	private SQLiteDatabase database;
	
	public CommentDao(Context context)
	{
		this.context = context;
		try
		{
		database = context.openOrCreateDatabase("LocalDATA.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		
		String commentSql = "CREATE TABLE IF NOT EXISTS Comments(ArticleNumber integer not null,"
				+ "									CommentNumber integer not null,"
				+ "									CommentWriter text not null,"
				+ "									CommentDate text not null,"
				+ "									Comment text not null,"
				+ "	PRIMARY KEY (CommentNumber, ArticleNumber));";
		database.execSQL(commentSql);
		
		
			
		} catch (Exception e)
		{
			Log.e("test", "CREATE TABLE FAILED! - " + e);
			e.printStackTrace();
		}
		
		
	}

	public void insertComment(String jsonData)
	{
		int articleNumber;
		int commentNumber;
		
		String commentWriter;
		String commentDate;
		String comment;
		
		try
		{
			JSONArray jArr = new JSONArray(jsonData);
			
			for(int i = 0 ; i < jArr.length() ; i++)
			{
				JSONObject jobj = jArr.getJSONObject(i);
				
				articleNumber = jobj.getInt("ArticleNumber");
				commentNumber = jobj.getInt("CommentNumber");
				commentWriter = jobj.getString("CommentWriter");
				commentDate = jobj.getString("CommentDate");
				comment = jobj.getString("Comment");
				
				
				Log.i("test", "ArticleNumber : " + articleNumber + " CommentNumber : " + commentNumber);
		
				
				try
				{
					String sql = "INSERT INTO Comments(ArticleNumber, CommentNumber, CommentWriter, CommentDate, Comment)"
							+ "		VALUES('" + articleNumber + "', '" + commentNumber + "', '" + commentWriter + "', '" + commentDate + "', '"
							+ comment + "');";
					
					database.execSQL(sql);
					
				} catch(Exception e)
				{
					Log.e("test","DB Insert ERROR(comment)! - " + e);
					e.printStackTrace();
				}
				
			}
			
		} catch(JSONException e)
		{
			Log.e("test", "JSON ERROR! - " + e);
			e.printStackTrace();
		}
	}

	public ArrayList<Comment> getCommentList() {
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		
		int articleNumber;
		int commentNumber;
		String commentWriter;
		String commentDate;
		String comment;
		
		String sql = "SELECT * FROM Comments;";
		
		Cursor cursor = database.rawQuery(sql, null);
		
		while(cursor.moveToNext()){  // cursor는 row를 한칸씩 옮겨가며 가리키는 역
			articleNumber = cursor.getInt(0);
			commentNumber = cursor.getInt(1);
			commentWriter = cursor.getString(2);
			commentDate = cursor.getString(3);
			comment = cursor.getString(4);
			Log.i("test",articleNumber +"," + commentNumber + "," + commentWriter +"," + commentDate + "," + comment);
			commentList.add(new Comment(articleNumber, commentNumber, commentWriter, commentDate, comment));
		}
		
		cursor.close();
		
		
		return commentList;
	}

	public void deleteComment(int articleNumber, int commentNumber) {
	
		String sql = "DELETE FROM Comments WHERE ArticleNumber=" + articleNumber +" AND CommentNumber=" + commentNumber +";";
		try{
			database.execSQL(sql);
		} catch(Exception e){
			Log.e("test","Comment delete ERROR(SQLight)! - " + e);
			e.printStackTrace();
		}
		
	}
	
	
}
