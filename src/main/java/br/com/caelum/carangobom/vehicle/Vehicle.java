package br.com.caelum.carangobom.vehicle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.caelum.carangobom.brand.Brand;

import java.math.BigDecimal;


@Entity
public class Vehicle {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Brand brand;
	private Integer year;
	private String model;
	private BigDecimal price;
	
	public Vehicle() {
		
	}

	public Vehicle(Long id, Brand brand, Integer year, String model, BigDecimal price) {
		this.id = id;
		this.brand = brand;
		this.year = year;
		this.model = model;
		this.price = price;
	}
	
	public Vehicle(Brand brand, Integer year, String model, BigDecimal price) {
		this.brand = brand;
		this.year = year;
		this.model = model;
		this.price = price;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public BigDecimal getPrice() { return price; }
	public void setPrice(BigDecimal price) { this.price = price; }
}
