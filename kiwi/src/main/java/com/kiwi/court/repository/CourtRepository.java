package com.kiwi.court.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kiwi.court.entity.Court;

public interface CourtRepository extends JpaRepository<Court, Long> {
	List<Court> findAll();
	
//  @Query(value = "select i.lat from court i ", nativeQuery = true)
//  List<Court> getLat();

}
