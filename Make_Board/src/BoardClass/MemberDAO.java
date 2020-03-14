package BoardClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MemberDAO {
	
	 public static int count(String name) throws Exception {

	    	String sql = "select count(title) " +
	    				 "from post " +
	    				 "where title Like ?";
	        // query 작성
	        
	        try (Connection connection = DB.getConnection("board");
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	             statement.setString(1, name + "%");
	             try (ResultSet resultSet = statement.executeQuery()) {
	                 if (resultSet.next())
	                     return resultSet.getInt(1);
	             }
	        }
	        
	        return 0;
	}
	
	 
	// 로그인 할 때 체크하는 메소드 
	public static int loginCheck(String id, String pwd) throws Exception {
		String sql = "select password from member " + "where loginId = ?";

		String dbpw = "";
		try (Connection connection = DB.getConnection("board");
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					dbpw = resultSet.getString("password");
					if (dbpw.equals(pwd))
						return 1; // 아이디와 비번이 같을 때

					else
						return 0; // 아이디와 비번이 다를 때
				}

				else
					return -1; // 아이디가 존재하지 않을 때
			}
		}
       
	}
	
	
	// 게시글 만들면 DB에 추가해주는 메소드
	
	public static void insertPost(Post post) throws Exception {
		String sql = "insert post(memberId, title, content, count) " +
					 "values(?, ?, ?, ?)";
		
		try (Connection connection = DB.getConnection("board");
	            PreparedStatement statement = connection.prepareStatement(sql)) {
	        	statement.setLong(1, post.getMemberId());
	        	statement.setString(2, post.getTitle());
	        	statement.setString(3, post.getContent());
	        	statement.setInt(4, 1);
	        
	            statement.executeUpdate();
	        }
	}
	
	// 회원가입하면 Member를 추가해주는 메소드
	public static void insert(Member member) throws Exception {
        
		String sql = "insert member(loginId, password, name, nickname, email) " +
				     "values(?, ?, ?, ?, ?)";
        
        try (Connection connection = DB.getConnection("board");
             PreparedStatement statement = connection.prepareStatement(sql)) {
        	statement.setString(1, member.getLoginId());
        	statement.setString(2, member.getPassword());
        	statement.setString(3, member.getName());
        	
        	statement.setString(4, member.getNickName());
        	statement.setString(5, member.getEmail());
            // 필요 코드 작성
            statement.executeUpdate();
        }
    }
	
	/*
	검색창으로 제목을 검색할 때  쓰는 메소드
	검색하려는 단어가 "제목" 의 처음이든 중간이든 마지막이든 들어있으면 모두 나오게 만들려고 만듬
	*/
	public static List<Post> findBytitle(String name, int currentPage, int pageSize) throws Exception {
		String sql = "select * " +
					 "from post " +
					 "where title Like ? or title Like ? or title Like ? " + 
					 "Limit ?, ?";
		
		if (name == "") name = null;         // 코드 실행하다 보면 name=""으로 넘어오는 경우가 있어서 만듬
		try (Connection connection = DB.getConnection("board");
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	        	    statement.setString(1, name + "%");
	        	    statement.setString(2, "%" + name);
	        	    statement.setString(3, "%" + name + "%");
	        	    statement.setInt(4, (currentPage - 1) * pageSize);
	                statement.setInt(5, pageSize);
	                List<Post> list = new ArrayList<Post>();
	                
	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
		                Post post = new Post();
		                post.setPostId(resultSet.getLong("postId"));
		                post.setMemberId(resultSet.getLong("memberId"));
		                post.setTitle(resultSet.getString("title"));
		                post.setContent(resultSet.getString("content"));
		                post.setCount(resultSet.getInt("count"));
		                post.setCreateDateTime(resultSet.getDate("createDateTime"));
		          
		                list.add(post);
		            }
	                
	                return list;
	            }
	            
	   }
		
    }
	
	
	//게시글을 누를 때 마다 조회수가 자동으로 올라가게 만드는 메소드
	public static void Countup(long id, int count) throws Exception {
		String sql = "Update post " +
				     "set count = ? " +
				     "where postId = ?";
		
		try (Connection connection = DB.getConnection("board");
	            PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setInt(1, count + 1);
	            statement.setLong(2, id);
	            statement.executeUpdate();
	    }
	}
	
	
	// 실행 하다보면 게시글 번호를 통해서 post 테이블을 찾아서 다른 컬럼을 찾아서 써야할 때가 있어서 만든 메소드
	public static Post findByPostId(int id) throws Exception {
		String sql = "select p.postId, p.title, p.content, p.count, m.name, p.createDateTime " +
					 "from member m join post p on m.memberId = p.memberId " +
					 "where p.postId = ?";
		
		 try (Connection connection = DB.getConnection("board");
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	             statement.setInt(1, id);
	             try (ResultSet resultSet = statement.executeQuery()) {
	            	  while (resultSet.next()) {
			                Post post = new Post();
			                post.setPostId(resultSet.getLong("postId"));
			                post.setTitle(resultSet.getString("title"));
			                post.setContent(resultSet.getString("content"));
			                post.setCount(resultSet.getInt("count"));
			                post.setName(resultSet.getString("name"));
			                post.setCreateDateTime(resultSet.getDate("createDateTime"));
			                return post;
			                
			          }
	            	
	                         
	           }
	             
	    }
		 
		return null;
	
	}
	
	
	//MemberId 로 멤버 테이블을 찾아주는 메소드
	public static int findByMemberId(String ID) throws Exception {
		String sql = "select memberId " +
					 "from member " +
					 "where loginId = ?";
		
		try (Connection connection = DB.getConnection("board");
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	             statement.setString(1, ID);
	             try (ResultSet resultSet = statement.executeQuery()) {
	            	  while (resultSet.next()) {
	            		  return resultSet.getInt("memberId");
			          }	            	                         
	             }
	             
	    }
		return -1;
		
	}
	
	
	// 댓글 삭제할 때 번호 찾아서 삭제하기 위한 메소드
	public static List<Comment> findByCommentId(int ID) throws Exception {
		String sql = "select c.commentid, c.content " +
					 "from comment c " +
					 "where commentid = ?";
				
		try (Connection connection = DB.getConnection("board");
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	             statement.setInt(1, ID);
	             List<Comment> list = new LinkedList<>();
	             try (ResultSet resultSet = statement.executeQuery()) {
	            	  while (resultSet.next()) {
	            		  Comment comment = new Comment();
	            		  comment.setCommentId(resultSet.getInt("commentid"));
	            		  comment.setContent(resultSet.getString("content"));
	            		  list.add(comment);
	            		  return list;
			          }
	            	  
	         
	             }
	             
	    }
		
		return null;
	}
	
	
	// 댓글을 입력하면 DB에 넣어주는 메소드
	public static void CommentInsert(String title, int postID) throws Exception {
		Post post = MemberDAO.findByPostId(postID);
		
		String sql = "insert into comment(postId, memberId, content) " +
					 "values(?,?,?)";
		
		try (Connection connection = DB.getConnection("board");
	            PreparedStatement statement = connection.prepareStatement(sql)) {
	        	statement.setLong(1, post.getPostId());
	        	statement.setLong(2, post.getMemberId());
	        	statement.setString(3, title);
	      
	            statement.executeUpdate();
	    }
		
		
	}
	
	
	// "댓글" 버튼을 누르면 댓글 전부다 보여주기 위해서 만든 메소드
	public static List<Comment> findByAllComment() throws Exception {
		String sql = "select c.* " +
					 "from comment c join post p " +
					 "on c.postId = p.postId";
		
		
		try (Connection connection = DB.getConnection("board");
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	             List<Comment> list = new ArrayList<>();
	                
	            try (ResultSet resultSet = statement.executeQuery()) {
	                 while (resultSet.next()) {
		                 Comment comment = new Comment();
		                 comment.setCommentId(resultSet.getLong("commentId"));
		                 comment.setPostId(resultSet.getLong("PostId"));
		                 comment.setMemberId(resultSet.getLong("memberId"));
		                 comment.setContent(resultSet.getString("content"));
		                 comment.setCreateDateTime(resultSet.getDate("createDateTime"));
		          
		                 list.add(comment);
		             }
	                
	                 return list;
	            }
	   }
		
		
	}
	
	
	// 멤버 이름으로 찾는 메소드 
	public static String findByName(String ID) throws Exception {
		String sql = "select name " +
					 "from member " +
					 "where loginId = ?";
		
		try (Connection connection = DB.getConnection("board");
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	             statement.setString(1, ID);
	             try (ResultSet resultSet = statement.executeQuery()) {
	            	  while (resultSet.next()) {
	            		  return resultSet.getString("name");
			          }	            	                         
	             }
	             
	    }
		
		return null;
	}

	// 게시글 수정하면 업데이트해주는 메소드
	public static void Update(Post post) throws Exception {
		
		String sql = "Update post " +
					 "set title = ?, content = ? " +
					 "where postId = ?";
		
		
		try (Connection connection = DB.getConnection("board");
	            PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, post.getTitle());
	            statement.setString(2, post.getContent());
	            //statement.setString(3, post.getName());
	            //statement.setDate(4, (Date) post.getCreateDateTime());   // 이게 맞는건가 이 줄
	            statement.setLong(3, post.getPostId());
	            statement.executeUpdate();
	    }
		
		String sql2 = "Update member " +
				      "set name = ? " + 
				      "where memberId = ?";
		
		try (Connection connection = DB.getConnection("board");
	            PreparedStatement statement = connection.prepareStatement(sql2)) {
	            statement.setString(1, post.getName());
	            statement.setLong(2, post.getMemberId());   
	            statement.executeUpdate();
	    }
		
	}
	
	
	// 게시글 삭제하는 메소드
	public static void Delete(int id) throws Exception {
		String sql = "Delete from post " +
				     "where postId = ?";
   

		try (Connection connection = DB.getConnection("board");
			PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		}
		
	}
	
	public static void DeleteComment(int id) throws Exception {
		String sql = "Delete from comment " +
					 "where commentid = ?";
		
		try (Connection connection = DB.getConnection("board");
				PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setInt(1, id);
				statement.executeUpdate();
		}
		
	}
	
	
 	

}
