package br.com.caelum.carangobom.dashboard;

import br.com.caelum.carangobom.vehicle.VehicleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
@ActiveProfiles("test")
public class DashboardControllerTest {
    @Mock
    private VehicleRepository vehicleRepository;

    private DashboardController dashboardController;

    @BeforeEach
    public void mockConfig() {
        openMocks(this);
        dashboardController = new DashboardController(vehicleRepository);
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
}
