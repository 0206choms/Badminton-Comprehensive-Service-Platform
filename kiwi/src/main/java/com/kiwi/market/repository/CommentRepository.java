package com.kiwi.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiwi.market.entity.Comment;
import com.kiwi.market.entity.Market;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByOrderByIdDesc();
	
	List<Comment> findCommentByMemId(Long id);
	
}