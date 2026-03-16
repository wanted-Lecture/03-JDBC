package com.wanted.b_template;


import java.sql.Connection;

import static com.wanted.b_template.JDBCTemplate.close;
import static com.wanted.b_template.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {

        Connection con = getConnection();
        System.out.println("con = " + con);

        close(con);
    }
}
