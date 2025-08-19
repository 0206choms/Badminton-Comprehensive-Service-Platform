package com.kiwi.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

//    private final ItemService itemService;

//    기존 컨트롤러 주석 -> 페이징 처리할때 복사에서 넣기
//    @GetMapping(value = "/")
//    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
//        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
//        model.addAttribute("items", items);
//        model.addAttribute("itemSearchDto", itemSearchDto);
//        model.addAttribute("maxPage", 5);
//        return "/main/main_test2";
//    }

	// 메인 컨트롤러(동영상)
	@GetMapping(value = "/")
	public String test(Model model) {
		model.addAttribute("videoUrl", "/video/main.mp4");
		return "main";
	}

}
