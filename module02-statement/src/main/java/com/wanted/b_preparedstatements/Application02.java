package com.wanted.b_preparedstatements;

import java.sql.*;
import java.util.Scanner;

import static com.wanted.common.JDBCTemplate.close;
import static com.wanted.common.JDBCTemplate.getConnection;

public class Application02 {
    public static void main(String[] args) {

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            // statement 는 Connection 을 통해 객체 생성,
            // 인터페이스여서 new 사용은 불가
            Scanner sc = new Scanner(System.in);
            System.out.print("조회하실 사번을 입력해주세요 : ");
            String empId = sc.nextLine();

            // SELECT ~~ 문을 STMT에 담아 executeQuery()를 사용하면 괄호 안에 쿼리를 실행시켜주기 때문에
            // 그 결과값을 rset에 담는다.
            pstmt = con.prepareStatement("SELECT EMP_ID, EMP_NAME FROM EMPLOYEE WHERE EMP_ID = ? ");
            /* ? : placeholder
            *  ? 의 개수에 따라 parameterIndex가 결정된다.
            *  ? 값은 항상 set 메서드로 채워줘야한다.
            * */
            pstmt.setString(1, empId);
            rset = pstmt.executeQuery();

            if (rset.next()) {
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
