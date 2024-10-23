// entities : 데이터 한 개 한 개를 의미
package org.koreait.product.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 상품 정보를 담는 데이터 클래스 정의
 *
 */


public class Product implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private long seq;  // 상품 등록번호
    private String name; // 상품이름
    private int price; // 판매가
    private int stock; // 재고

    private LocalDateTime regDt; // 상품등록일시
    private LocalDateTime modDt; // 상품수정일시

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDateTime getModDt() {
        return modDt;
    }

    public void setModDt(LocalDateTime modDt) {
        this.modDt = modDt;
    }

    public LocalDateTime getRegDt() {
        return regDt;
    }

    public void setRegDt(LocalDateTime regDt) {
        this.regDt = regDt;
    }

    @Override
    public String toString() {
        return "Product{" +
               "seq=" + seq +
               ", name='" + name + '\'' +
               ", price=" + price +
               ", stock=" + stock +
               ", regDt=" + regDt +
               ", modDt=" + modDt +
               '}';
    }
}
