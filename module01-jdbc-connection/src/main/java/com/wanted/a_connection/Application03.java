package com.wanted.a_connection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Application03 {
    public static void main(String[] args) {


        /**
         * comment,
         *  데이터 베이스 관련 정보가 class에 그대로 노출이 되어 있다.
         *  우리는 이를 파일 관련 처리로 민감한 정보를 숨길 것이다.
         */
        Connection con = null;

        /* comment, propertices 파일을 읽기 위하 객체 생성 */
        Properties prop = new Properties();

        try {

            prop.load(new FileReader("src/main/java/com/wanted/a_connection/jdbc-config.properties"));

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


    }
}
