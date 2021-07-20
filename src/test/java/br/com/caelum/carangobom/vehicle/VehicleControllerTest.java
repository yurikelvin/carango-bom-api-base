package br.com.caelum.carangobom.vehicle;

import br.com.caelum.carangobom.brand.Brand;
import br.com.caelum.carangobom.brand.BrandService;
import br.com.caelum.carangobom.exception.BadRequestException;
import br.com.caelum.carangobom.exception.NotFoundException;
import ch.qos.logback.core.net.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
class VehicleControllerTest {

    private VehicleController vehicleController;
    private UriComponentsBuilder uriBuilder;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private BrandService brandService;

    private VehicleForm vehicleForm;
    private Vehicle vehicle;
    private Brand brand;

    @BeforeEach
    public void mockConfig() {
        openMocks(this);
        this.vehicleController = new VehicleController(this.vehicleRepository, this.vehicleService);
        this.uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
        this.vehicleForm = new VehicleForm(1L, 2012, "model", new BigDecimal(20000));
        this.brand = new Brand(1L, "Audi");
        this.vehicle = this.vehicleForm.toVehicle(this.brand);
    }

//    public String getJsonBodyResponse() {
//        Object obj = new Object();
//        ObjectWriter objectWriter = new ObjectMapper();
//    }

    @Test
    void shouldCreateVehicle() throws Exception {
        when(this.vehicleService.create(vehicleForm)).thenReturn(this.vehicle);

        ResponseEntity<Vehicle> result = vehicleController.create(vehicleForm, uriBuilder);
        assertEquals(result.getStatusCodeValue(), 201);
        Mockito.verify(this.vehicleService).create(vehicleForm);
    }
//
//    @Test
//    void shouldReturn400WhenTryCreateVehicleWithWrongParameters() throws Exception {
//        VehicleForm incorrectVehicleForm = new VehicleForm(1L, 1700, "E", new BigDecimal(-0));
//        when(this.vehicleService.create(vehicleForm)).thenReturn(this.vehicle);
//        JSONObject expectedResponseBody = new JSONObject();
//        expectedResponseBody.
//
//        {
//            "erros": [
//                {
//                    "parametro": "year",
//                        "mensagem": "The vehicle year must be equal or greater than 1880."
//                },
//                {
//                    "parametro": "price",
//                        "mensagem": "must be greater than or equal to 0"
//                }
//            ],
//            "quantidadeDeErros": 2
//        }
//
//        ResponseEntity<Vehicle> result = vehicleController.create(vehicleForm, uriBuilder);
//        assertEquals(result.getStatusCodeValue(), 400);
//        assertEquals(result.getBody(), );
//        Mockito.verify(this.vehicleService).create(vehicleForm);
//    }


//
//
//    @Test
//    void shouldTestUserDTO_Convert() {
//        List<User> userList = List.of(
//                new User(1L, "username1", "password1"),
//                new User(2L, "username2", "password2")
//        );
//
//        List<UserDTO> userConvertDTO = UserDTO.toUserList(userList);
//        assertEquals(userConvertDTO.size(), userList.size());
//    }
//
//    @Test
//    void shouldTestListAll() {
//        List<User> userList = List.of(
//                new User(1L, "username1", "password1"),
//                new User(2L, "username2", "password2")
//        );
//
//        when(userRepository.findAll()).thenReturn(userList);
//
//        List<UserDTO> userListController = userController.listAll();
//
//        assertEquals(userList.size(),userListController.size());
//    }
//
//    @Test
//    void shouldFindUserWithPathId(){
//        User user = new User(1L, "username1", "password1");
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        var findById = userController.details(user.getId());
//        assertEquals(findById.getStatusCodeValue(), 200);
//    }
//
//    @Test
//    void shouldNotFindUserWithInvalidPathId(){
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//        Assert.assertThrows(NotFoundException.class, () -> {
//            userController.details(1L);
//        });
//    }
//
//    @Test
//    void shouldDeleteUserWithPathId(){
//        User newUser = new User(1L, "username1", "password1");
//
//        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(newUser));
//
//        ResponseEntity<UserDTO> userControllerDelete = userController.delete(1L);
//
//        assertEquals(userControllerDelete.getStatusCodeValue(), 200);
//    }
//
//    @Test
//    void shouldNotDeleteUserWithInvalidPathId(){
//        User newUser = new User(1L, "username1", "password1");
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        Assert.assertThrows(NotFoundException.class, () -> {
//            userController.delete(1L);
//        });
//    }
}
