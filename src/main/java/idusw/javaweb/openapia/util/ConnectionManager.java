package idusw.javaweb.openapia.util;

import java.sql.*;

public class ConnectionManager{
    private static ConnectionManager instance = new ConnectionManager();
    private ConnectionManager() {}
    public static ConnectionManager getInstance(){return instance;}
    public Connection getConnection() {
        Connection conn = null;

        String dbName = "db_a201812015";
        String jdbcUrl = "jdbc:mysql://localhost:3306/"+dbName+"?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
        String dbUser = "ua201812015";
        String dbPass = "cometure";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Driver를 메모리에 적재
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);  //dbms 연결
        } catch(SQLException e) {
            System.out.println("Connection Fail - ");
        } finally {
            return conn;
        }
    }
}
