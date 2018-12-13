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
import com.horwell.matthew.springboot.database.springBootGarage.model.Order;
import com.horwell.matthew.springboot.database.springBootGarage.model.Vehicle;
import com.horwell.matthew.springboot.database.springBootGarage.repository.OrderRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SpringBootGarageApplication.class })
@DataJpaTest
public class OrderRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private OrderRepository testOrderRepository;

	@Test
	public void findByOrderID() {
		Vehicle testVehicle = new Vehicle("testType", "testManufacturer", "testModel", "testColour", 0);
		Order testOrder = new Order("testTitle", "testDescription", testVehicle);
		entityManager.persist(testVehicle);
		entityManager.persist(testOrder);
		entityManager.flush();
		assertTrue(testOrderRepository.findById(testOrder.getId()).isPresent());
	}


}
