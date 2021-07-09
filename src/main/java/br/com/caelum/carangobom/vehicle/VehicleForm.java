package br.com.caelum.carangobom.vehicle;

import java.util.Optional;

import br.com.caelum.carangobom.brand.Brand;
import br.com.caelum.carangobom.brand.BrandRepository;
import br.com.caelum.carangobom.brand.BadRequestException;


public class VehicleForm {
	
	private Long brandId;
	private Integer year;
	private String model;
	
	public VehicleForm() {
		
	}
	
	public VehicleForm(String model, Integer year) {
		this.year = year;
		this.model = model;
	}
	
	public VehicleForm(Long brandId, String model, Integer year) {
		this.brandId = brandId;
		this.year = year;
		this.model = model;
	}
	
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
	
	public Vehicle toVehicle(BrandRepository brandRepository) {
		Optional<Brand> optional = brandRepository.findById(brandId);
		if (optional.isPresent()) {
			return new Vehicle(optional.get(), year, model);
		}
		throw new BadRequestException("Brand not found!");
	}

}
