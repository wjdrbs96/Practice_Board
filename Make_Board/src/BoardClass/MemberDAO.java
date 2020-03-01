package BoardClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class MemberDAO {
	
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

}
