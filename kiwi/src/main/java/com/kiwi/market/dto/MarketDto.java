package com.kiwi.market.dto;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.kiwi.court.dto.CourtDto;
import com.kiwi.court.dto.ReservationDto;
import com.kiwi.court.entity.Court;
import com.kiwi.market.constant.ItemSellStatus;
import com.kiwi.market.entity.Market;
import com.kiwi.member.constant.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarketDto {

	private Long id; // 게시글 코드

	@NotBlank(message = "제목을 반드시 입력해주세요.")
	private String title; // 게시글 제목

	@NotBlank(message = "내용을 반드시 입력해주세요.")
	private String detail; // 게시글 내용

	@NotBlank(message = "가격을 반드시 입력해주세요.")
	private String price; // 가격

	private ItemSellStatus status; // 판매 여부

	private String filename;

	private String filepath;

	private String oriImgName;
	
	// 주소
    private Address address;

	@Column(name = "market_memId")
	private Long memId; // 작성자 ID

	@Column(name = "market_memName")
	private String memName; // 작성자 이름

	@Column(name = "market_memImg")
	private String memImg; // 작성자 프로필 사진

	private Long buy_memId; // 구매자 id

	public void marketBuy(MarketDto dto, Long buyId) {
		dto.setStatus(ItemSellStatus.구매대기);
		dto.setBuy_memId(buyId);
	}

	private static ModelMapper modelMapper = new ModelMapper();
	
	// modelMapper를 이용하여 엔티티 객체와 DTO객체 간의 데이터를 복사하여 복사한 객체를 반환해주는 메소드
	public Market createItem() {
		return modelMapper.map(this, Market.class);
	}

	// modelMapper를 이용하여 엔티티 객체와 DTO객체 간의 데이터를 복사하여 복사한 객체를 반환해주는 메소드
	public static MarketDto of(Market market) {
		return modelMapper.map(market, MarketDto.class);
	}

}