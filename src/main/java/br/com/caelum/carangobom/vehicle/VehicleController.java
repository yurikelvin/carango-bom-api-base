package br.com.caelum.carangobom.vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpStatus;

import java.net.URI;

import br.com.caelum.carangobom.brand.BrandRepository;
import br.com.caelum.carangobom.validacao.ErroDeParametroOutputDto;
import br.com.caelum.carangobom.validacao.ListaDeErrosOutputDto;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
	
		@Autowired
	    private VehicleRepository vehicleRepository;
		@Autowired
	    private BrandRepository brandRepository;

	    @Autowired
	    public VehicleController(VehicleRepository vehicleRepository) {
	        this.vehicleRepository = vehicleRepository;
	    }
	    
	    @GetMapping
	    public Page<Vehicle> findAll(
				@PageableDefault(sort = "model", direction = Direction.ASC, page = 0, size = 10)
				Pageable pageable
			) {
		    return vehicleRepository.findAll(pageable);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Vehicle> findById(@PathVariable Long id) {
	        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
	        if (vehicle.isPresent()) {
	            return ResponseEntity.ok(vehicle.get());
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @PostMapping
	    @Transactional
	    public ResponseEntity<Vehicle> save(@Valid @RequestBody VehicleForm vehicleForm, UriComponentsBuilder uriBuilder) {
	    	
	    	Vehicle vehicle = vehicleRepository.save(vehicleForm.toVehicle(brandRepository));
	        URI uri = uriBuilder.path("/vehicles/{id}").buildAndExpand(vehicle.getId()).toUri();
	        return ResponseEntity.created(uri).body(vehicle);
	    }

	    @PutMapping("/{id}")
	    @Transactional
	    public ResponseEntity<Vehicle> update(@PathVariable Long id, @Valid @RequestBody Vehicle vehicleForm) {
	        Optional<Vehicle> optional = vehicleRepository.findById(id);
	        if (optional.isPresent()) {
	        	
	            Vehicle vehicle = optional.get();
	            vehicle.setModel(vehicleForm.getModel());
	            vehicle.setBrand(vehicleForm.getBrand());
	            vehicle.setYear(vehicleForm.getYear());
	            return ResponseEntity.ok(vehicle);
	            
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @DeleteMapping("/{id}")
	    @Transactional
	    public ResponseEntity<Vehicle> delete(@PathVariable Long id) {
	        Optional<Vehicle> optional = vehicleRepository.findById(id);
	        if (optional.isPresent()) {
	            Vehicle vehicle = optional.get();
	            vehicleRepository.delete(vehicle);
	            return ResponseEntity.ok(vehicle);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ListaDeErrosOutputDto validate(MethodArgumentNotValidException exception) {
	        List<ErroDeParametroOutputDto> l = new ArrayList<>();
	        exception.getBindingResult().getFieldErrors().forEach(e -> {
	            ErroDeParametroOutputDto d = new ErroDeParametroOutputDto();
	            d.setParametro(e.getField());
	            d.setMensagem(e.getDefaultMessage());
	            l.add(d);
	        });
	        ListaDeErrosOutputDto l2 = new ListaDeErrosOutputDto();
	        l2.setErros(l);
	        return l2;
	    }
}
