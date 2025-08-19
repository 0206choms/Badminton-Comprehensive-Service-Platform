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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kiwi.member.entity.Member;
import com.kiwi.shop.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 댓글 ID

	@Column(length = 500)
	private String content; // 댓글 내용
	
	@JsonBackReference
	@ManyToOne // 한 글에 여러개의 댓글 가능
	@JoinColumn(name = "market_id")
	private Market market; // 마켓 게시글

	@ManyToOne // 한 유저가 여러개의 댓글 가능
	@JoinColumn(name = "member_id")
	private Member member; // 작성자 ID
	
	// market을 조회할 때 Comment를 조회하게 되고 Comment를 조회하면 Market, Member를 조회하게 됨
	// 여기서 또 Market 조회하고 또 Comment를 조회하게 되고.... (무한 반복)
	// 해결하려면 Market 조회하고 Comment를 조회하고 다시 Market를 조회 안하게 되면 됨
	// @JsonIgnoreProperties하면 해결
	@OrderBy("id desc") // 댓글 작성시 최근 순으로 볼 수 있도록 설정
    @JsonIgnoreProperties({"market"})
    @OneToMany(mappedBy = "market", fetch = FetchType.EAGER)
    private List<Comment> commentList;
	
	@Column(name = "comment_memId")
	private Long memId;  // 작성자 id
	
	@Column(name = "comment_memName")
	private String memName;  // 작성자 이름
	
	@Column(name = "comment_memImg")
	private String memImg;  // 작성자 프로필 사진
	
	public void save(Market market, String content) {
        this.market = market;
        this.content = content;
    }

}