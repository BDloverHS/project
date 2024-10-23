package org.koreait.global.validators;

/**
 * 자료형 체크
 *
 */

// 문자형과 숫자형을 나눈 이유 : 필요한 것만 뽑아서 사용하기 위해
// 숫자인지 아닌지 체크
public interface TypeValidator {
    default boolean isNumber(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}