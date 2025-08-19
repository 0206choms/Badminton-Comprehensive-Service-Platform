package com.kiwi.pay.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.kiwi.member.constant.Address;
import com.kiwi.member.constant.Bank;
import com.kiwi.member.constant.Gender;
import com.kiwi.member.constant.Role;
import com.kiwi.member.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cash")
@Getter @Setter
@NoArgsConstructor
@ToString
public class Cash {
	@Id
    @Column(name="cash_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	// 캐시 충전 금액
	private int amount;
	
	// 캐시 충전 시간 
	private LocalDateTime time;
	
	// 고유 ID
	private String imp_uid;
	
	// 상점 거래 ID
	private String merchant_uid;
	
	// 카드 승인 번호 
	private String apply_num;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	public Cash(int amount, String merchant_uid, String imp_uid, String apply_num) {
		this.amount = amount;
		this.merchant_uid = merchant_uid;
		this.imp_uid = imp_uid;
		this.apply_num = apply_num;
		this.time = LocalDateTime.now();
	}
}
