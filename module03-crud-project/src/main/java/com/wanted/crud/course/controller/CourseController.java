package com.wanted.crud.course.controller;

import com.wanted.crud.course.model.dto.CourseDTO;
import com.wanted.crud.course.model.service.CourseService;

import java.util.List;

public class CourseController {

    /* comment,
    *   Controller 계층의 책임
    *   - Controller 는 View와 Service 사이를 연결하는 커멘드 센터
    *   - View 가 사용자에게 입력을 받고, 그 입력을 Controller 에게 전달하면
    *   - Controller 는 적절한 Service 계층의 메서드를 호출한다.
    *   -
    *   - Controller가 해야할 일
    *   - 1. View에서 받은 요청을 처리하는 메서드
    *   - 2. Service 메서드 호출 코드
    *   - 3. 필요시 DTO / 객체를 조립하는 코드
    *   -
    *   - Controller가 하면 안되는 일
    *   - 1. Scanner 입력처리
    *   - 2. 출력처리
    *   - 3. SQL 작성
    *   - 4. commit / rollback 작업
     */

    private final CourseService service;

    // 생성자로 초기화
    public CourseController(CourseService service) {
        this.service = service;
    }

    // 강좌 당일 조회
    public CourseDTO findCourseById(long id) {

        return service.findCourseById(id);
    }

    // 강좌 수정
    public CourseDTO updateCourse(long id, String title, String description) {

        return service.updateCourse(id, title, description);

//        return true;
    }
    // 강좌 전체 조회
    public List<CourseDTO> findAllCourse() {

        return service.findAllCourses();
    }

    /**
     * 사용자가 입력한 데이터를 바탕으로 강좌를 삽입
     * @param title 사용자가 입력한 강좌의 제목
     * $param description 사용자가 입력한 강좌의 설명
    * */
    // 강좌등록
    public CourseDTO createCourse(String title, String description) {

        return service.createCourse(title, description);
    }

    // 과정등록2 - Long 타입
    public Long createCourse2(String title, String description) {
        /* comment, 타이틀과 설명은 논리적으로 묶여야 하는 데이터이다.
        *   author_id 는 나중에 로그인을 한 유저 객체에서 추출해서 넣어주어야 한다.
         */
        CourseDTO newCourseDTO = new CourseDTO(null, 1L, title, description, "draft");
        return service.createCourse2(newCourseDTO);
    }

    // 강과 삭제
    public boolean deleteCourse(long id) {

        return service.deleteCourse(id);
    }

}
