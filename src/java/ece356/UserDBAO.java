package ece356;

import java.sql.*;

public class UserDBAO {
    //public static final String url = "jdbc:mysql://localhost:3306/";
    public static final String url = "jdbc:mysql://eceweb.uwaterloo.ca:3306/";
    public static final String user = "user_p3bhatt";
    public static final String pwd = "user_p3bhatt";
    
    public static void testConnection()
            throws ClassNotFoundException, SQLException {
        Statement stmt;
        Connection con;
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, user, pwd);
        stmt = con.createStatement();
        con.close();
    }
}
