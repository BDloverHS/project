package org.koreait.global.exceptions;

/**
 * 애플리케이션에서 발생하는 예외는 체계를 정합니다.
 * 저희가 정의하는 모든 예외는 CommonException의 하위 클래스가 될 수 있도록 체계를 정하며
 * 경우에 따라서는 상속의 상속 관계 처럼, 좀더 세부적으로 예외 클래스를 정의할 수 도 있습니다.
 * 예외의 체계성을 위해 예외 코드를 저희가 미리 정하며, 예외 코드 및 사용자 전달 문구를 함께 설정할 수 있도록 구현 합니다.
 */
public class CommonException extends RuntimeException {

    private int code; // 예외 코드

    // 생성자 정의(에러 코드와 메세지를 받을 수 있도록)
    public CommonException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
