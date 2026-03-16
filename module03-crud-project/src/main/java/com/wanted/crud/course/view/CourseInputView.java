package com.wanted.crud.course.view;

import com.wanted.crud.course.controller.CourseController;
import com.wanted.crud.course.model.dto.CourseDTO;

import java.util.List;
import java.util.Scanner;

public class CourseInputView {

    /* comment/
    *   view 계층의 책임
    *   - 사용자의 입력 or 출력을 담당한다.
    *   - InputView의 할 일
    *   - 사용자가 고를 수 있는 메뉴를 출력한다.
    *   - Scanner를 활용한 입력을 출력한다.
    *   - Controller를 사용자 입력에 맞게 호출한다.
    *   - OutputView를 호출하여 결과를 출력할 수 있게 한다.
    *   -
    *   - InputView가 하면 안되는 일 !
    *   - SQL 작성 X
    *   - 비지니스 로직 처리 X
    *   - commit / Rollback X
     */

    private final CourseController controller;
    private final CourseOutputView outputView;
    private final Scanner sc = new Scanner(System.in);

    // 생성자를 통한 final 변수 초기화
    public CourseInputView(CourseController controller, CourseOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }

    public void displayMainMenu() {

        while (true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("         LMS 실습 메인 메뉴");
            System.out.println("=================================");
            System.out.println("1. 기초 실습");
            System.out.println("2. 중급 실습");
            System.out.println("3. 심화 실습");
            System.out.println("4. 실습 종료");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu) {
                case 1:
                    displayBeginnerMenu();
                    break;
                case 2:
                    displayIntermediateMenu();
                    break;
                case 3:
                    displayAdvancedMenu();
                    break;
                case 4:
                    outputView.printMessage("== 실습을 종료합니다. ==");
                    return;
                default:
                    outputView.printError("다시 선택해주세요.");
            }
        }
    }

    // 기초 실습을 누르면 전체 강좌가 조회된다.
    private void displayBeginnerMenu() {
        outputView.printMessage("\n--- [기초 실습] 강좌 목록 전체 조회 ---");

        List<CourseDTO> courseList = controller.findAllCourse();
        outputView.printCourses(courseList);
    }

    private void displayIntermediateMenu() {
        while (true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("      [중급 실습] 과정 CRUD");
            System.out.println("=================================");
            System.out.println("1. 과정 등록");
            System.out.println("2. 단일 과정 조회");
            System.out.println("3. 과정 수정");
            System.out.println("4. 과정 삭제");
            System.out.println("5. 이전으로 돌아가기");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu) {
                case 1:
                    createCourse(); // author_id 없이 등록이 가능한가?!
                    break;
                case 2:
                    findCourse();
                    break;
                case 3:
                    updateCourse();
                    break;
                case 4:
                    deleteCourse();
                    break;
                case 5:
                    outputView.printMessage("");
                    return;
                default:
                    outputView.printError("");
            }
        }
    }

    private void displayAdvancedMenu() {
        while (true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("         [심화 실습]");
            System.out.println("=================================");
            System.out.println("1. 트랜잭션으로 강좌 개설");
            System.out.println("2. 강좌 상세 조회 (섹션 포함)");
            System.out.println("3. 이전으로 돌아가기");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu) {
                case 1:
                    createCourseWithDefaultSection();
                    break;
                case 2:
                    findCourseWithSections();
                    break;
                case 3:
                    outputView.printMessage("");
                    return;
                default:
                    outputView.printError("");
            }
        }
    }

    private void createCourseWithDefaultSection() {
        outputView.printMessage("\n--- [심화 실습] 트랜잭션으로 강좌 개설 ---");

        boolean result = true;

        if (result) {
            outputView.printSuccess("");
        } else {
            outputView.printError("");
        }
    }

    private void findCourseWithSections() {
        outputView.printMessage("\n--- [심화 실습] 강좌 상세 조회 (섹션 포함) ---");
        System.out.print("조회할 강좌 ID를 입력해주세요 : ");
        long id = inputLong();

        outputView.printCourseDetail(null);
    }

    // 과정 등록 메서드
    // 임시로 강좌 아이디 등록하기
    private void createCourse() {
        System.out.print("과정명을 입력해주세요 : ");
        String title = sc.nextLine();

        System.out.print("과정설명을 입력해주세요 : ");
        String description = sc.nextLine();

        // 내가 짠 로직
//        CourseDTO newCourse = controller.createCourse(title, description);
//        outputView.printSuccessNewCourse(newCourse);

        /* comment,
         *  select 문 같은 경우에는 ResultSet 객체에 SQL 결과가 담기게 된다.
         *  그러면 Update, Insert, Delete는 어떻게 결과가 노출되나?
         *  DML 구문은 SQL 을 execute 하게 되면 영향을 받은 행의 갯수만큼 정수값을
         *  리턴해준다. ex) 1개 행 삽입 완료 -> 1
         */

        // 수업시간 짠 로직
        Long result = controller.createCourse2(title, description);
        if (result != null && result > 0) {
            outputView.printSuccess("과정 등록 성공! 생성된 과정 ID : " + result);
        } else {
            outputView.printError("과정 등록 실패");
        }
    }

    // 단일 과정 조회
    private void findCourse() {
        System.out.print("조회할 과정 번호를 입력해주세요 : ");
        long id = inputLong();

        CourseDTO courseDTO = controller.findCourseById(id);

        outputView.printCourse(courseDTO);
    }

    private void updateCourse() {
        System.out.print("수정할 과정 번호를 입력해주세요 : ");
        long id = inputLong();

        System.out.print("새로운 과정명을 입력해주세요 : ");
        String title = sc.nextLine();

        System.out.print("새로운 과정 설명을 입력해주세요 : ");
        String description = sc.nextLine();

        CourseDTO courseDTO = controller.updateCourse(id, title, description);

        if (courseDTO != null) {
            outputView.printSuccess("========== 과정 수정 성공! 수정 된 과정 목록 조회 ==========");
            outputView.printCourse(courseDTO);
        } else {
            outputView.printError("과정 수정 실패 : 해당 ID의 과정이 없거나 변경에 실패했습니다.");
        }
    }

    private void deleteCourse() {
        System.out.print("삭제할 과정 번호를 입력해주세요 : ");
        long id = inputLong();

        boolean result = controller.deleteCourse(id);

        if (result) {
            outputView.printSuccess("✅ 과정 삭제 성공!");
        } else {
            outputView.printError("과정 삭제 실패 : 해당 ID의 과정이 없습니다.");
        }
    }

    private int inputInt() {
        while (true) {
            try {
                int value = Integer.parseInt(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
            }
        }
    }

    private long inputLong() {
        while (true) {
            try {
                long value = Long.parseLong(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
            }
        }
    }

}
