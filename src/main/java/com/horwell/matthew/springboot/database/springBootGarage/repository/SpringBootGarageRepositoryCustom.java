package com.horwell.matthew.springboot.database.springBootGarage.repository;

import java.util.List;
import com.horwell.matthew.springboot.database.springBootGarage.model.Vehicle;

public interface SpringBootGarageRepositoryCustom {
	List<Vehicle> findByColour(String colour);
}
