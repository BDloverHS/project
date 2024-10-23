package org.koreait.global.validators;

/**
 * 필수 항목 체크
 *
 */
public interface RequiredValidator {

    /**
     * 문자열 값이 있는지 체크(공백 역시 빈 문자열이라고 판단)
     *
     * @param str : 문자열
     */

    // 값이 있는지 없는지 체크
    default boolean check(String str) {
        return str != null && !str.isBlank();
    }
}