package br.com.caelum.carangobom.vehicle;

import br.com.caelum.carangobom.brand.Brand;

import java.math.BigDecimal;

public class VehicleForm {

	public VehicleForm(Long brandId, Integer year, String model, BigDecimal price) {
		this.brandId = brandId;
		this.year = year;
		this.model = model;
		this.price = price;
	}

	private Long brandId;
	private Integer year;
	private String model;
	private BigDecimal price;

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Vehicle toVehicle(Brand brand) {
		return new Vehicle(brand, year, model, price);
	}
}
