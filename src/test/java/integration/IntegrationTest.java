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

import com.horwell.matthew.springboot.database.springBootGarage.SpringBootGarageApplication;
import com.horwell.matthew.springboot.database.springBootGarage.model.Vehicle;
import com.horwell.matthew.springboot.database.springBootGarage.repository.SpringBootGarageRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootGarageApplication.class})
@AutoConfigureMockMvc
public class IntegrationTest {
	
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private SpringBootGarageRepository testrepository;
	
	@Before
	public void emptyDB() {
		testrepository.deleteAll();
	}
	
	@Test
	public void findAndRetrieveVehicleFromDatabase()
	throws Exception{
		testrepository.save(new Vehicle("testType", "testManufacturer", "testModel", "testColour", 0));
		mvc.perform(get("/api/vehicle")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].type", is("testType")));		
	}
	
	@Test
	public void addVehicleToDatabaseTest() 
			throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/api/vehicle")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"type\" : \"Car\","
						+ "\"manufacturer\" : \"Vauxhall\","
						+ "\"model\" : \"Agila\","
						+ "\"colour\" : \"Purple\","
						+ "\"yearMade\" : 2004}"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.type", is("Car")));
	}
	
	@Test
	public void putToVehicle()
			throws Exception {
		Vehicle testVehicle = new Vehicle("testType", "testManufacturer", "testModel", "testColour", 0);
		testrepository.save(testVehicle);
		mvc.perform(MockMvcRequestBuilders.put("/api/vehicle/" + testVehicle.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"type\" : \"Car\","
						+ "\"manufacturer\" : \"Suzuki\","
						+ "\"model\" : \"Wagon R\","
						+ "\"colour\" : \"Purple\","
						+ "\"yearMade\" : 2004}"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.model", is("Wagon R")));
	}
	
	@Test
	public void deleteVehicle()
	throws Exception {
		Vehicle testVehicle = new Vehicle("testType", "testManufacturer", "testModel", "testColour", 0);
		testrepository.save(testVehicle);
		mvc.perform(MockMvcRequestBuilders.delete("/api/vehicle/" + testVehicle.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders.delete("/api/vehicle/" + testVehicle.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
				
		
	}
	

}
