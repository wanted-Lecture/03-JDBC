package com.wanted.crud.course.model.dao;

import com.wanted.crud.course.model.dto.CourseDTO;
import com.wanted.crud.course.model.dto.CourseSectionDTO;
import com.wanted.crud.course.model.dto.SectionDTO;
import com.wanted.crud.global.utils.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    /* comment,
     *  DAO 계층의 역활
     *  - Data Access Object 약자
     *  - 즉, DAO 클래스는 DB로 접근하기 위한 마지막 관문
     *    DAO가 해야할 일
     *  - 1. SQL 구문을 실행한다.
     *  - 2. PreparedStatement 생성
     *  - 3. 전달 받은 파라미터를 바인딩한다. (? -> 값 대입)
     *  - 4. ResultSet으로 SQL 결과를 받는다.
     *  - 5. SQL 결과를 Java 객체로 변환한다.
     */

    private final Connection connection;

    public CourseDAO(Connection connection) {
        this.connection = connection;
    }

    // 전체 조회 쿼리문 동작 시키는 메소드
    public List<CourseDTO> findAll() throws SQLException {
        List<CourseDTO> courseList = new ArrayList<>();

        // 동작 시킬 쿼리문 준비
        String query = QueryUtil.getQuery("course.findAll");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
//                convertToDTO(rset);
                courseList.add(convertToDTO(rset));

            }

        }

        return courseList;
    }

    // 단일 조회 쿼리 준비
    public CourseDTO findCourseById(long id) throws SQLException {

        String query = QueryUtil.getQuery("course.findById");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);
            // select의 결과는 ResultSet 객체로 반환된다.
            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return convertToDTO(rset);
                }
            }
        }

        return null;
    }

    // 강좌 등록 쿼리 실행 메서드
    public CourseDTO createCourse(String title, String description) throws SQLException {

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("🚨 과정 제목은 필수로 입력 해주세요.");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("🚨 과정 설명은 필수로 입력 해주세요.");
        }

        String query = QueryUtil.getQuery("course.creatCourse");

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, 16); // 현재는 임의로 설정
            pstmt.setString(2, title);
            pstmt.setString(3, description);

            int resultQueryRow = pstmt.executeUpdate();

            if (resultQueryRow > 0) {
                try (ResultSet rset = pstmt.getGeneratedKeys()) {
                    if (rset.next()) {
                        System.out.println("과정 등록이 완료됐습니다.");
                        Long newCourseId = rset.getLong(1);
                        return findCourseById(newCourseId);
                    }
                }
            } else {
                throw new SQLException("[등록 실패] 과정 등록 중 에러가 발생했습니다.");
            }
        }
        return null;
    }

    // 강좌 등록2 - Long 타입
    public Long save(CourseDTO newCourseDTO) throws SQLException {

        String query = QueryUtil.getQuery("coruse.save");

        try (PreparedStatement pstmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, newCourseDTO.getAuthor_id());
            pstmt.setString(2, newCourseDTO.getTitle());
            pstmt.setString(3, newCourseDTO.getDescription());
            pstmt.setString(4, newCourseDTO.getStatus());

            // dml 구문은 executeUpdate 를 통해 query를 실행한다.
            // 결과 값은 정수 자료형 즉 영향을 받은 행의 갯수가 리턴된다.
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rset = pstmt.getGeneratedKeys();
                if (rset.next()) {
                    return rset.getLong(1);
                }
            }
        }

        return null;
    }

    // 과정 수정 로직
    public CourseDTO updateCourse(long id, String title, String description) throws SQLException {

        if (!existsById(id)) {
            throw new IllegalArgumentException("🚨 수정하려는 강좌(ID: " + id + ")가 존재하지 않습니다.");
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("🚨 변경할 과정 제목 필수 입력 사항 입니다...");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("🚨 변경할 과정 설명은 필수 사항 입니다...");
        }

        String query = QueryUtil.getQuery("course.update");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setLong(3, id);
            pstmt.executeUpdate();
        }

        return findCourseById(id);
    }

    // 강좌 삭제 로직
    public int deleteCourse(long id) throws SQLException {

        if (!existsById(id)) {
            throw new IllegalArgumentException("🚨 삭제하려는 강좌(ID: " + id + ")가 존재하지 않습니다.");
        }

        String query = QueryUtil.getQuery("course.delete");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate();

//            반환 타입 boolean 일 때,
//            int resultRow = pstmt.executeUpdate();
//
//            if (resultRow > 0) {
//                return true;
//            } else {
//                return false;
//            }
        }
    }

    // 코스 + 섹션으로 이루어진 데이터 반환
    public CourseSectionDTO findCourseWithSections(long courseId) throws SQLException {
        String query = QueryUtil.getQuery("course.findCourseWithSections");

        // null로 초기화, 이후 대입 예정
        CourseSectionDTO courseSectionDTO = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, courseId);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                /* comment, 1개의 코스에는 여러개의 섹션이 있다.
                *   1개의 강의 정보만 생성한다.
                * */
                if (courseSectionDTO == null) {
                    courseSectionDTO = new CourseSectionDTO(
                            rset.getLong("course_id"),
                            rset.getLong("author_id"),
                            rset.getString("title"),
                            rset.getString("description"),
                            rset.getString("status")
                    );
                }

                /* comment, LEFT JOIN이기 때문에, section_id는 null 일 수 있다.
                *   getLong(), getInt()는 DB null을 그대로 담지 못하며
                *   null 대신 0으로 처리하여 반환해준다.
                *   따라서 wasNull()메서드로 처리해준다.
                * */

                Long sectionId = rset.getLong("section_id");

                if (!rset.wasNull()) {
                    SectionDTO section = new SectionDTO(
                            sectionId,
                            rset.getLong("section_course_id"),
                            rset.getString("section_title"),
                            rset.getInt("section_order")
                    );

                    courseSectionDTO.getSections().add(section);
                }
            }

        }

        return courseSectionDTO;
    }


    // ================ 편의 메서드 ====================

    // DTO 변환 로직
    private CourseDTO convertToDTO(ResultSet rset) throws SQLException {
        return new CourseDTO(
                rset.getLong("course_id"),
                rset.getLong("author_id"),
                rset.getString("title"),
                rset.getString("description"),
                rset.getString("status")
        );
    }

    // course 존재 여부 확인 메서드, 단순 조회 용도
    public boolean existsById(long id) throws SQLException {
        String query = QueryUtil.getQuery("course.findById");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // 결과가 한 줄이라도 있으면 true, 없으면 false
            }
        }
    }
}
