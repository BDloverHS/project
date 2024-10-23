package org.koreait.global.libs;

import org.koreait.global.BeanContainer;
import org.koreait.global.Controller;
import org.koreait.global.Model;
import org.koreait.global.Router;
import org.koreait.global.exceptions.BadRequestException;
import org.koreait.global.exceptions.CommonException;
import org.koreait.main.controllers.MainController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Utils {
    /**
     * 구분선 그리기
     *
     * @param ch : 출력할 문자
     * @param length : 출력할 갯수
     */
    public static void drawLine(char ch, int length) {
        for (int i = 0; i < length; i++) {
            System.out.print(ch);
        }
        System.out.println();
    }

    public static void drawLine(char ch) {
        drawLine(ch, 10);
    }

    /**
     * 템플릿 출력
     *
     * Class 클래스는 클래스의 구성 요소의 정보도 조회하나 
     *  reflection 기능, 즉 동적 객체 생성, 동적 메서드 호출, 또는 동적으로 멤버 변수의 값 접근 및 변경 가능합니다.
     *  reflection 기능은 불특정 클래스로 부터 객체를 생성하거나 메서드를 호출할때 즉, 범용적인 접근을 할때 주로 사용할 수 있습니다.
     *
     * @param clazz
     * @param <T>
     * @param model 전송 데이터
     */
    public static <T> T loadTpl(Class<T> clazz, Model model) {
        try {

            // 동적 객체 생성
            Object obj = BeanContainer.getBean(clazz);
            if (model == null) {
                Method method = clazz.getDeclaredMethod("print");
                method.invoke(obj);
            } else { // 템플릿에 전달할 데이터가 있는 경우
                Method method = clazz.getDeclaredMethod("print", Model.class);
                method.invoke(obj, model);
            }

            return (T)obj;
        } catch (Exception e) {
           if (e instanceof InvocationTargetException targetException) {
               Throwable throwable = targetException.getTargetException();
               if (throwable instanceof CommonException commonException) {
                   throw commonException;
               }
           }

           e.printStackTrace();
        }

        return null;
    }

    public static <T> T loadTpl(Class<T> clazz) {
        return loadTpl(clazz, null);
    }

    /**
     * 컨트롤러 실행
     *
     * 메뉴 컨트롤러는 여러개로 정의될 수 있으므로 Controller라는 클래스 공통 자료형을 통해 하나로 묶어 줄수 있음(다형성 활용)
     *
     * @param clazz
     * @return
     * @param <T>
     * @param model : 전송할 데이터
     */
    public static <T> T loadController(Class<T> clazz, Model model) {

        /**
         * 컨트롤러는 공통적으로 run 이라는 메서드가 정의되어 있고 run은 일련의 실행 절차가 정의되어 있다.
         */
        try {
            // 싱글톤
            Object obj = BeanContainer.getBean(clazz); // getbean은 무조건 싱글톤.

            // [Controller인 경우만 처리]
            if (obj instanceof Controller controller) {

                if (model != null) { // 전송 데이터 처리
                    controller.setData(model.getData());
                }

                //
                Method method = clazz.getSuperclass().getDeclaredMethod("run");
                method.invoke(obj);

                return (T) controller;
            }
        } catch (Exception e) { // 예외에 대한 정보 처리
            if (e instanceof InvocationTargetException targetException) {
                Throwable throwable = targetException.getTargetException();
                if (throwable instanceof CommonException commonException) {
                    throw commonException;
                }
            }

            e.printStackTrace();
        }

        return null;
    }

    // 메세지 오버로드
    public static <T> T loadController(Class<T> clazz) {
        return loadController(clazz, null);
    }

    /**
     * 텍스트 입력 처리
     *
     * @param title : 안내 문구
     * @param message : 검증 실패시 안내 문구
     * @return
     */


    public static String getString(String title, String message) {
        Scanner sc = Router.sc;
        while(true) {
            try {
                System.out.print(title + ": ");
                String input = sc.nextLine();
                // 공통적인 부분
                if (commonInputProcess(input, message)) {
                    break;
                }
                return input;
            } catch (CommonException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    /**
     * 숫자 입력 처리
     *
     * 1) 필수 여부 체크
     * 2) 숫자 형식 체크
     * 
     * @param title
     * @param message
     * @return
     */
    public static int getNumber(String title, String message) {

        Scanner sc = Router.sc;
        while(true) {
            try {
                System.out.print(title + ": ");
                String input = sc.nextLine();
                if (commonInputProcess(input, message)) { // 공통 입력 처리
                    break;
                }
                return Integer.parseInt(input);
            } catch (Exception e) {
                if (e instanceof CommonException) {
                    System.out.println(e.getMessage());
                } else {
                    System.out.println("숫자 형식으로 입력하세요.");
                }
            }
        }

        return 0;
    }

    /**
     * 공통 입력 처리
     *
     * @param input
     * @return boolean : true인 경우 반복 중단
     */
    private static boolean commonInputProcess(String input, String message) {
        // 공백이라면 오류 던짐
        if (input == null || input.isBlank()) {
            throw new BadRequestException(message);
        }

        // 입력 문구가 대소문자 구분없이 M인 경우 메인 메뉴로 이동
        if (input.trim().toUpperCase().equals("M")) {
            Utils.loadController(MainController.class);
            return true;
        }

        // 입력 문구가 대소문자 구분없이 Q인 경우 프로그램 종료
        if (input.trim().toUpperCase().equals("Q")) {
            System.out.println("종료합니다.");
            System.exit(1);
        }

        return false;
    }
}
