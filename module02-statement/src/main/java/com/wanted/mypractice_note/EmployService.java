package com.wanted.mypractice_note;

import com.wanted.common.EmployeeDTO;
import com.wanted.common.JDBCTemplate;
import com.wanted.common.SearchDAO;

import java.sql.Connection;
import java.util.List;

import static com.wanted.common.JDBCTemplate.close;

public class EmployService {
    private SearchDAO dao = new SearchDAO();

    public List<EmployeeDTO> findAll() {

        // DB 열기
        Connection con = JDBCTemplate.getConnection();

        // DAO에서 데이터 받아오기
        List<EmployeeDTO> empList = dao.selectAllDatabase(con);

        close(con);

        return empList;
    }

    public List<EmployeeDTO> findByEmpId() {
        Connection con = JDBCTemplate.getConnection();

        List<EmployeeDTO> empList = dao.selectById(con);

        close(con);

        return empList;
    }
}
