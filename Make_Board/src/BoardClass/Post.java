package BoardClass;

import java.util.Date;

public class Post {
	long postId;
	long memberId;
	String title;
	String content;
	int count;
	Date createDateTime;
	
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
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public Date getCreateDateTime() {
		return createDateTime;
	}
	
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
}
