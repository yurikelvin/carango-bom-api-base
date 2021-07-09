package br.com.caelum.carangobom.vehicle;

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

@RunWith(SpringRunner.class)
@DataJpaTest
class VehicleUnitTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    void souldConvertVehicleFormToVehicleIfBrandExist() throws Exception {
    	
    	Brand brand = new Brand("Audi");
    	
    	Brand createdBrand = brandRepository.save(brand);
    	
    	VehicleForm vehicleForm = new VehicleForm(createdBrand.getId(), "TT", 2012);
        Vehicle resultVehicle = vehicleForm.toVehicle(brandRepository);
        
        Vehicle expected = new Vehicle(createdBrand, 2012, "TT");

        Assert.assertEquals(expected.getModel(), resultVehicle.getModel());
		Assert.assertEquals(expected.getYear(), resultVehicle.getYear());
		Assert.assertEquals(expected.getId(), resultVehicle.getId());
		Assert.assertEquals(expected.getBrand().getName(), resultVehicle.getBrand().getName());
		Assert.assertEquals(expected.getBrand().getId(), resultVehicle.getBrand().getId());
    }
    
    @Test
    void souldReturn404WhenTryToConvertVehicleFormToVehicleWithNonexistentBrandId() {
    	
    	VehicleForm vehicleForm = new VehicleForm(1L, "TT", 2012);
          
        Assert.assertThrows(BadRequestException.class, () -> vehicleForm.toVehicle(brandRepository));
		
    }
}


