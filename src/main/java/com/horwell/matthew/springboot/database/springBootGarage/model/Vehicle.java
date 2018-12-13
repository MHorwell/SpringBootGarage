package com.horwell.matthew.springboot.database.springBootGarage.model;
	
	import java.io.Serializable;
	import java.util.Date;

	import javax.persistence.*;
	import javax.persistence.TemporalType;
	import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
	import org.springframework.data.annotation.LastModifiedDate;
	import org.springframework.data.jpa.domain.support.AuditingEntityListener;

	import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.horwell.matthew.springboot.database.springBootGarage.repository.SpringBootGarageRepository;

	@Entity
	@Table(name = "vehicle")
	@EntityListeners(AuditingEntityListener.class)
	@JsonIgnoreProperties(value = {"creationDate","lastModified"}, allowGetters = true)
	@DynamicInsert(true)
	@DynamicUpdate(true)

	public class Vehicle implements Serializable{
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long Id;
		
		private String type;
		
		private String manufacturer;
		
		private String model;
		
		private String colour;
		
		private int yearMade;
		
		@Column(nullable = false, updatable = false)
		@Temporal(TemporalType.TIMESTAMP)
		@CreatedDate
		private Date creationDate;
		
		@Column(nullable = false)
		@Temporal(TemporalType.TIMESTAMP)
		@LastModifiedDate
		private Date lastModified;
		
		public Vehicle() {
			
		}
		
		public Vehicle(String type, String manufacturer, String model, String colour, int yearMade) {
			this.type = type;
			this.manufacturer = manufacturer;
			this.model = model;
			this.colour = colour;
			this.yearMade = yearMade;
		}

		public Long getId() {
			return Id;
		}

		public void setId(Long id) {
			Id = id;
		}

		public String getManufacturer() {
			return manufacturer;
		}

		public void setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getColour() {
			return colour;
		}

		public void setColour(String colour) {
			this.colour = colour;
		}
		
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getYearMade() {
			return yearMade;
		}

		public void setYearMade(int yearMade) {
			this.yearMade = yearMade;
		}

		public Date getCreationDate() {
			return creationDate;
		}

		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}

		public Date getLastModified() {
			return lastModified;
		}

		public void setLastModified(Date lastModified) {
			this.lastModified = lastModified;
		}
		

}
