package com.wanted.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {
    /**
     * Comment.
     *  우리는 DB에 접속할 때 마다 Connection 객체를 사용해야 한다.
     *
     */

    public static Connection getConnection() {

        Connection con = null;

        /* comment, propertices 파일을 읽기 위하 객체 생성 */
        Properties prop = new Properties();

        try {

            prop.load(new FileReader("src/main/java/com/wanted/config/jdbc-config.properties"));

            System.out.println("prop = " + prop);

            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            // 사용할 드라이버 등록
            Class.forName(driver);

            // Connection은 인터페이스이기 때문에 직접 객체를 생성하지 못한다.
            // 따라서 Connection을 생성해주는 DriverManager를 통해
            // 우리가 사용할 DB의 정보를 넘겨주며 객체를 생성한다.
            con = DriverManager.getConnection(url,
                    user,
                    password);

            System.out.println(con);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return con;
    }


    // 사용한 커넥션을 닫아주는 메서드
    public static void close(Connection con) {

        // con 이 null 이 아니거나, 닫혀있지 않을 때, con을 닫는다.
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 사용한 stmt을 닫아주는 메서드
    public static void close(Statement stmt) {

        // stmt 이 null 이 아니거나, 닫혀있지 않을 때, stmt를 닫는다.
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 사용한 rset를 닫아주는 메서드
    public static void close(ResultSet rset) {

        // rset 이 null 이 아니거나, 닫혀있지 않을 때, rset를 닫는다.
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
