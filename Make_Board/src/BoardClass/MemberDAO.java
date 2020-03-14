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
	        // query �ۼ�
	        
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
	
	 
	// �α��� �� �� üũ�ϴ� �޼ҵ� 
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
						return 1; // ���̵�� ����� ���� ��

					else
						return 0; // ���̵�� ����� �ٸ� ��
				}

				else
					return -1; // ���̵� �������� ���� ��
			}
		}
       
	}
	
	
	// �Խñ� ����� DB�� �߰����ִ� �޼ҵ�
	
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
	
	// ȸ�������ϸ� Member�� �߰����ִ� �޼ҵ�
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
            // �ʿ� �ڵ� �ۼ�
            statement.executeUpdate();
        }
    }
	
	/*
	�˻�â���� ������ �˻��� ��  ���� �޼ҵ�
	�˻��Ϸ��� �ܾ "����" �� ó���̵� �߰��̵� �������̵� ��������� ��� ������ ������� ����
	*/
	public static List<Post> findBytitle(String name, int currentPage, int pageSize) throws Exception {
		String sql = "select * " +
					 "from post " +
					 "where title Like ? or title Like ? or title Like ? " + 
					 "Limit ?, ?";
		
		if (name == "") name = null;         // �ڵ� �����ϴ� ���� name=""���� �Ѿ���� ��찡 �־ ����
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
	
	
	//�Խñ��� ���� �� ���� ��ȸ���� �ڵ����� �ö󰡰� ����� �޼ҵ�
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
	
	
	// ���� �ϴٺ��� �Խñ� ��ȣ�� ���ؼ� post ���̺��� ã�Ƽ� �ٸ� �÷��� ã�Ƽ� ����� ���� �־ ���� �޼ҵ�
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
	
	
	//MemberId �� ��� ���̺��� ã���ִ� �޼ҵ�
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
	
	
	// ��� ������ �� ��ȣ ã�Ƽ� �����ϱ� ���� �޼ҵ�
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
	
	
	// ����� �Է��ϸ� DB�� �־��ִ� �޼ҵ�
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
	
	
	// "���" ��ư�� ������ ��� ���δ� �����ֱ� ���ؼ� ���� �޼ҵ�
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
	
	
	// ��� �̸����� ã�� �޼ҵ� 
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

	// �Խñ� �����ϸ� ������Ʈ���ִ� �޼ҵ�
	public static void Update(Post post) throws Exception {
		
		String sql = "Update post " +
					 "set title = ?, content = ? " +
					 "where postId = ?";
		
		
		try (Connection connection = DB.getConnection("board");
	            PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, post.getTitle());
	            statement.setString(2, post.getContent());
	            //statement.setString(3, post.getName());
	            //statement.setDate(4, (Date) post.getCreateDateTime());   // �̰� �´°ǰ� �� ��
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
	
	
	// �Խñ� �����ϴ� �޼ҵ�
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
