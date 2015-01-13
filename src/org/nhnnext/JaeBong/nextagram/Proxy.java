package org.nhnnext.JaeBong.nextagram;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import android.util.Log;

public class Proxy {
	public static String SERVER_URL = "http://192.168.0.17:5009";
	private RestAdapter restAdapter;
	private NextagramService service;
	
	public Proxy(){
			restAdapter = new RestAdapter.Builder()
			.setEndpoint(SERVER_URL)
			.build();

			service = restAdapter
					.create(NextagramService.class);
		
	}
	
	public String getJson(){
		return service.getJSON().toString();
	}
	
	public void uploadData(Article article,TypedFile imageFile){
		service.uploadData(
				article.getTitle(),
				article.getWriter(),
				article.getId(),
				article.getContent(),
				article.getWriteDate(),
				article.getImgName(),
				imageFile,
				new Callback<DefaultResponse>(){

					@Override
					public void failure(RetrofitError error) {
						Log.i("retrofitError", ""+error);
						
					}

					@Override
					public void success(DefaultResponse uploadResponse, Response response) {
						if (uploadResponse.getStatus() == 200) {
							Log.i("test","uploadSuccess");
						} else  {
							Log.i("test","uploadResponseError");
						}
					}

				}
				);
		
		service.uploadData(
				article.getTitle(),
				article.getWriter(),
				article.getId(),
				article.getContent(),
				article.getWriteDate(),
				article.getImgName(),
				imageFile,
				new Callback<DefaultResponse>(){

					@Override
					public void failure(RetrofitError error) {
						Log.i("retrofitError", ""+error);
						
					}

					@Override
					public void success(DefaultResponse uploadResponse, Response response) {
						Log.d("Proxy", "uploadData(): " + uploadResponse.getStatus());
						if (uploadResponse.getStatus() == 200) {
							Log.i("test","uploadSuccess");
						} else  {
							Log.i("test","uploadResponseError");
						}
					}

				}
				);
	}
	
	
	public String getCommentJson(){
		return service.getComment().toString();
	}
	public void uploadComment(int articleNumber, Comment comment){
		service.uploadComment(articleNumber,comment.getCommentWriter(),comment.getCommentDate(),comment.getComment(),
				new Callback<DefaultResponse>(){

			@Override
			public void failure(RetrofitError error) {
				Log.i("retrofitError", ""+error);
				
			}

			@Override
			public void success(DefaultResponse uploadResponse, Response response) {
				Log.d("Proxy", "uploadData(): " + uploadResponse.getStatus());
				if (uploadResponse.getStatus() == 200) {
					Log.i("test","uploadSuccess");
				} else  {
					Log.i("test","uploadResponseError");
				}
			}

		});
	}
	public void deleteComment(int articleNumber, int commentNumber){
		service.deleteComment(articleNumber, commentNumber,
				new Callback<DefaultResponse>(){

			@Override
			public void failure(RetrofitError error) {
				Log.i("retrofitError", ""+error);
				
			}

			@Override
			public void success(DefaultResponse uploadResponse, Response response) {
				Log.d("Proxy", "uploadData(): " + uploadResponse.getStatus());
				if (uploadResponse.getStatus() == 200) {
					Log.i("test","uploadSuccess");
				} else  {
					Log.i("test","uploadResponseError");
				}
			}

		});
	}
	
}
