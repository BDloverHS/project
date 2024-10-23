package org.koreait.global;

import org.koreait.global.exceptions.CommonException;
import org.koreait.global.libs.Utils;
import org.koreait.main.controllers.MainController;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Router {
    // 사용자에게 입력 받기
    // 정적인 이유 - 매번 Scanner를 만들 필요없게 하기 위해.
    public static final Scanner sc;

    // 스캐너를 사용하기 위함
    static {
        sc = new Scanner(System.in);
    }

    /**
     * 컨트롤러 라우터 실행
     *
     */

    // 처음 실행됨
    public void execute() {
        // 무한 반복
        while(true) {
            try {
                // 시작 시 메인 컨트롤러가 나올 수 있도록 해줌
                Utils.loadController(MainController.class); // 컨트롤러 변경
            } catch (Exception e) { // 예외처리
                // 예외 공통 출력 처리 S
                int code = 500; // 우리가 정의한 예외가 아니라면 500으로 예외 코드 고정

                // 우리가 정의한 예외라면 그 예외 코드로 교체
                // reflection으로 유입된 경우
                if (e instanceof InvocationTargetException targetException) {
                   Throwable throwable = targetException.getTargetException();
                   if (throwable instanceof CommonException exception) {
                       code = exception.getCode();
                   }
                }
                // 그 외
                if (e instanceof CommonException commonException) { // 다형성을 이용한 예외처리
                    code = commonException.getCode();
                }

                System.out.printf("[%d]%s%n", code, e.getMessage());
                // 예외 공통 출력 처리 E
            }
        }
    }
}
