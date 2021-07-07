package br.com.caelum.carangobom.user;

import br.com.caelum.carangobom.user.controller.UserController;
import br.com.caelum.carangobom.user.form.UserForm;
import br.com.caelum.carangobom.user.model.User;
import br.com.caelum.carangobom.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.util.UriComponentsBuilder;
import static org.mockito.MockitoAnnotations.openMocks;

 class UserControllerTest {

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

}
