package org.koreait.global;

import java.util.HashMap;
import java.util.Map;

/**
 * 객체 컨테이너
 *
 * 컨트롤러, 서비스 객체는 [싱글턴 패턴]으로 [컨테이너에서 관리]
 * getBean으로 최초 조회시 객체 저장소에 저장하고 두번째 조회시에는 객체 저장소에서 해당 객체를 찾아서 반환한다.
 * 다만 필요에 따라 매번 생성하는 객체도 고려하여 구현
 */

public class BeanContainer {

    // Map : key와 value 형태로 정의
    private final static Map<String, Object> beans; // 객체 컨테이너

    static {
        beans = new HashMap<>();
    }

    /**
     * 객체 조회
     *
     * @param clazz
     * @param isPrototype : true - 매번 새로운 객체 생성, false - 싱글톤 방식
     * @return 조회된 또는 생성된 객체
     * @param <T>
     */

    // 메인 컨트롤러로 인해 T가 정의됨.
    // 동적 객체 생성을 위함
    public static <T> T getBean(Class<T> clazz, boolean isPrototype) {

        // Class<T> clazz :
        // boolean isPrototype : false이면 싱글톤 / true이면 매번 객체 생성

        // 전체 클래스명
        String key = clazz.getName();
        /**
         * 매번 객체를 생성하는 경우(isPrototype - true)는 null로 obj의 초기값을 설정하여 객체를 동적 생성하게 처리하고 싱글톤(isPrototype - false)인 경우라면 객체 컨테이너에서 조회를 한다.
         */

        // false 일 때 객체 조회
        Object obj = isPrototype ? null : beans.get(key);

        if (obj == null) {  // 없다면 동적으로 새로 생성
            try {
                obj = clazz.getDeclaredConstructor().newInstance();

                beans.put(key, obj); // 최초 생성한 객체는 컨테이너에 저장한다.

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return (T)obj;
    }

    
    /**
     * 객체를 기본 생성자 싱글톤 방식으로 조회
     *
     * @param clazz
     * @return
     * @param <T>
     */

    // 메서드 오버로드를 사용
    public static <T> T getBean(Class<T> clazz) {
        return getBean(clazz, false);
    }
}
