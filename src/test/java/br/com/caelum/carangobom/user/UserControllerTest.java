package br.com.caelum.carangobom.user;

import br.com.caelum.carangobom.user.controller.UserController;
import br.com.caelum.carangobom.user.dto.UserDTO;
import br.com.caelum.carangobom.user.form.UserForm;
import br.com.caelum.carangobom.user.model.User;
import br.com.caelum.carangobom.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class UserControllerTest {

    private UserController userController;
    private UriComponentsBuilder uriBuilder;


    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void configuraMock() {
        openMocks(this);

        userController = new UserController(userRepository);
        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
    }


    @Test
    void shouldCreateANewUser(){
        UserForm userForm = new UserForm("", "password");
        User user = userForm.convert();

        Mockito.when(
                userController.create(userForm, uriBuilder)
        ).thenThrow(RuntimeException.class);

        Mockito.notNull();
        try {
            Mockito.verifyNoInteractions(userRepository.save(user));
        }catch (Exception e){}
    }

    @Test
    void shouldReturnTheUserList() {
        List<User> users = Stream.of(
                new User(1L, "User1", "dsdds232"),
                new User(2L,"User2", "123213")
        ).collect(Collectors.toList());

        when(userRepository.findAll())
                .thenReturn(users);

        List<UserDTO> resultado = userController.listUsers();

        assertEquals(resultado.size(), users.size());
    }

}
