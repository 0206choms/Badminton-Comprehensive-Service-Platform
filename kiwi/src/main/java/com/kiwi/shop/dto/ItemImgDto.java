//package com.kiwi.shop.dto;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.modelmapper.ModelMapper;
//
//import com.kiwi.shop.entity.ItemImg;
//
//@Getter @Setter
//public class ItemImgDto {
//
//    private Long id;
//
//    private String imgName;
//
//    private String oriImgName;
//
//    private String imgUrl;
//
//    private String repImgYn;
//
//    // 멤버 변수로 ModelMapper 객체를 추가한다.
//    private static ModelMapper modelMapper = new ModelMapper();
//
//    // itemImg 객체를 파라미터로 받아서 itemImg객체와 자료형과 멤버변수명이 같을때 itemImgDto로 값을 복사해서 변환한다.
//    // static 메소드로 선언해서 itemImgDto객체를 생성하지 않아도 사용할 수 있다.
//    public static ItemImgDto of(ItemImg itemImg){
//        return modelMapper.map(itemImg, ItemImgDto.class);
//    }
//
//}
