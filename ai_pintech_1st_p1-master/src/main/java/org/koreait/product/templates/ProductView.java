package org.koreait.product.templates;

import org.koreait.global.Model;
import org.koreait.global.Template;
import org.koreait.product.entities.Product;

/**
 * 상품 상세 보기 출력
 *
 */
public class ProductView implements Template {

    private Product item;

    @Override
    public void print() {
        System.out.println("상품 상세...");
        System.out.println(item);
    }

    @Override
    public void print(Model model) {

        Object data = model.getData();
        if (data != null) {
            item = (Product)data;
        }

        print();
    }
}
