//package com.kiwi.shop.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.kiwi.shop.dto.ItemFormDto;
//import com.kiwi.shop.dto.ItemImgDto;
//import com.kiwi.shop.dto.ItemSearchDto;
//import com.kiwi.shop.dto.MainItemDto;
//import com.kiwi.shop.entity.Item;
//import com.kiwi.shop.entity.ItemImg;
//import com.kiwi.shop.repository.ItemImgRepository;
//import com.kiwi.shop.repository.ItemRepository;
//
//import javax.persistence.EntityNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class ItemService {
//    private final ItemRepository itemRepository;
//
//    private final ItemImgService itemImgService;
//
//    private final ItemImgRepository itemImgRepository;
//
//    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
//        // 상품 등록
//        Item item = itemFormDto.createItem();       // 상품 등록 폼으로부터 입력 받은 데이터를 이용하여 item 객체를 생성
//        itemRepository.save(item);                  // 상품 데이터 저장
//
//        // 이미지 등록
//        for(int i = 0; i<itemImgFileList.size();i++){
//            ItemImg itemImg = new ItemImg();
//            itemImg.setItem(item);
//            if(i == 0){                             // 첫 번째 이미지일 때 대표 상품 이미지 값을 Y로 설정하고 나머지 이미지는 N으로 설정한다
//                itemImg.setRepimgYn("Y");
//            } else {
//                itemImg.setRepimgYn("N");
//            }
//            itemImgService.saveItemImg(itemImg,itemImgFileList.get(i));     // 상품 이미지 정보를 저장합니다.
//        }
//
//        return item.getId();
//    }
//
//    // 상품 데이터를 읽어오는 트랜젹슨을 읽기 전용으로 설정한다. 성능 향상 및 아래에서 영속성 컨텍스트에 저장되는 내용이 없기에 읽기 전용으로 설정
//    @Transactional(readOnly = true)
//    public ItemFormDto getItemDtl(Long itemId){
//        // 메소드 매개 변수로 적은 상품의 이미지를 조회합니다. 등록순으로 가지고 오기 위해서 상품 이미지 아이디 오름차순으로 가져온다
//        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
//        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
//        // 조회한 ItemImg 엔티티를 ItemImgDto 객체로 만들어서 리스트에 추가
//        for (ItemImg itemImg: itemImgList) {
//            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
//            itemImgDtoList.add(itemImgDto);
//        }
//        // 상품의 아이디를 통해 상품 엔티티를 조회한다.
//        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
//        ItemFormDto itemFormDto = ItemFormDto.of(item);
//        itemFormDto.setItemImgDtoList(itemImgDtoList);
//        return itemFormDto;
//    }
//
//    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
//
//        // 상품 수정
//        // 상품 등록으로부터 전달 받은 상품 아이디를 이용하여 변경할 상품 엔티 조회
//        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
//        // 상품 등록 화면으로 부터 전달 받은 ItemFormDto를 통해 상품 엔티티를 업데이트 한다.
//        item.updateItem(itemFormDto);
//
//        // 상품 이미지 아이디 리스트를 조회
//        List<Long> itemImgIds = itemFormDto.getItemImgIds();
//
//        // 이미지 등록
//        // 상품 이미지를 업데이트 하기 위해서 updateItemImg() 메소드에 상품 이미지 아이디와 상품 이미지 파일 정보를 파라미터로 전달한다.
//        for (int i=0;i<itemImgFileList.size();i++){
//            itemImgService.updateItemImg(itemImgIds.get(i),itemImgFileList.get(i));
//        }
//
//        return item.getId();
//    }
//
//    @Transactional(readOnly = true)
//    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
//        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
//        return itemRepository.getMainItemPage(itemSearchDto, pageable);
//    }
//}
