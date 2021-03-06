package org.nhnnext.JaeBong.nextagram;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Dao {
	private Context context;
	private SQLiteDatabase database;

	public Dao(Context context) {
		this.context = context;

		database = context.openOrCreateDatabase("LocalDATA.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);

		try {
			String sql = "CREATE TABLE IF NOT EXISTS Articles(ID integer primary key autoincrement,"
					+ "										 ArticleNumber integer UNIQUE not null,"
					+ "										 Title text not null,"
					+ "										 WriterName text not null,"
					+ "										 WriterID text not null,"
					+ "										 Content text not null,"
					+ "										 WriteDate text not null,"
					+ "										 ImgName text UNIQUE not null);";
			database.execSQL(sql);
			
			String commentSql = "CREATE TABLE IF NOT EXISTS Comments(ArticleNumber integer not null,"
					+ "									CommentNumber integer not null,"
					+ "									CommentWriter text not null,"
					+ "									CommentDate text not null,"
					+ "									Comment text not null,"
					+ "	PRIMARY KEY (CommentNumber, ArticleNumber));";
			database.execSQL(commentSql);
			
			
			
			
		} catch (Exception e) {
			Log.e("test", "CREATE TABLE FAILED! - " + e);
			e.printStackTrace();
		}
	}

	public void insertJsonData(String jsonData) {

		int articleNumber;
		String title;
		String writer;
		String id;
		String content;
		String writeDate;
		String imgName;

		FileDownloader fileDownloader = new FileDownloader(context);

		try {
			JSONArray jArr = new JSONArray(jsonData);
			Log.i("test", "JSONArray Length" + jArr.length());
			for (int i = 0; i < jArr.length(); ++i) {
				JSONObject jObj = jArr.getJSONObject(i);

				articleNumber = jObj.getInt("ArticleNumber");
				title = jObj.getString("Title");
				writer = jObj.getString("Writer");
				id = jObj.getString("Id");
				content = jObj.getString("Content");
				writeDate = jObj.getString("WriteDate");
				imgName = jObj.getString("ImgName");

				Log.i("test", "ArticleNumber: " + articleNumber + " Title"
						+ title);

				String sql = "INSERT INTO Articles(ArticleNumber, Title, WriterName, WriterID, Content, WriteDate, ImgName)"
						+ " VALUES("
						+ articleNumber
						+ ", '"
						+ title
						+ "', '"
						+ writer
						+ "', '"
						+ id
						+ "', '"
						+ content
						+ "', '"
						+ writeDate + "', '" + imgName + "');";

				try {
					database.execSQL(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}

				fileDownloader.downFile(Proxy.SERVER_URL + "/image/" + imgName,
						imgName);
			}

		} catch (Exception e) {
			Log.e("test", "JSON ERROR! - " + e);
			e.printStackTrace();
		}

	}

	public Article getArticleByArticleNumber(int articleNumber) {

		Article article = null;

		String title;
		String writer;
		String id;
		String content;
		String writeDate;
		String imgName;

		String sql = "SELECT * FROM Articles WHERE ArticleNumber ="
				+ articleNumber + ";";

		Cursor cursor = database.rawQuery(sql, null);

		cursor.moveToNext();

		articleNumber = cursor.getInt(1);
		title = cursor.getString(2);
		writer = cursor.getString(3);
		id = cursor.getString(4);
		content = cursor.getString(5);
		writeDate = cursor.getString(6);
		imgName = cursor.getString(7);

		article = new Article(articleNumber, title, writer, id, content,
				writeDate, imgName);

		cursor.close();

		return article;
	}

	public ArrayList<Article> getArticleList() {

		ArrayList<Article> articleList = new ArrayList<Article>();

		int articleNumber;
		String title;
		String writer;
		String id;
		String content;
		String writeDate;
		String imgName;

		String sql = "SELECT * FROM Articles;";
		Cursor cursor = database.rawQuery(sql, null);

		while (cursor.moveToNext()) {
			articleNumber = cursor.getInt(1);
			title = cursor.getString(2);
			writer = cursor.getString(3);
			id = cursor.getString(4);
			content = cursor.getString(5);
			writeDate = cursor.getString(6);
			imgName = cursor.getString(7);

			articleList.add(new Article(articleNumber, title, writer, id,
					content, writeDate, imgName));
		}

		cursor.close();

		return articleList;
	}

	
}