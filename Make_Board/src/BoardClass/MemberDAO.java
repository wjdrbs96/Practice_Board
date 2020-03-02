package BoardClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
	
	public static int loginCheck(String id, String pwd) throws Exception {
        String sql = "select password from member " +
        			 "where loginId = ?";
        String dbpw = "";
        try (Connection connection = DB.getConnection("board");
                PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                   if (resultSet.next()) {
                	   dbpw = resultSet.getString("password");
                	   if (dbpw.equals(pwd)) 
                		   return 1;              // 아이디와 비번이 같을 때
                	   else 
                		   return 0;              // 아이디와 비번이 다를 때
                   }
                   
                   else
                       return -1;                // 아이디가 존재하지 않을 때 
               }
        }
       
	}
	
	
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
	
	public static List<Post> findBytitle(String name, int currentPage, int pageSize) throws Exception {
		String sql = "select * " +
					 "from post " +
					 "where title Like ? " + 
					 "Limit ?, ?";
		
		if (name == "") name = null;
		try (Connection connection = DB.getConnection("board");
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	        	    statement.setString(1, name + "%");
	        	    statement.setInt(2, (currentPage - 1) * pageSize);
	                statement.setInt(3, pageSize);
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
	
	public static Post findByPostId(int id) throws Exception {
		String sql = "select p.postId, p.title, p.content, m.name, p.createDateTime " +
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
			                post.setName(resultSet.getString("name"));
			                post.setCreateDateTime(resultSet.getDate("createDateTime"));
			                return post;
			          
			       
			        }
	                         
	           }
	             
	    }
		return null;
	
	}
	
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
	
	public static void Delete(int id) throws Exception {
		String sql = "Delete from post " +
				     "where postId = ?";
   

		try (Connection connection = DB.getConnection("board");
			PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		}
		
	}
 	

}
