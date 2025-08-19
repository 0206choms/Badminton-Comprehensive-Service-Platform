//package com.kiwi.shop.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import org.thymeleaf.util.StringUtils;
//
//import com.kiwi.shop.entity.ItemImg;
//import com.kiwi.shop.repository.ItemImgRepository;
//
//import javax.persistence.EntityNotFoundException;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class ItemImgService {
//
//    @Value("${itemImgLocation}")
//    private String itemImgLocation;
//
//    private final ItemImgRepository itemImgRepository;
//
//    private final FileService fileService;
//
//    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
//        String oriImgName = itemImgFile.getOriginalFilename();
//        String imgName = "";
//        String imgUrl = "";
//
//        //파일 업로드
//        if(!StringUtils.isEmpty(oriImgName)){
//            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
//            imgUrl = "/images/item/" + imgName;
//        }
//
//        // 상품 이미지 정보 저장
//        itemImg.updateItemImg(oriImgName,imgName,imgUrl);
//        itemImgRepository.save(itemImg);
//    }
//
//    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
//        // 상품 이미지를 수정한 경우 상품 이미지를 업데이트 한다.
//        if(!itemImgFile.isEmpty()) {
//            // 상품 이미지 아이디를 조회해서 기존에 저장했던 상품 이미지 엔티티를 조회
//            ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);
//            // 기존에 등록했던 이미지 파일이 있을 경우 이미지 삭제한다.
//            if(!StringUtils.isEmpty(savedItemImg.getImgName())){
//                fileService.deleteFile(itemImgLocation+"/"+savedItemImg.getImgName());
//            }
//            String oriImgName = itemImgFile.getOriginalFilename();
//            // 업데이트 한 상품 이미지 파일을 업로드
//            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
//            String imgUrl = "/images/item/" + imgName;
//            // 변경된 상품 이미지 정보를 셋팅한다. 여기서는 중요한점이 상품 등록때 처럼 .save()메소드를 사용하는게 아니라 savedItemImg 엔티티는 영속 상태이므로
//            // 데이터를 변경하는 것 만으로 트랜잭션이 update쿼리를 실행 해줌 즉, 여기서 중요한 점은 엔티티가 영속 상태여야 하는것이다.
//            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
//        }
//    }
//}
