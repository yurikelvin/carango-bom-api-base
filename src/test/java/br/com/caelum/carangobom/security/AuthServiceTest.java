package br.com.caelum.carangobom.security;

import br.com.caelum.carangobom.config.security.AuthenticationService;
import br.com.caelum.carangobom.user.User;
import br.com.caelum.carangobom.user.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        authenticationService = new AuthenticationService(userRepository);
    }

    @Test
    void shouldReturnTheUser(){

        var newUser = new User(1L, "username", "password");

        when(userRepository.findByUsername(newUser.getUsername())).thenReturn(java.util.Optional.of(newUser));

        var authAction = authenticationService.loadUserByUsername(newUser.getUsername());

        Assert.assertEquals(authAction.getUsername(), newUser.getUsername());
    }

    @Test
    void shouldReturnUsernameNotFoundException(){
        var userName = "any_string";

        when(userRepository.findByUsername(userName)).thenReturn(java.util.Optional.empty());

        Assert.assertThrows(UsernameNotFoundException.class, () -> {
            authenticationService.loadUserByUsername(userName);
        });
    }
}
