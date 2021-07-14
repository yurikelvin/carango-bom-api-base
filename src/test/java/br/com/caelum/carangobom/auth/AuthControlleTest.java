package br.com.caelum.carangobom.auth;

import br.com.caelum.carangobom.config.security.AuthenticationService;
import br.com.caelum.carangobom.config.security.TokenService;
import br.com.caelum.carangobom.exception.BadRequestException;
import br.com.caelum.carangobom.user.*;
import org.apache.coyote.Response;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import java.net.http.HttpResponse;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
public class AuthControlleTest {

    private EntityManager entityManager;

    @Mock
    private AuthenticationService authenticationService;


    @Mock
    private AuthController authController;

    @Mock
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
        loginForm.setUsername("");
        loginForm.setPassword("password");

        UserForm userForm = new UserForm("new_1", "validaPassword");
        User user = userForm.convert();

        when(tokenService.gerarToken(Mockito.any())).thenReturn("any");

        ResponseEntity<?> a = authController.autenticar(loginForm);

        System.out.println(a);

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


    @Test
    void TestAuthenticationService(){
        User user = new User(1L, "username", "validaPassword");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);

        Assert.assertThrows(UsernameNotFoundException.class, () -> {
            authenticationService.loadUserByUsername(user.getUsername());
        });

    }
}
