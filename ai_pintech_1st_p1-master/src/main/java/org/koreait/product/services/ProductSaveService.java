package org.koreait.product.services;

import org.koreait.product.entities.Product;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 상품 추가 및 저장 처리
 *
 */
public class ProductSaveService {
    /**
     * 사용자가 입력한 요청 데이터로 상품 정보(Product) 등록 및 수정 처리
     * 요청 데이터 중에서 등록번호가 있다면 수정, 없다면 추가로 판단
     *
     * @param item
     */
    public void save(Product item) {
        File file = new File("products.obj");
        Map<Long, Product> data = load();
        long seq = item.getSeq();
        if (seq < 1L) seq = System.currentTimeMillis();

        if (data.containsKey(seq)) { // 상품 정보 수정
            // 수정 시간
            item.setModDt(LocalDateTime.now());
        } else { // 상품 정보 등록
            item.setSeq(seq);
            // 등록 시간
            item.setRegDt(LocalDateTime.now());
        }

        // 상품이 있다면 수정 / 없었다면 등록
        data.put(seq, item);

        try (FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(data);

        } catch (IOException e) {}
    }

    /**
     * 상품 정보 목록 파일에서 로드
     *
     * @return
     */
    private Map<Long, Product> load() {
        File file = new File("products.obj");
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream oos = new ObjectInputStream(fis)) {
                Map<Long, Product> data = (Map<Long, Product>) oos.readObject();
                return data;
            } catch (Exception e) {}
        }

        return new HashMap<>();
    }
}
