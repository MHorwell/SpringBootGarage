package com.horwell.matthew.springboot.database.springBootGarage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.horwell.matthew.springboot.database.springBootGarage.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	Page<Order> findByVehicleId(Long vehicleID, Pageable pageable);
	
}
	
	


