package org.nhnnext.JaeBong.nextagram;

public class Comment {
	public int articleNumber;
	public int commentNumber;

	public String commentWriter;
	public String commentDate;
	public String comment;

	public Comment(int articleNumber, String commentWriter, String commentDate,
			String comment) {
		super();
		this.articleNumber = articleNumber;
		this.commentWriter = commentWriter;
		this.commentDate = commentDate;
		this.comment = comment;
	}

	public int getArticleNumber() {
		return articleNumber;
	}

	public int getCommentNumber() {
		return commentNumber;
	}

	public Comment(int articleNumber, int commentNumber, String commentWriter,
			String commentDate, String comment) {
		super();
		this.articleNumber = articleNumber;
		this.commentNumber = commentNumber;
		this.commentWriter = commentWriter;
		this.commentDate = commentDate;
		this.comment = comment;
	}

	public String getCommentWriter() {
		return commentWriter;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public String getComment() {
		return comment;
	}

}
