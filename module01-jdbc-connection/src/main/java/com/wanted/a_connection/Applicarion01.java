package com.wanted.a_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Applicarion01 {
    public static void main(String[] args) {


        /* MySQL 접속을 위한Connection 객체 생성*/

        // finally 블럭에서 자원 해제를 위해 null로 초기화
        Connection con = null;

        try {
            // 사용할 드라이버 등록
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connection은 인터페이스이기 때문에 직접 객체를 생성하지 못한다.
            // 따라서 Connection을 생성해주는 DriverManager를 통해
            // 우리가 사용할 DB의 정보를 넘겨주며 객체를 생성한다.
            con = DriverManager.getConnection("jdbc:mysql://localhost/employee",
                    "wanted",
                    "wanted");

            System.out.println(con);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
