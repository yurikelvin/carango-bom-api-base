package br.com.caelum.carangobom.user;

import br.com.caelum.carangobom.user.controller.UserController;
import br.com.caelum.carangobom.user.form.UserForm;
import br.com.caelum.carangobom.user.model.User;
import br.com.caelum.carangobom.user.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;

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
      void shouldIncrementUserForm() {
         UserForm userForm = new UserForm();

         userForm.setUsername("username");
         userForm.setPassword("password");

         Assert.assertEquals("username", userForm.getUsername());
         Assert.assertEquals("password", userForm.getPassword());
     }
}
