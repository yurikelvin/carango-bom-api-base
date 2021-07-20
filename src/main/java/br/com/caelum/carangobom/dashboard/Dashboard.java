package br.com.caelum.carangobom.dashboard;


import br.com.caelum.carangobom.brand.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard {
     String brand;
     BigInteger totalVehicle;
     BigDecimal totalPrice;


    public static List<Dashboard> toDashboard(List<List<Object>> dashboard){
        List<Dashboard> listDashboard = new ArrayList<>(1);
        dashboard.forEach(el ->
                listDashboard.add(new Dashboard((String) el.get(0), (BigInteger) el.get(1), (BigDecimal) el.get(2)))
        );
        return listDashboard;
    }
}
