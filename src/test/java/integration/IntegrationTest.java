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
		ObjectMapper vehicleToJson = new ObjectMapper();
		Vehicle testVehicle = new Vehicle("testType", "testManufacturer", "testModel", "testColour", 0);
		testrepository.save(testVehicle);
		
		Vehicle changedVehicle = new Vehicle("Car", "Suzuki", "Wagon R", "Purple", 2004);
		String jsonString = vehicleToJson.writeValueAsString(changedVehicle);
		
		mvc.perform(MockMvcRequestBuilders.put("/api/vehicle/" + testVehicle.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.type", is("Car")))
		.andExpect(jsonPath("$.manufacturer", is("Suzuki")))
		.andExpect(jsonPath("$.model", is("Wagon R")))
		.andExpect(jsonPath("$.colour", is("Purple")))
		.andExpect(jsonPath("$.yearMade", is(2004)));
	}
	
	@Test
	public void addToVehicle() throws Exception {
		ObjectMapper vehicleToJson = new ObjectMapper();
		Vehicle testVehicle = new Vehicle("testType", "testManufacturer", "testModel", "testColour", 0);
		testrepository.save(testVehicle);
		
		Vehicle emptyVehicle = new Vehicle();
		String jsonString = vehicleToJson.writeValueAsString(emptyVehicle);
		
		mvc.perform(MockMvcRequestBuilders.put("/api/vehicle/" + testVehicle.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.type", is("testType")))
		.andExpect(jsonPath("$.manufacturer", is("testManufacturer")))
		.andExpect(jsonPath("$.model", is("testModel")))
		.andExpect(jsonPath("$.colour", is("testColour")))
		.andExpect(jsonPath("$.yearMade", is(0)));
		
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
