package org.koreait.global;

/**
 *  데이터 전달 및 조회
 *
 */
public class Model {
    private Object data;

    public Model() {}

    public Model(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
