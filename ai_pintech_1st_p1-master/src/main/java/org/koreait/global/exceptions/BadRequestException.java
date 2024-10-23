package org.koreait.global.exceptions;

/**
 * 사용자의 입력이 프로그램 처리에서 의도된 것과 다른 경우,
 * 즉, 사용자의 입력 데이터 검증에 실패한 경우에 해당 예외를 사용할 수 있습니다.
 * 이 예외는 예외 코드를 400으로 고정합니다.
 */
public class BadRequestException extends CommonException {
    public BadRequestException() {
        // 기본 문구 고정
        this("잘못된 요청입니다.");
    }

    public BadRequestException(String message) {
        super(message, 400);
    }
}
