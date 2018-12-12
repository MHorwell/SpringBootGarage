package repository;

import static org.junit.Assert.assertTrue;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.horwell.matthew.springboot.database.springBootGarage.SpringBootGarageApplication;
import com.horwell.matthew.springboot.database.springBootGarage.model.Vehicle;
import com.horwell.matthew.springboot.database.springBootGarage.repository.SpringBootGarageRepository;

@RunWith(SpringRunner.class)

@ContextConfiguration(classes=SpringBootGarageApplication.class)
@DataJpaTest
public class RepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private SpringBootGarageRepository testRepository;
	
	@Test
	public void findByID() {
		Vehicle model = new Vehicle("testType", "testManufacturer", "testModel", "testColour", 0);
		entityManager.persist(model);
		entityManager.flush();
		assertTrue(testRepository.findById(model.getId()).isPresent());
	}
	
	@Test
	public void findByColour() {
		Vehicle model = new Vehicle("testType", "testManufacturer", "testModel", "testColour", 0);
		entityManager.persist(model);
		entityManager.flush();
		assertTrue(testRepository.findByColour(model.getColour()).stream().findFirst().isPresent());
	}
	
	

}
