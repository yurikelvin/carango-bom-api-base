package br.com.caelum.carangobom.dashboard;

import br.com.caelum.carangobom.user.UserDTO;
import br.com.caelum.carangobom.vehicle.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<List<Object>> list = vehicleRepository.getDashboard();

        List<Dashboard> listDashboard = new ArrayList<>(1);

        list.forEach(el ->
            listDashboard.add(new Dashboard((String) el.get(0), (BigInteger) el.get(1), (BigDecimal) el.get(2)))
        );

        return ResponseEntity.ok(listDashboard);
    }
}
