package BoardClass;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DB {

    static DataSource dataSource = null;

    public static Connection getConnection(String databaseName) throws SQLException, NamingException {
        if (dataSource == null) {
            InitialContext context = new InitialContext();
            dataSource = (DataSource)context.lookup("java:comp/env/jdbc/" + databaseName);              // 경로의 있는 스키마를 찾아온다 
        }
        return dataSource.getConnection();
    }
}