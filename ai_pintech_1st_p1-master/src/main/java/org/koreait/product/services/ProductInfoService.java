package org.koreait.product.services;

import org.koreait.product.entities.Product;
import org.koreait.product.exceptions.ProductNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 상품 정보 조회
 *
 */
public class ProductInfoService {
    /**
     * 상품 등록번호로 상품 하나 조회
     *  - 상품이 없다면 ProductNotFoundException 발생
     *
     * @param seq
     * @return
     */
    public Product get(long seq) {
        List<Product> items = getList(false);
        Product item = items.stream().filter(i -> i.getSeq() == seq).findFirst().orElseThrow(ProductNotFoundException::new);

        return item;
    }

    /**
     * 전체 목록 가져오기
     *
     * @param isDesc : false - 기본 상품번호 오름차순으로 기본 정렬, true - 상품번호 기준 내림차순으로 정렬
     * @return
     */
    public List<Product> getList(boolean isDesc) {
        File file = new File("products.obj");
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream oos = new ObjectInputStream(fis)) {
                Map<Long, Product> data = (Map<Long, Product>)oos.readObject();
                if (data != null && !data.isEmpty()) {
                    List<Product> items = data.values().stream().sorted((i1, i2) -> isDesc ? Long.valueOf(i1.getSeq()).compareTo(i2.getSeq()) : Long.valueOf(i2.getSeq()).compareTo(i1.getSeq())).toList();
                    return items;
                }

            } catch (IOException | ClassNotFoundException e) {}
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * 기본 상품 목록 조회 (오름차순)
     *
     * @return
     */
    public List<Product> getList() {
        return getList(false);
    }
}
