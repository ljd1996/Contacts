package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {

    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url="jdbc:mysql://localhost:3306/web_data?useUnicode=true&characterEncoding=UTF-8";
    private static final String username="root";
    private static final String password="9202410250";
    private static Connection conn=null;
    static {
        try {
            Class.forName(driver);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public static Connection getConnection() throws Exception {
        if(conn==null) {
            conn = DriverManager.getConnection(url, username, password);
            return conn;
        }
        return conn;
    }
}
