package br.com.caelum.carangobom.dashboard;


import br.com.caelum.carangobom.brand.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Dashboard {
    public Brand brand;
    public Integer totalVehicle ;
    public BigDecimal totalPrice;
}
