package br.com.caelum.carangobom.vehicle;

import br.com.caelum.carangobom.dashboard.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query(value = "SELECT brand.name as brand, \n" +
            "COUNT(vehicle.id) as TotalVehicle,\n" +
            "SUM(vehicle.price) as totalPrice FROM vehicle\n" +
            "LEFT JOIN brand on vehicle.brand_id = brand.id\n" +
            "GROUP BY name", nativeQuery = true)
    List<List<Object>> getDashboard();
}
