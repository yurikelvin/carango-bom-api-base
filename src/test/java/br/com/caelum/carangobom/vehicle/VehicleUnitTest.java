package br.com.caelum.carangobom.vehicle;

import br.com.caelum.carangobom.brand.Brand;
import br.com.caelum.carangobom.brand.BrandRepository;
import br.com.caelum.carangobom.brand.BrandService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;

import java.math.BigDecimal;

import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
public class VehicleUnitTest {

    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private BrandService brandService;
    @Mock
    private BrandRepository brandRepository;

    private VehicleService vehicleService;
    private UriComponentsBuilder uriBuilder;
    private EntityManager entityManager;

    @BeforeEach
    public void mockConfig() {
        openMocks(this);

        vehicleService = new VehicleService(vehicleRepository);
        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
    }

    @Test
    public void shouldCreateVehicle() throws Exception {
        Brand brand = new Brand(1L, "Audi");
        Vehicle vehicle = new Vehicle(brand, 2012, "TT", new BigDecimal(3500.56));
        VehicleForm vehicleForm = new VehicleForm(1L, 2012, "TT", new BigDecimal(3500.56));

        Mockito.when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        Mockito.when(brandService.findById(brand.getId())).thenReturn(brand);
//        Mockito.when(brandRepository.findById(brand.getId()))

        Vehicle createNewVehicle = vehicleService.create(vehicleForm);

        Assert.assertEquals(createNewVehicle, vehicle);
    }
}
