package com.horwell.matthew.springboot.database.springBootGarage.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horwell.matthew.springboot.database.springBootGarage.repository.*;
import com.horwell.matthew.springboot.database.springBootGarage.exception.ResourceNotFoundException;
import com.horwell.matthew.springboot.database.springBootGarage.model.*;

@RestController
@RequestMapping("/api")
public class VehicleController {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private SpringBootGarageRepository garageRepository;
	
	@GetMapping("/vehicle/{vehicleId}/orders")
	public Page<Order> getAllOrdersbyVehicleId(@PathVariable (value = "vehicleId") Long vehicleId, Pageable pageable){
		return orderRepository.findByVehicleId(vehicleId, pageable);
	}
	
	@PostMapping("/vehicle/{vehicleId}/orders")
	public Order createComment(@PathVariable (value = "vehicleId") Long vehicleId,
			@Valid @RequestBody Order order) {
		return garageRepository.findById(vehicleId).map(vehicle -> {
			order.setVehicle(vehicle);
			return orderRepository.save(order);
		}).orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", order));
	}
	
	@PutMapping("/vehicle/{id}/orders/{orderId}")
	public Order updateOrder(@PathVariable (value = "id") Long vehicleId,
			@PathVariable (value = "orderId") Long orderId,
			@Valid @RequestBody Order orderRequest) {
		if(!garageRepository.existsById(vehicleId)) {
			throw new ResourceNotFoundException("Vehicle", "id", orderRequest);
		}
		
		return orderRepository.findById(orderId).map(order -> {
			order.setTitle(orderRequest.getTitle());
			return orderRepository.save(order);
		}).orElseThrow(() -> new ResourceNotFoundException("OrderId", "id", orderRequest));
		
	}
	
	@DeleteMapping("/vehicle/{id}/orders/{orderId}")
	public ResponseEntity<?> deleteComment(@PathVariable (value = "id") Long vehicleId,
			@PathVariable (value = "orderId") Long orderId) {
		if(!garageRepository.existsById(vehicleId)) {
			throw new ResourceNotFoundException("Vehicle", "id", vehicleId);
		}
		
		return orderRepository.findById(orderId).map(order -> {
			orderRepository.delete(order);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Order Id ", orderId.toString(), null));
	}
	
	
 	

}
