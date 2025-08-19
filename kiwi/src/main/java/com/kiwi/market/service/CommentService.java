package com.kiwi.market.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiwi.market.entity.Comment;
import com.kiwi.market.entity.Market;
import com.kiwi.market.repository.CommentRepository;
import com.kiwi.market.repository.MarketRepository;
import com.kiwi.member.entity.Member;
import com.kiwi.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final MarketRepository marketRepository;
	private final MemberRepository memberRepository;

	// 댓글 작성
	@Transactional
	public void commentSave(Long marketId, String content, Comment comment, String memberName, String memberImage, Long memberId) { // Member member
		Market market = marketRepository.findById(marketId)
				.orElseThrow(() -> new IllegalArgumentException("해당 marketId가 없습니다. id=" + marketId));
		
		Member member = memberRepository.findMemberById(memberId);
		
		comment.setMemId(memberId);
		comment.setMemName(memberName);
		comment.setMemImg(member.getImage());
		
		comment.save(market, content); // , member
		commentRepository.save(comment);
	}

	// 댓글 리스트
	public List<Comment> commentList() {
		List<Comment> list = commentRepository.findAllByOrderByIdDesc();
		return list;
	}

	// 댓글 삭제
	public void deleteComment(Long id) {
		commentRepository.deleteById(id);
	}

}
