//package com.kiwi.shop.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.kiwi.shop.dto.ItemFormDto;
//import com.kiwi.shop.dto.ItemSearchDto;
//import com.kiwi.shop.entity.Item;
//import com.kiwi.shop.service.ItemService;
//
//import javax.persistence.EntityNotFoundException;
//import javax.validation.Valid;
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequiredArgsConstructor
//public class ItemController {
//
//    private final ItemService itemService;
//
//    @GetMapping(value = "/admin/item/new")
//    public String itemForm(Model model){
//        model.addAttribute("itemFormDto", new ItemFormDto());
//        return "/item/itemForm";
//    }
//
//    @PostMapping(value = "/admin/item/new")
//    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
//                          @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList){
//
//        // 상품 등록 시 필수 값이 없다면 다시 상품 등록페이지로 이동
//        if(bindingResult.hasErrors()){
//            return "item/itemForm";
//        }
//
//        // 상품 등록시 첫번째 이미지가 없다면 에러 메시지와 함께 상품 등록 페이지로 전환합니다. 첫번째 이미지는 메인페이지에서 보여줄 이미지 이기에 필수 값으로 지정함
//        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
//            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
//            return "item/itemForm";
//        }
//
//        // 상품 저장 로직을 호출 // 상품을 저장할때는 상품 정보와 이미지 파일 같이 저장
//        try{
//            itemService.saveItem(itemFormDto, itemImgFileList);
//        } catch (Exception e){
//            model.addAttribute("errorMessage","상품 등록 중 에러가 발생하였습니다.");
//            return "item/itemForm";
//        }
//
//        // 상품이 정상적으로 등록되었다면 메인 페이지로 이동
//        return "redirect:/";
//    }
//
//    @GetMapping(value = "/admin/item/{itemId}")
//    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){
//        try {
//            // 조회한 상품 데이터를 모델에 담아서 뷰로 전달
//            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
//            model.addAttribute("itemFormDto", itemFormDto);
//        }
//        // 상품 엔티티가 존재하지 않을경우 에러메시지를 담아서 상품 등록 페이지로 이동한다.
//        catch (EntityNotFoundException e){
//            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
//            model.addAttribute("itemFormDto", new ItemFormDto());
//            return "item/itemForm";
//        }
//        return "item/itemForm";
//    }
//
//    @PostMapping(value = "/admin/item/{itemId}")
//    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
//                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,Model model){
//        if(bindingResult.hasErrors()){
//            return "item/itemForm";
//        }
//        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
//            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
//            return "item/itemForm";
//        }
//
//        try{
//            itemService.updateItem(itemFormDto, itemImgFileList);
//        }catch (Exception e){
//            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
//            return "item/itemForm";
//        }
//
//        return "redirect:/";
//    }
//
//    // value 페이지 진입시 URL에 페이지 번호가 있는경우, 없는 경우 매핑
//    @GetMapping(value = {"/admin/items","/admin/items/{page}"})
//    public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page")Optional<Integer> page, Model model){
//        // PageRequest.of로 pageable객체 생성하는데 page.isPresent() 즉, 페이지 번호가 있으면 해당 페이지 번호 세팅하고 없으면 0으로 세팅
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
//        // 조회 조건과 페이징 정보를 파리미터로 넘겨서 Page객체로 반환
//        Page<Item> items = itemService.getAdminItemPage(itemSearchDto,pageable);
//        // 조회한 상품 데이터 및 페이징 정보를 뷰에 전달
//        model.addAttribute("items", items);
//        // 페이지 전환 시 기존 검색 조건을 유지한 채 이동할 수 있도록 뷰에 다시 전달
//        model.addAttribute("itemSearchDto", itemSearchDto);
//        // 메뉴 하단에 보여질 페이지 번호의 최대 개수 5로 설정했으면 1,2,3,4,5까지 출력
//        model.addAttribute("maxPage", 5);
//        return "item/itemMng";
//
//    }
//
//    @GetMapping(value = "/item/{itemId}")
//    public String itemDtl(Model model, @PathVariable("itemId") Long itemId){
//        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
//        model.addAttribute("item", itemFormDto);
//        return "item/itemDtl";
//    }
//
//
//}
