package br.com.caelum.carangobom.dashboard;


import br.com.caelum.carangobom.brand.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
public class Dashboard {
    public String brand;
    public BigInteger totalVehicle ;
    public BigDecimal totalPrice;
}
