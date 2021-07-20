package br.com.caelum.carangobom.vehicle;

import java.util.ArrayList;
import java.util.List;

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

import br.com.caelum.carangobom.validacao.ErroDeParametroOutputDto;
import br.com.caelum.carangobom.validacao.ListaDeErrosOutputDto;

@RestController
@Transactional
@RequestMapping("/vehicles")
public class VehicleController {

	private VehicleRepository vehicleRepository;
	private VehicleService vehicleService;

	public VehicleController() {}

	@Autowired
	public VehicleController(VehicleRepository vehicleRepository, VehicleService vehicleService) {
		this.vehicleRepository = vehicleRepository;
		this.vehicleService = vehicleService;
	}

	@GetMapping
	public Page<Vehicle> findAll(
			@PageableDefault(sort = "model", direction = Direction.ASC, page = 0, size = 10)
			Pageable pageable
		) {
		return vehicleRepository.findAll(pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Vehicle> findById(@PathVariable Long id) throws Exception {
		Vehicle vehicle = vehicleService.findById(id);
		return ResponseEntity.ok().body(vehicle);
	}

	@PostMapping
	public ResponseEntity<Vehicle> create(
			@Valid @RequestBody VehicleForm vehicleForm,
			UriComponentsBuilder uriBuilder
	) throws Exception {
		Vehicle vehicle = vehicleService.create(vehicleForm);
		URI uri = uriBuilder.path("/brands/{id}").buildAndExpand(vehicle.getId()).toUri();
		return ResponseEntity.created(uri).body(vehicle);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Vehicle> update(
			@PathVariable Long id,
			@Valid @RequestBody VehicleForm vehicleForm
	) throws  Exception {
		Vehicle vehicle = vehicleService.update(id, vehicleForm);
		return ResponseEntity.ok().body(vehicle);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Vehicle> delete(@PathVariable Long id) throws Exception {
		Vehicle vehicle = vehicleService.delete(id);
		return ResponseEntity.ok().body(vehicle);
	}

	// TODO - Create ControllerAdvice To handler error
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
