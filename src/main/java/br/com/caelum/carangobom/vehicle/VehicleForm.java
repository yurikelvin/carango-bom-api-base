package br.com.caelum.carangobom.vehicle;

import br.com.caelum.carangobom.brand.Brand;

public class VehicleForm {

	public VehicleForm(Long brandId, Integer year, String model) {
		this.brandId = brandId;
		this.year = year;
		this.model = model;
	}

	private Long brandId;
	private Integer year;
	private String model;

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

	public Vehicle toVehicle(Brand brand) {
		return new Vehicle(brand, year, model);
	}
}
