//package com.kiwi.shop.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
//import org.springframework.data.repository.query.Param;
//
//import com.kiwi.shop.entity.Item;
//
//import java.util.List;
//
//public interface ItemRepository extends JpaRepository<Item,Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {
//
//    List<Item> findByItemNm(String itemNm);
//
//    @Query("select i from Item i where i.itemDetail like %:itemDetail2% order by i.price desc")
//    List<Item> findByItemDetail(@Param("itemDetail2") String itemDetail2);
//
//    @Query(value = "select * from Item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
//    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
//}
