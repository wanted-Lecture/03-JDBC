package com.wanted.mypractice_note;

import com.wanted.common.EmployeeDTO;
import com.wanted.common.JDBCTemplate;

import java.sql.Connection;
import java.util.List;

public class Application {
    public static void main(String[] args) {


        EmployService employService = new EmployService();

        List<EmployeeDTO> list = employService.findByEmpId();


        for (EmployeeDTO emp : list) {
            System.out.println(emp);
        }

    }
}
