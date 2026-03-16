package com.wanted.a_statements;

import com.wanted.common.EmployeeDTO;
import com.wanted.common.JDBCTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.wanted.common.JDBCTemplate.close;

public class Application04 {

    public static void main(String[] args) {
        /**
         * comment, 하부르타 시 해야할 일
         *  select * from employee;
         *  Application03 에서 employeeDTO 에 1명을 담았었다.
         *  그렇다면 모든 회원은 어떻게 담을까?
         */

        // 1. Conncent 연결해주기
        Connection con = JDBCTemplate.getConnection();

        // 2. pstmt, rset 초기화 해주기
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        // 객체를 담을 DTO를 생성 근데 ArrayList로
        List<EmployeeDTO> empList = new ArrayList<>();


        try {
            pstmt = con.prepareStatement("select * from employee");
            rset = pstmt.executeQuery();

            while (rset.next()) {
                EmployeeDTO emp = new EmployeeDTO();

                emp.setEmpId(rset.getString("EMP_ID"));
                emp.setEmpName(rset.getString("EMP_NAME"));
                emp.setEmpNo(rset.getString("EMP_NO"));
                emp.setEmail(rset.getString("EMAIL"));
                emp.setPhone(rset.getString("PHONE"));
                emp.setDeptCode(rset.getString("DEPT_CODE"));
                emp.setJobCode(rset.getString("JOB_CODE"));
                emp.setSalLevel(rset.getString("SAL_LEVEL"));
                emp.setSalary(rset.getInt("SALARY"));
                emp.setBonus(rset.getDouble("BONUS"));
                emp.setManagerId(rset.getString("MANAGER_ID"));
                emp.setHireDate(rset.getDate("HIRE_DATE"));
                emp.setEntDate(rset.getDate("ENT_DATE"));
                emp.setEntYn(rset.getString("ENT_YN"));

                empList.add(emp);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(rset);
            close(con);
        }

        for (EmployeeDTO emp : empList) {
            System.out.println(emp);
        }

    }

}
