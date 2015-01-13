package org.nhnnext.JaeBong.nextagram;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

import com.google.gson.JsonArray;

public interface NextagramService {
	@GET("/loadData")
	JsonArray getJSON();
	
	@GET("/loadComment")
	JsonArray getComment();
	
	@Multipart
	@Headers("Cache-Control: no-cache; Connection: Keep-Alive")
	@POST("/upload")
	void uploadData(
			@Part("title") String title,
			@Part("writer") String writerName,
			@Part("id") String writerID,
			@Part("content") String content,
			@Part("writeDate") String writeDate,
			@Part("imgName") String imageName,
			@Part("uploadedfile") TypedFile imageFile,
			Callback<DefaultResponse> cb
			);
	
	@FormUrlEncoded
	@POST("/uploadComment")
	void uploadComment(
			@Field("articleNumber") int articleNum,
			@Field("commentWriter") String commentWriter,
			@Field("commentDate") String commentDate,
			@Field("comment") String comment,
			Callback<DefaultResponse> cb
			);
	
	@FormUrlEncoded
	@POST("/deleteComment")
	void deleteComment(
			@Field("articleNumber") int articleNum,
			@Field("commentNumber") int commentNumber,
		Callback<DefaultResponse> cb
		
		);
}
