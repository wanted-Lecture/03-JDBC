package com.wanted.crud;

import com.wanted.crud.course.controller.CourseController;
import com.wanted.crud.course.model.service.CourseService;
import com.wanted.crud.course.view.CourseInputView;
import com.wanted.crud.course.view.CourseOutputView;
import com.wanted.crud.global.config.JDBCTemplate;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {

        try (Connection con = JDBCTemplate.getConnection()) {

            System.out.println("✅ 데이터베이스 연결 성공!!!");
            JDBCTemplate.printConnectionStatus();

            /* comment.
            *   request 시
            *   Application -> CourseInputView -> CourseController -> CourseService
            *   -> CourseDAO -> MySQL (DBMS)를 호출
            *   response 시
            *   역순이다. 다만 CourseOutputView를 통해 결과물을 보여줄 것이다.
            * */

            /**
             * 문서화 주석
             * @deprecated 현재 아래에 작성 될 코드는 나중에는 사라지는 코드
             */

            CourseService service = new CourseService(con);
            CourseController controller = new CourseController(service);
            CourseOutputView outputView = new CourseOutputView();
            CourseInputView inputView = new CourseInputView(controller, outputView);

            // Application 이 실행되면 View 메소드 호출한다.
            inputView.displayMainMenu();



        } catch (SQLException e) {
            System.err.println("🚨 데이터 베이스 연결 실패... 🚨");
        } finally {
            JDBCTemplate.cloase();
            System.out.println("🔚 데이테 베이스 종료...");
        }


    }
}
