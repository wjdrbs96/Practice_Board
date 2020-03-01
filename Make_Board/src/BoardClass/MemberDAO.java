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
	

}
