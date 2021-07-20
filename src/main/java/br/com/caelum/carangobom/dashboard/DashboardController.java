package br.com.caelum.carangobom.dashboard;

import br.com.caelum.carangobom.user.UserDTO;
import br.com.caelum.carangobom.vehicle.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    public DashboardController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping
    public ResponseEntity<List<Dashboard>> getAll(){
        List<List<Object>> list = vehicleRepository.getDashboard();
        List<Dashboard> listDashboard = Dashboard.toDashboard(list);
        return ResponseEntity.ok(listDashboard);
    }
}
