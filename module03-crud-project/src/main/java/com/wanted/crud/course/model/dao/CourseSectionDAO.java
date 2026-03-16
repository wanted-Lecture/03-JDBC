package com.wanted.crud.course.model.dao;

import java.sql.Connection;

public class CourseSectionDAO {

    private final Connection connection;

    public CourseSectionDAO(Connection connection) {
        this.connection = connection;
    }
}
