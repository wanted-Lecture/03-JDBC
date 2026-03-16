package com.wanted.a_statements;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.wanted.common.JDBCTemplate.close;
import static com.wanted.common.JDBCTemplate.getConnection;

public class Application01 {

    public static void main(String[] args) {

        /**
         * Comment.
         *  Jdbc의 핵심적인 인터페이스 2가지
         *  1. Statement
         *  - SQl 문을 저장하고 실행할 수 있는 기능을 가진 인터페이스
         *  2. ResultSet
         *  - SQL문 결과 집합을 받아올 수 있는 인터페이스
         */
        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rset = null;

        try {
            // statement 는 Connection 을 통해 객체 생성,
            // 인터페이스여서 new 사용은 불가
            stmt = con.createStatement();

            // SELECT ~~ 문을 STMT에 담아 executeQuery()를 사용하면 괄호 안에 쿼리를 실행시켜주기 때문에
            // 그 결과값을 rset에 담는다.
            rset = stmt.executeQuery("SELECT EMP_ID, EMP_NAME FROM EMPLOYEE");

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
            close(stmt);
            close(con);
            close(rset);
        }
    }
}
