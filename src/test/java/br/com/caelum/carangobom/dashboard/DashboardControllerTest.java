package br.com.caelum.carangobom.dashboard;

import br.com.caelum.carangobom.user.User;
import br.com.caelum.carangobom.vehicle.VehicleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
public class DashboardControllerTest {


    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private DashboardController dashboardController;

    @BeforeEach
    public void mockConfig() {
        openMocks(this);
    }

    @Test
    public void shouldConvertTheListReturnedList(){
        List<List<Object>> dashboardList =
                List.of(
                        List.of("Brand 1", new BigInteger(String.valueOf(2)), new BigDecimal(2000000)),
                        List.of("Brand 2", new BigInteger(String.valueOf(3)), new BigDecimal(54000000))
                );
        List<Dashboard> dashboardAction = Dashboard.toDashboard(dashboardList);
        Assert.assertEquals(dashboardAction.size(), dashboardList.size());
    }

    @Test
    public void shouldListTheListOfBrands(){
        List<List<Object>> dashboardList =
                List.of(
                        List.of("Brand 1", new BigInteger(String.valueOf(2)), new BigDecimal(2000000)),
                        List.of("Brand 2", new BigInteger(String.valueOf(3)), new BigDecimal(54000000))
                );
        List<Dashboard> dashboardAction = Dashboard.toDashboard(dashboardList);
        Assert.assertEquals(dashboardAction.size(), dashboardList.size());
    }
}
