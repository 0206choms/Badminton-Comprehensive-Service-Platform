package com.kiwi.market.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kiwi.shop.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "marketLike")
@Getter
@Setter
@ToString
@NoArgsConstructor // 디폴트 생성자
@AllArgsConstructor
public class MarketLike extends BaseEntity{

	@Id
	@Column(name = "marketLike_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; // 마켓 좋아요 아이디

	@Column(name = "marketLike_memId")
    private Long memId; // 좋아요 누른 멤버 아이디
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id")
    private Market marketId;
	
	// 마켓 좋아요 - 멤버 아이디 넣기
	public static MarketLike createML(Long id) {
		MarketLike ml = new MarketLike();
		ml.setMemId(id);
		
		return ml;
	}
}