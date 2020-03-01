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
                		   return 1;              // ���̵�� ����� ���� ��
                	   else 
                		   return 0;              // ���̵�� ����� �ٸ� ��
                   }
                   
                   else
                       return -1;                // ���̵� �������� ���� �� 
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
            // �ʿ� �ڵ� �ۼ�
            statement.executeUpdate();
        }
    }

}
