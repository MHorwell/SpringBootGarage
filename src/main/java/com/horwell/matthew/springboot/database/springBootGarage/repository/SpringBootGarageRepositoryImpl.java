package com.horwell.matthew.springboot.database.springBootGarage.repository;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.horwell.matthew.springboot.database.springBootGarage.model.Vehicle;

@Repository
public class SpringBootGarageRepositoryImpl implements SpringBootGarageRepositoryCustom{
	
    @PersistenceContext
    EntityManager entityManager;
    
	@Override
	public List<Vehicle> findByColour(String colour) {
		
		Query query = entityManager.createNativeQuery("SELECT em.* FROM springbootgarage.vehicle as em " +
				"WHERE colour = ?", Vehicle.class);
		query.setParameter(1, colour);
		
		return query.getResultList();
	}
	
	


}
