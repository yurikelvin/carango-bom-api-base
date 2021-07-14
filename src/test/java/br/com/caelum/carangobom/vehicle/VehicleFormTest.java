package br.com.caelum.carangobom.vehicle;

import br.com.caelum.carangobom.user.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.caelum.carangobom.brand.Brand;
import br.com.caelum.carangobom.brand.BrandRepository;
import br.com.caelum.carangobom.brand.BadRequestException;

import org.junit.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(SpringRunner.class)
@DataJpaTest
class VehicleFormTest {

    @Test
    void shouldConvertVehicleFormToVehicleIfBrandExist() throws Exception {
    	
    	Brand brand = new Brand(1L, "Audi");
    	
    	VehicleForm vehicleForm = new VehicleForm(brand.getId(), 2018, "TT");
        Vehicle result = vehicleForm.toVehicle(brand);
        
        Vehicle expected = new Vehicle(brand, 2018, "TT");

        Assert.assertEquals(expected.getModel(), result.getModel());
		Assert.assertEquals(expected.getYear(), result.getYear());
		Assert.assertEquals(expected.getId(), result.getId());
		Assert.assertEquals(expected.getBrand().getName(), result.getBrand().getName());
		Assert.assertEquals(expected.getBrand().getId(), result.getBrand().getId());
    }
}


