package br.com.caelum.carangobom.vehicle;

import br.com.caelum.carangobom.brand.Brand;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Calendar;

public class VehicleForm {

	Calendar calendar = Calendar.getInstance();
	final Integer maxYear = calendar.YEAR;

	public VehicleForm(Long brandId, Integer year, String model, BigDecimal price) {
		this.brandId = brandId;
		this.year = year;
		this.model = model;
		this.price = price;
	}

	@NotNull()
	private Long brandId;

	@NotNull()
	@Min(value = 1880, message = "The vehicle year must be equal or greater than 1880.")
	private Integer year;

	@NotNull
	@Length(min = 1, max = 100)
	private String model;

	@NotNull
	@Min(0)
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
