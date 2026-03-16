package com.wanted.crud.course.model.service;

import com.wanted.crud.course.model.dao.CourseDAO;
import com.wanted.crud.course.model.dao.CourseSectionDAO;
import com.wanted.crud.course.model.dto.CourseDTO;
import com.wanted.crud.course.model.dto.CourseSectionDTO;
import com.wanted.crud.course.model.dto.SectionDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CourseService {

    /* comment.
    *   Service 계층의 역활
    *   - Service 계층은 비지니스 로직을 담당하는 계층이다.
    *   - 비즈니스 로직이란?
    *   - 단순한 SQL을 실행하는 것이 아닌,
    *   - 우리 프로젝트(회사) 규칙 상 어떤 순서로 어떤 처리를 하는가를
    *   - 다루는 구문을 의미한다.
    *   - Service 계층이 해야할 일
    *   - 1. DAO를 호출
    *   - 2. 데이터를 검증/가공
    *   - 3. 필요 시 여러 DAO를 조합 (EX - 강의를 삽입할 때 강의에 포함되는 챕터, 섹션 삽입)
    *   - 4. 트랜잭션 처리 (commit / rollback)
    *   - 5. 예외처리
    *   -
    *   - Service 하면 안되는 일
    *   - 컨트롤러와 비슷 생량
     */

    private final CourseDAO courseDAO;
    private final CourseSectionDAO courseSectionDAO;
    private final Connection connection;


    public CourseService(Connection connection) {
        this.courseDAO = new CourseDAO(connection);
        this.courseSectionDAO = new CourseSectionDAO(connection);
        this.connection = connection;
    }

    public List<CourseDTO> findAllCourses() {

        try {
            return courseDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("강의 전체 조회 중 에러 발생!! 🚨🚨" + e);
        }
    }

    public CourseDTO findCourseById(long id) {
        try {
            return courseDAO.findCourseById(id);
        } catch (SQLException e) {
            throw new RuntimeException("강의 단일 조회 중 에러 발생!! 🚨🚨" + e);
        }
    }

    // 강좌 생성
    public CourseDTO createCourse(String title, String description) {
        try {
            return courseDAO.createCourse(title, description);
        } catch (SQLException e) {
            throw new RuntimeException("🚨🚨 과정 생성 중 에러 발생" + e);
        }
    }

    // 강좌 생성2 - Long타입
    public Long createCourse2(CourseDTO newCourseDTO) {

        try {
            return courseDAO.save(newCourseDTO);
        } catch (SQLException e) {
            throw new RuntimeException("🚨🚨 과정 생성 중 에러 발생!!" + e);
        }
    }

    public CourseDTO updateCourse(long id, String title, String description) {
        try {
            return courseDAO.updateCourse(id, title, description);
        } catch (SQLException e) {
            throw new RuntimeException("과정 수정 중 에러 발생" + e);
        }
    }

    public int deleteCourse(long id) {
        try {
            return courseDAO.deleteCourse(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CourseSectionDTO findCourseWithSections(long courseId) {
        try {
            return courseDAO.findCourseWithSections(courseId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createCourseWithDefaultSection(CourseDTO newCourse, SectionDTO newSection) {

        /* comment,
            courses 테이블에 강의를 insert
            course_section 테이블에 섹션 insert
        */

        try {
            /* setAutoCommit 을 끄는 이유는 2개의 작업 중 1개라도 오류가 나면
            * 우리가 직접 rollback을 시키기 위함.
            * 또한 2개의 작업이 정상적으로 마무리 되면 직접 commit 하기 위함.
            *
            * */
            connection.setAutoCommit(false);

            // 메소드란? 하나의 작업을 묶어둔 코드 덩어리
            // 따라서 우리는 필요함에 있어 재호출을 통해 공통적인 작업을 효과적으로 수행할 수 있다.

            // 이전에 만들어두었던, 강의 등록 메서드 재활용
            // generatedCourseId에는 강의 등록 시 생성된 PK 값이 들어있다.
            Long generatedCourseId = courseDAO.save(newCourse);

            if(generatedCourseId == null) {
                throw new SQLException("🚨 강좌 ID 생성에 실패했습니다!");
            }

            // newSection 객체의 courseId는 null 값이다.
            // 하지만 위 쪽에서, 코스를 등록하며 발생한 pk를 알았기 때문에
            // 해당 courseId를 newSection 객체의 courseId 필드에 넣을 것이다.
            newSection.setCourseId(generatedCourseId);

            // insert가 성공적으로 수행되면, result 변수에 1이 담김
            int result = courseSectionDAO.save(newSection);

            if (result == 0) {
                throw new SQLException("섹션 생성에 실패!");
            }

            // 위 쪽에 2개의 논리적 작업이 잘 수행되면 commit()
            connection.commit();
            return true;

        } catch (SQLException e) {
            /* try 구문에서 에러가 발생하면, catch 구문으로 넘어온다.
            * 즉 우리는 try 구문 내에서 에러가 발생하면, catch 구문에서
            * connection 을 rollback 하게 할 것이다. 얘도 try, catch 문으로 감싸기
            * */
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("롤백 중 Error 발생" + ex);
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
