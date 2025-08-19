//package com.kiwi.shop.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.thymeleaf.util.StringUtils;
//
//import com.kiwi.member.entity.Member;
//import com.kiwi.member.repository.MemberRepository;
//import com.kiwi.shop.dto.CartDetailDto;
//import com.kiwi.shop.dto.CartItemDto;
//import com.kiwi.shop.dto.CartOrderDto;
//import com.kiwi.shop.dto.OrderDto;
//import com.kiwi.shop.entity.Cart;
//import com.kiwi.shop.entity.CartItem;
//import com.kiwi.shop.entity.Item;
//import com.kiwi.shop.repository.CartItemRepository;
//import com.kiwi.shop.repository.CartRepository;
//import com.kiwi.shop.repository.ItemRepository;
//
//import javax.persistence.EntityNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class CartService {
//
//    private final ItemRepository itemRepository;
//    private final MemberRepository memberRepository;
//    private final CartRepository cartRepository;
//    private final CartItemRepository cartItemRepository;
//    private final OrderService orderService;
//
//    // 장바구니에 상품을 담는 로직
//    public Long addCart(CartItemDto cartItemDto, String email){
//
//        // 장바구니에 담을 상품 엔티티 조회
//        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
//        // 현재 로그인한 회원 조회
//        Member member = memberRepository.findByEmail(email);
//        // 현재 로그인한 회원의 장바구니 조회
//        Cart cart = cartRepository.findByMemberId(member.getId());
//        // cart가 null값이면 즉, 장바구니가 없을 경우 현재 로그인한 회원의 장바구니를 생성함
//        if(cart == null){
//            cart = Cart.createCart(member);
//            cartRepository.save(cart);
//        }
//        // 현재 장바구니 상품이 이미 들어가 있는지 조회함
//        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(),item.getId());
//
//        // 장바구니에 이미 있던 상품일 경우 기존 수량에 현재 장바구니에 담을 수량만큼 더한다
//        if(savedCartItem != null){
//            savedCartItem.addCount(cartItemDto.getCount());
//            return savedCartItem.getId();
//        } else {
//          // 장바구니 엔티티, 상품 엔티티, 장바구니에 담을 수량을 이용하여 장바구니 상품 엔티티 생성
//          CartItem cartItem = CartItem.createCartItem(cart,item, cartItemDto.getCount());
//          // 장바구니에 담을 상품 저장
//          cartItemRepository.save(cartItem);
//          return cartItem.getId();
//        }
//    }
//
//    @Transactional(readOnly = true)
//    public List<CartDetailDto> getCartList(String email){
//
//        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();
//
//        Member member = memberRepository.findByEmail(email);
//        // 현재 로그인한 회원의 아이디로 장바구니 조회
//        Cart cart = cartRepository.findByMemberId(member.getId());
//        // 장바구니가 null 즉, 상품을 한번도 안 담았을경우 장바구니 엔티티가 없으므로 빈 리스트 반환
//        if(cart == null){
//            return cartDetailDtoList;
//        }
//
//        // 장바구니에 담겨있는 상품 정보 조회
//        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());
//
//        return cartDetailDtoList;
//    }
//
//
//    @Transactional(readOnly = true)
//    public boolean validateCartItem(Long cartItemId, String email){
//        // 현재 로그인한 회원 조회
//        Member curMember = memberRepository.findByEmail(email);
//        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
//        // 장바구니 상품을 저장할 회원 조회
//        Member savedMember = cartItem.getCart().getMember();
//
//        // 현재 로그인한 회원과 장바구니 상품을 저장한 회원이 다를 경우 false, 같을 경우 true
//        if(!StringUtils.equals(curMember.getEmail(),savedMember.getEmail())){
//            return false;
//        }
//
//        return true;
//    }
//
//    // 장바구니 상품의 수량을 업데이트 하는 메소드
//    public void updateCartItemCount(Long cartItemId, int count){
//        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
//
//        cartItem.updateCount(count);
//    }
//
//    // 장바구니 상품 삭제
//    public void deleteCartItem(Long cartItemId){
//        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
//        cartItemRepository.delete(cartItem);
//    }
//
//    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email){
//        List<OrderDto> orderDtoList = new ArrayList<>();
//
//        // 장바구니 페이지에서 전달 받은 주문 상품 번호롤 이용하여 주문 로직으로 전달할 orderDto 객체 생성
//        for (CartOrderDto cartOrderDto: cartOrderDtoList) {
//            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);
//
//            OrderDto orderDto = new OrderDto();
//            orderDto.setItemId(cartItem.getItem().getId());
//            orderDto.setCount(cartItem.getCount());
//            orderDtoList.add(orderDto);
//        }
//
//        // 장바구니에 담은 상품을 주문하도록 주문 로직 호출
//        Long orderId = orderService.orders(orderDtoList,email);
//
//        // 장바구니에 담은 상품이 주문에 들어갔으므로 기존 장바구니 상품 삭제
//        for (CartOrderDto cartOrderDto: cartOrderDtoList) {
//            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);
//            cartItemRepository.delete(cartItem);
//        }
//
//        return orderId;
//    }
//}
