package org.koreait.product.templates;

import org.koreait.global.Template;

/**
 * 상품 등록 양식 출력
 *
 */
public class ProductForm implements Template {
    @Override
    public void print() {
        StringBuffer sb = new StringBuffer();
        sb.append("상품 등록 항목 안내 작성...");
        System.out.println(sb);
    }
}
