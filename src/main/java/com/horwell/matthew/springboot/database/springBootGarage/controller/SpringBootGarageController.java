package com.horwell.matthew.springboot.database.springBootGarage.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horwell.matthew.springboot.database.springBootGarage.exception.ResourceNotFoundException;
import com.horwell.matthew.springboot.database.springBootGarage.model.Vehicle;
import com.horwell.matthew.springboot.database.springBootGarage.repository.SpringBootGarageRepository;

@RestController
@RequestMapping("/api")
public class SpringBootGarageController {
	
	@Autowired
	SpringBootGarageRepository repository;
	
	@PostMapping("/vehicle")
	public Vehicle createVehicle(@Valid @RequestBody Vehicle sDGM) {
		return repository.save(sDGM);
	}
	
	@GetMapping("/vehicle/{id}")
	public Vehicle getVehiclebyID(@PathVariable(value = "id")Long vehicleID) {
		return repository.findById(vehicleID)
				.orElseThrow(()-> new ResourceNotFoundException("SpringBootDatabase","id",vehicleID));
	}
	
    @GetMapping("/vehicle/colour/{colour}")
    public List<Vehicle> getFiltered(@PathVariable(value = "colour")String colour) {
        return repository.findByColour(colour);
    }
	
	@GetMapping("/vehicle")
	public List<Vehicle> getAllVehicles(){
		return repository.findAll();
	}
	
	@PutMapping("/vehicle/{id}")
	public Vehicle updateVehicle(@PathVariable(value = "id") Long vehicleID,
			@Valid @RequestBody Vehicle vehicleDetails) {
		Vehicle sDGM = repository.findById(vehicleID)
				.orElseThrow(()-> new ResourceNotFoundException("Vehicle","id",vehicleID));
		
		sDGM.setType(vehicleDetails.getType());
		sDGM.setManufacturer(vehicleDetails.getManufacturer());
		sDGM.setModel(vehicleDetails.getModel());
		sDGM.setColour(vehicleDetails.getColour());
		sDGM.setYearMade(vehicleDetails.getYearMade());
		
		
		Vehicle updateData = repository.save(sDGM);
		return updateData;
	}
	
	@PutMapping("/vehicle/{id}")
	public Vehicle updateVehicleModel(@PathVariable(value = "id") Long vehicleID, 
			@Valid @RequestBody Vehicle vehicleDetails) {
		Vehicle sDGM = repository.findById(vehicleID)
				.orElseThrow(()-> new ResourceNotFoundException("Vehicle","id",vehicleID));
		
		sDGM.setType(vehicleDetails.getType());
		sDGM.setManufacturer(vehicleDetails.getManufacturer());
		sDGM.setModel(vehicleDetails.getModel());
		sDGM.setColour(vehicleDetails.getColour());
		sDGM.setYearMade(vehicleDetails.getYearMade());
		
		
		Vehicle updateData = repository.save(sDGM);
		return updateData;
	}
	
	
	@DeleteMapping("/vehicle/{id}")
	public ResponseEntity<?> deleteVehicle(@PathVariable(value = "id")Long vehicleID){
		Vehicle sDGM = repository.findById(vehicleID)
				.orElseThrow(()-> new ResourceNotFoundException("Vehicle","id",vehicleID));
		repository.delete(sDGM);
		return ResponseEntity.ok().build();
	}


}
