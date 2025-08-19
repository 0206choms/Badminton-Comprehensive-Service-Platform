//package com.kiwi.shop.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import com.kiwi.shop.dto.CartDetailDto;
//import com.kiwi.shop.dto.CartItemDto;
//import com.kiwi.shop.entity.CartItem;
//
//import java.util.List;
//
//public interface CartItemRepository extends JpaRepository<CartItem,Long> {
//    // 카트 아이디와 상품 아이디를 이용해서 상품이 장바구니에 들어있는지 조회한다.
//    CartItem findByCartIdAndItemId(Long cartId, Long itemId);
//
//    // CartDetailDto의 생성자를 이용하여 DTO를 반환할때는 아래와 같이 풀 경로를 다 적어야함
//    @Query("select new com.kiwi.shop.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
//            "from CartItem ci, ItemImg im "+
//            "join ci.item i " +
//            "where ci.cart.id = :cartId " +
//            "and im.item.id = ci.item.id " +
//            "and im.repimgYn = 'Y' " +
//            "order by ci.regTime desc"
//    )
//    List<CartDetailDto> findCartDetailDtoList(Long cartId);
//}
