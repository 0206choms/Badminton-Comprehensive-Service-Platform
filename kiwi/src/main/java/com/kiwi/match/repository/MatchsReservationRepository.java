package com.kiwi.match.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiwi.match.entity.MatchsReservation;

public interface MatchsReservationRepository extends JpaRepository<MatchsReservation, Long>{

	List<MatchsReservation> findAllByOrderByIdDesc();

}
