//package com.kiwi.shop.entity;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import javax.persistence.*;
//
//import com.kiwi.constant.ItemSellStatus;
//import com.kiwi.exception.OutOfStockException;
//import com.kiwi.shop.dto.ItemFormDto;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name="item")
//@Getter
//@Setter
//@ToString
//public class Item extends BaseEntity{
//    @Id
//    @Column(name="item_id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;       //상품 코드
//
//    @Column(nullable = false,length = 50)
//    private String itemNm;  //상품명
//
//    @Column(name="price",nullable = false)
//    private int price;     //가격
//
//    @Column(nullable = false)
//    private int stockNumber;    //재고수량
//
//    @Lob
//    @Column(nullable = false)
//    private String itemDetail;  //상품 상세 설명
//
//    @Enumerated(EnumType.STRING)   //@Enumrated는 enum타입 매핑시 사용함!
//    private ItemSellStatus itemSellStatus;  //상품 판매 상태
//
//    // Auditing 이용해서 BaseEntity로 부터 regTime, updateTime, createBy, modifyBy 상속 받고 알아서 지정되므로 기존에 필드는 주석처리
////    private LocalDateTime regTime;
////    private LocalDateTime updateTime;
//
//    public void updateItem(ItemFormDto itemFormDto){
//        this.itemNm = itemFormDto.getItemNm();
//        this.price = itemFormDto.getPrice();
//        this.stockNumber = itemFormDto.getStockNumber();
//        this.itemDetail = itemFormDto.getItemDetail();
//        this.itemSellStatus = itemFormDto.getItemSellStatus();
//    }
//
//    // 주문하면 상품의 재고를 감소시키는 로직
//    public void removeStock(int stockNumber){
//        // 상품의 재고 수량에서 주문수량 빼기
//        int restStock = this.stockNumber - stockNumber;
//        // 재고가 주문수량보다 적을경우 예외 처리
//        if(restStock < 0){
//            throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량: " + this.stockNumber + ")");
//        }
//        // 주문후 남은 재고 수량을 상품의 현재 재고값으로 할당
//        this.stockNumber = restStock;
//    }
//
//    // 상품의 재고를 증가시키는 메소드
//    public void addStock(int stockNumber){
//        this.stockNumber += stockNumber;
//    }
//}
