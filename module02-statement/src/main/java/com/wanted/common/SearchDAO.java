package com.wanted.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class SearchDAO {

    // 전체 조회 메서드
    public List<EmployeeDTO> selectAllDatabase(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<EmployeeDTO> empList = new ArrayList<>();

        System.out.println("전체 조회");

        Properties prop = new Properties();

        try {
            prop.loadFromXML(
                    new FileInputStream("src/main/java/com/wanted/b_preparedstatements/employee-query.xml")
            );

            String query = prop.getProperty("selectAll");

            pstmt = con.prepareStatement(query);

            rset = pstmt.executeQuery();

            while (rset.next()) {
                empList.add(convertToDTO(rset));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return empList;
    }


    // 단일 조회 메서드
    public List<EmployeeDTO> selectById(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<EmployeeDTO> empList = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        System.out.print("조회하실 사번을 입력해주세요 : ");
        String empId = sc.nextLine();

        Properties prop = new Properties();

        try {
            prop.loadFromXML(
                    new FileInputStream("src/main/java/com/wanted/b_preparedstatements/employee-query.xml")
            );

            String query = prop.getProperty("selectEmpById");

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, empId);

            rset = pstmt.executeQuery();

            if (rset.next()) {
                empList.add(convertToDTO(rset));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return empList;
    }

    // dto 변환 메서드
    private EmployeeDTO convertToDTO(ResultSet rset) {
        EmployeeDTO dto = new EmployeeDTO();

        try {
            dto.setEmpId(rset.getString("EMP_ID"));
            dto.setEmpName(rset.getString("EMP_NAME"));
            dto.setEmpNo(rset.getString("EMP_NO"));
            dto.setEmail(rset.getString("EMAIL"));
            dto.setPhone(rset.getString("PHONE"));
            dto.setDeptCode(rset.getString("DEPT_CODE"));
            dto.setJobCode(rset.getString("JOB_CODE"));
            dto.setSalLevel(rset.getString("SAL_LEVEL"));
            dto.setSalary(rset.getInt("SALARY"));
            dto.setBonus(rset.getDouble("BONUS"));
            dto.setManagerId(rset.getString("MANAGER_ID"));
            dto.setHireDate(rset.getDate("HIRE_DATE"));
            dto.setEntDate(rset.getDate("ENT_DATE"));
            dto.setEntYn(rset.getString("ENT_YN"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("dto = " + dto);
        return dto;
    }
}
