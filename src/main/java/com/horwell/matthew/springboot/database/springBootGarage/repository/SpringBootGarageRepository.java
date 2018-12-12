package com.horwell.matthew.springboot.database.springBootGarage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.horwell.matthew.springboot.database.springBootGarage.model.Vehicle;

@Repository
public interface SpringBootGarageRepository extends JpaRepository<Vehicle,Long> {
	
	List<Vehicle> findByColour(String colour);

}
