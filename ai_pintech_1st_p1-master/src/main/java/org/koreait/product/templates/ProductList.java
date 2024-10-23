package org.koreait.product.templates;

import org.koreait.global.Model;
import org.koreait.global.Template;
import org.koreait.global.libs.Utils;
import org.koreait.product.entities.Product;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 상품 목록 템플릿
 *
 */
public class ProductList implements Template {

    private List<Product> items;

    @Override
    // 필수 데이터
    public void print() {
        // 상품 목록 데이터 - view 출력
        System.out.println("상품목록");
        Utils.drawLine('-', 30);
        if (items != null && !items.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            for (Product item : items) {
                System.out.printf("상품번호: %d / 상품명: %s / 판매가: %d원 / 등록일: %s%n", item.getSeq(), item.getName(), item.getPrice(), formatter.format(item.getRegDt()));
            }
            return;
        }

        System.out.println("등록된 상품이 없습니다.");
    }

    @Override
    public void print(Model model) {
        Object data = model.getData();
        if (data != null) {
            items = (List<Product>)data;
        }

        print();
    }
}
