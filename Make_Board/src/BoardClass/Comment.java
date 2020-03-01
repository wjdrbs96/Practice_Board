package BoardClass;

import java.util.Date;

public class Comment {
	long commentId;
	long postId;
	long memberId;
	String content;
	Date createDateTime;
	
	public long getCommentId() {
		return commentId;
	}
	
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	
	public long getPostId() {
		return postId;
	}
	
	public void setPostId(long postId) {
		this.postId = postId;
	}
	
	public long getMemberId() {
		return memberId;
	}
	
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getCreateDateTime() {
		return createDateTime;
	}
	
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	} 

}
