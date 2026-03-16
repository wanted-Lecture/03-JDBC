package com.wanted.b_preparedstatements;

import com.wanted.common.EmployeeDTO;

import java.sql.*;
import java.util.Scanner;

import static com.wanted.common.JDBCTemplate.close;
import static com.wanted.common.JDBCTemplate.getConnection;

public class Application01 {
    public static void main(String[] args) {

        /**
         * comment.
         * Statement 는 SQL 쿼리문을 실행할 때마다 SQL 문이
         * DBMS에 전송되어 DBMS 에서 SQL 구문을 파싱(번역)학고
         * 컴파일을 하는 과정을 거치게 된다.
         * PerparedStatement 는 최초에 한 번 실행 시,
         * SQL 구문을 파싱하고 컴파일 하는 것은 동일하지만
         * 동일한 구문을 여러 번 실행하면, 최초에 컴파일 한 SQL을 재사용 하게 된다.
         * 따라서 파싱하고 컴파일하는 과정을 생략하여 성능이 향상된다.
         */


        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            // statement 는 Connection 을 통해 객체 생성,
            // 인터페이스여서 new 사용은 불가
            pstmt = con.prepareStatement("SELECT EMP_ID, EMP_NAME FROM EMPLOYEE");

            // SELECT ~~ 문을 STMT에 담아 executeQuery()를 사용하면 괄호 안에 쿼리를 실행시켜주기 때문에
            // 그 결과값을 rset에 담는다.
            rset = pstmt.executeQuery();

            while(rset.next()) {
                /**
                 * comment, next(): ResultSet을 목록화 시켜
                 *  행이 존재하면, True, 존재하지 않으면 False를 반환
                 */
                System.out.println(rset.getString("EMP_ID") + "번 " +
                        rset.getString("EMP_NAME") + "사원");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(con);
            close(rset);
        }
    }
}
