package com.wanted.crud.course.view;

import com.wanted.crud.course.model.dto.CourseDTO;

import java.util.List;

public class CourseOutputView {
    public void printMessage(String s) {
    }

    public void printError(String message) {
        System.out.println("🚨🚨 " + message);
    }

    public void printCourses(List<CourseDTO> courseList) {

        if (courseList == null || courseList.isEmpty()) {
            System.out.println("조회 된 강좌가 없습니다!");
            return;
        }

        System.out.println("=====================강의 전체 조회 목록 결과=====================");
        for (CourseDTO courseDTO : courseList) {
            System.out.println(courseDTO);
        }

    }

    public void printSuccess(String message) {
        System.out.println("✅ " + message);
    }

    public void printCourseDetail(Object o) {
    }

    public void printCourse(CourseDTO courseDTO) {

        if (courseDTO == null) {
            System.out.println("🚨조회 된 강좌가 없습니다!");
            return;
        }
        System.out.println("=====================강의 목록 결과=====================");
        System.out.println(courseDTO);
    }

    public void printSuccessNewCourse(CourseDTO newCourse) {

        if (newCourse == null) {
            System.out.println("🚨[생성 실패] 강좌 생성 중 에러가 발생했습니다.");
            return;
        }
        System.out.println("===================== 등록된 강좌 정보 =====================");
        System.out.println(newCourse);
    }
}
