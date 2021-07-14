package br.com.caelum.carangobom.vehicle;

import br.com.caelum.carangobom.brand.BadRequestException;
import br.com.caelum.carangobom.brand.Brand;
import br.com.caelum.carangobom.brand.BrandService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@AllArgsConstructor
@Data
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BrandService brandService;

    public Vehicle create(VehicleForm vehicleForm) throws Exception {

        Brand brand = brandService.findById(vehicleForm.getBrandId());
        Vehicle vehicle = vehicleForm.toVehicle(brand);
        return vehicleRepository.save(vehicle);
    }

    public Vehicle update(Long id, VehicleForm vehicleForm) throws Exception {
        Brand brand = brandService.findById(vehicleForm.getBrandId());
        Vehicle vehicleData = vehicleForm.toVehicle(brand);
        Vehicle vehicle = this.findById(id);

        vehicle.setModel(vehicleData.getModel());
        vehicle.setBrand(vehicleData.getBrand());
        vehicle.setYear(vehicleData.getYear());

        return vehicle;
    }

    public Vehicle findById(Long id) throws Exception {
        Optional<Vehicle> optional = vehicleRepository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new NotFoundException("Vehicle not Found");
    }

    public Vehicle delete(Long id) throws Exception {
        Vehicle vehicle = this.findById(id);
        vehicleRepository.delete(vehicle);
        return vehicle;
    }
}
