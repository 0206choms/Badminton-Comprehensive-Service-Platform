package com.kiwi.court.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiwi.court.entity.Reservation;
import com.kiwi.match.entity.Matchs;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

	List<Reservation> findAllByOrderByIdDesc();

}