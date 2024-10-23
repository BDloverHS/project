package org.koreait.product.controllers;

import org.koreait.global.BeanContainer;
import org.koreait.global.Controller;
import org.koreait.global.Model;
import org.koreait.global.libs.Utils;
import org.koreait.product.entities.Product;
import org.koreait.product.exceptions.ProductNotFoundException;
import org.koreait.product.services.ProductInfoService;
import org.koreait.product.templates.ProductView;

/**
 * 상품 상세 보기
 *
 */
public class ProductViewController extends Controller {

    @Override
    public void view() {
        Object data = getData();
        if (data == null) {
            throw new ProductNotFoundException();
        }

        long seq = (long)data; // 상품번호
        ProductInfoService service = BeanContainer.getBean(ProductInfoService.class);

        Product item = service.get(seq);

        // 상품 정보와 함께 출력
        Utils.loadTpl(ProductView.class, new Model(item));
    }
}
