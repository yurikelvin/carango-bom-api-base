package br.com.caelum.carangobom.auth;

import br.com.caelum.carangobom.config.security.TokenService;
import br.com.caelum.carangobom.exception.BadRequestException;
import br.com.caelum.carangobom.user.User;
import br.com.caelum.carangobom.user.UserController;
import br.com.caelum.carangobom.user.UserForm;
import br.com.caelum.carangobom.user.UserRepository;
import org.apache.coyote.Response;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import javax.persistence.EntityManager;

import java.net.http.HttpResponse;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class AuthControlleTest {

    private EntityManager entityManager;
    @Mock
    private AuthController authController;

    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authManager;

    @BeforeEach
    public void configuraMock() {
        openMocks(this);
    }

    @Test
    void shouldReturnError(){
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername("new_1");
        loginForm.setPassword("password");

        UserForm userForm = new UserForm("1", "validaPassword");
        User user = userForm.convert();

        when(userRepository.save(user)).thenReturn(user);

        authController.autenticar(loginForm);
    }

    @Test
    void shouldNotDeleteUserWithInvalidPathId(){

        LoginForm loginForm = new LoginForm();
        loginForm.setUsername("new_1");
        loginForm.setPassword("password");

        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(null);

        when(authManager.authenticate(Mockito.any())).thenReturn(null);

        ResponseEntity<?> controller = authController.autenticar(loginForm);

        System.out.println(controller);

        Assert.assertNull(controller);

    }


}
