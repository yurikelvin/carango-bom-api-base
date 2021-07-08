package br.com.caelum.carangobom.vehicle;

import java.net.URI;

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
class SpringVehicleControllerTest {

		@Autowired
		private BrandRepository brandRepository;
	    @Autowired
	    private MockMvc mockMvc;
	    
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
	                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	    }

	    @Test
	    public void shouldReturn404WhenTryCreateVehicleWithNonexistentBrandId() throws Exception {
	    	
			URI uri = new URI("/vehicles");
				        
	        JSONObject body = new JSONObject();
	        body.put("model", "TT");
	        body.put("brandId", 999L);
	        body.put("year", 2012);
	        
	        mockMvc.perform(MockMvcRequestBuilders
	                .post(uri)
	                .content(body.toString())
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
	    }
}
