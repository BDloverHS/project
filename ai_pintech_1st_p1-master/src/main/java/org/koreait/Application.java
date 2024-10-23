package org.koreait;

import org.koreait.global.Router;

public class Application {
    public static void main(String[] args) {
        Router router = new Router();
        router.execute(); // 라우터 실행
    }
}
