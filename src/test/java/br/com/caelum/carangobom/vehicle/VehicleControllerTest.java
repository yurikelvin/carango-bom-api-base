package br.com.caelum.carangobom.vehicle;

import java.net.URI;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.internal.constraintvalidators.hv.ModCheckBase;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.caelum.carangobom.brand.Brand;
import br.com.caelum.carangobom.brand.BrandRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VehicleControllerTest {

	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturn400WhenTryUpdateVehicleWithNonexistentBrandId() throws Exception {
		Brand brand = brandRepository.save(new Brand("Audi"));
		Vehicle vehicle = vehicleRepository.save(new Vehicle(brand, 2012, "TT"));

		URI uri = new URI("/vehicles/" + vehicle.getId());

		JSONObject body = new JSONObject();
		body.put("model", "TT");
		body.put("brandId", 9999);
		body.put("year", 2012);

		mockMvc.perform(MockMvcRequestBuilders
				.put(uri)
				.content(body.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void shouldUpdateVehicleWithCorrectParameters() throws Exception {
		Brand brand = brandRepository.save(new Brand("Audi"));
		Vehicle vehicle = vehicleRepository.save(new Vehicle(brand, 2012, "TT"));

		URI uri = new URI("/vehicles/" + vehicle.getId());

		JSONObject body = new JSONObject();
		body.put("model", "TT");
		body.put("brandId", brand.getId());
		body.put("year", 2012);

		mockMvc.perform(MockMvcRequestBuilders
				.put(uri)
				.content(body.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void shouldCreateANewVehicle() throws Exception {

		Brand brand = brandRepository.save(new Brand("Audi"));

		URI uri = new URI("/vehicles");

		JSONObject body = new JSONObject();
		body.put("model", "TT");
		body.put("brandId", brand.getId());
		body.put("year", 2012);

		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(body.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void shouldReturn400WhenTryCreateVehicleWithNonexistentBrandId() throws Exception {

		URI uri = new URI("/vehicles");

		JSONObject body = new JSONObject();
		body.put("model", "TT");
		body.put("brandId", 999L);
		body.put("year", 2012);

		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(body.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void shouldDeleteVehicleWithCorrectVehicleId() throws Exception {

		Brand brand = brandRepository.save(new Brand("Audi"));
		Vehicle vehicle = vehicleRepository.save(new Vehicle(brand, 2012, "TT"));

		URI uri = new URI("/vehicles/" + vehicle.getId());

		mockMvc.perform(MockMvcRequestBuilders
				.delete(uri)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void shouldReturn400WhenTryDeleteVehicleWithNonexistentVehicleId() throws Exception {

		Brand brand = brandRepository.save(new Brand("Audi"));
		vehicleRepository.save(new Vehicle(brand, 2012, "TT"));

		URI uri = new URI("/vehicles/" + 999);

		mockMvc.perform(MockMvcRequestBuilders
				.delete(uri)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void shouldReturn200WhenFindAllVehicles() throws Exception {
		URI uri = new URI("/vehicles");

		mockMvc.perform(MockMvcRequestBuilders
				.get(uri)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void shouldReturn200WhenFindOneVehicle() throws Exception {
		Brand brand = brandRepository.save(new Brand("Audi"));
		Vehicle vehicle = vehicleRepository.save(new Vehicle(brand, 2012, "TT"));
		URI uri = new URI("/vehicles/" + vehicle.getId());


		mockMvc.perform(MockMvcRequestBuilders
				.get(uri)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void shouldReturn400WhenTryFindOneWithNonexistentVehicleId() throws Exception {
		Brand brand = brandRepository.save(new Brand("Audi"));
		Vehicle vehicle = vehicleRepository.save(new Vehicle(brand, 2012, "TT"));
		URI uri = new URI("/vehicles/" + 999);


		mockMvc.perform(MockMvcRequestBuilders
				.get(uri)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	    
}
