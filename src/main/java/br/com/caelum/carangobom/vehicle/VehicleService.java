package br.com.caelum.carangobom.vehicle;

import br.com.caelum.carangobom.brand.Brand;
import br.com.caelum.carangobom.brand.BrandService;
import br.com.caelum.carangobom.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BrandService brandService;

    public VehicleService() {}

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
