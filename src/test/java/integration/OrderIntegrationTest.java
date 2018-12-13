package integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.horwell.matthew.springboot.database.springBootGarage.SpringBootGarageApplication;
import com.horwell.matthew.springboot.database.springBootGarage.model.Order;
import com.horwell.matthew.springboot.database.springBootGarage.model.Vehicle;
import com.horwell.matthew.springboot.database.springBootGarage.repository.OrderRepository;
import com.horwell.matthew.springboot.database.springBootGarage.repository.SpringBootGarageRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootGarageApplication.class})
@AutoConfigureMockMvc
public class OrderIntegrationTest {
		
		@Autowired
		private MockMvc mvc;
		
		@Autowired
		private OrderRepository testOrderRepository;
		
		@Autowired 
		private SpringBootGarageRepository testVehicleRepository;
		
		@Before
		public void emptyDB() {
			testOrderRepository.deleteAll();
		}
		
		@Test
		public void findAndRetrieveOrdersFromDatabase()
		throws Exception{
			Vehicle testVehicle = new Vehicle();
			testVehicleRepository.save(testVehicle);
			testOrderRepository.save(new Order("testTitle", "testDescription", testVehicle));
			mvc.perform(get("/api/vehicle/" + testVehicle.getId() + "/orders")
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content()
					.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.content.[0]title", is("testTitle")));
		}
		
		@Test
		public void addOrderToVehicleTest() 
				throws Exception {
			Vehicle testVehicle = new Vehicle();
			testVehicleRepository.save(testVehicle);
			mvc.perform(MockMvcRequestBuilders.post("/api/vehicle/" + testVehicle.getId() + "/orders")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"title\" : \"testTitle\","
							+ "\"description\" : \"testDescription\"}"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.title", is("testTitle")));
		}
		
		@Test
		public void changeOrderOnVehicle()
				throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			Vehicle testVehicle = new Vehicle();
			testVehicleRepository.save(testVehicle);
			
			Order testOrder = new Order("testTitle","testDescription", testVehicle);
			String jsonString = mapper.writeValueAsString(testOrder);
			mvc.perform(MockMvcRequestBuilders.put("/api/vehicle/" + testVehicle.getId() + "/orders")
					.contentType(MediaType.APPLICATION_JSON));			
		}
		
		

}
