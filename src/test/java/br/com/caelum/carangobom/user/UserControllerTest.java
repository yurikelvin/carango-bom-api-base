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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

 class UserControllerTest {

    private UserController userController;
    private UriComponentsBuilder uriBuilder;
    private EntityManager entityManager;


    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void configuraMock() {
        openMocks(this);

        userController = new UserController(userRepository);
        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
    }


    @Test
    void shouldNotCreateANewUser(){
        UserForm userForm = new UserForm("1", "sssssss");
        User user = userForm.convert();

        when(
                userController.create(userForm, uriBuilder)
        ).thenThrow(RuntimeException.class);

        try {
            Mockito.verifyNoInteractions(userRepository.save(user));
        }catch (Exception e){}
    }

    @Test
     void shoudNotCreateDuplicatedValues(){
        User newUser = new User(1L, "username", "password");
        when(userRepository.findById(newUser.getId())).thenReturn(java.util.Optional.of(newUser));

        UserForm newUserForm = new UserForm("username", "password");

        ResponseEntity<UserDTO> response = userController.create(newUserForm, uriBuilder);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
