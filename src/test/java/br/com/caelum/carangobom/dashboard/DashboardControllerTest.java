package br.com.caelum.carangobom.dashboard;

import br.com.caelum.carangobom.vehicle.VehicleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
@ActiveProfiles("test")
public class DashboardControllerTest {

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

        VehicleRepository localMockRepository = Mockito.mock(VehicleRepository.class);
        Mockito.when(localMockRepository.getDashboard()).thenReturn(dashboardList);
        DashboardController dashboardController = new DashboardController(localMockRepository);

        ResponseEntity<List<Dashboard>> actionController = dashboardController.getAll();

        Assert.assertEquals(dashboardList.size(), actionController.getBody().size());
        Mockito.verify(localMockRepository).getDashboard();
    }
}
