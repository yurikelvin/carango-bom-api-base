package br.com.caelum.carangobom.user;

import br.com.caelum.carangobom.exception.BadRequestException;
import br.com.caelum.carangobom.exception.NotFoundException;
import br.com.caelum.carangobom.services.user.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void configuraMock() {
        openMocks(this);

        userService = new UserService(userRepository);
    }

    @Test
    void shouldCreateUserById(){
        User user = new User(1L, "username1", "password1");
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        var createUser = userService.saveUserById(user.getId());
        Assert.assertEquals(createUser.getStatusCodeValue(), 200);
    }

    @Test
    void shoulNotCreateUserById(){
        User user = new User(1L, "username1", "password1");
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assert.assertThrows(NotFoundException.class, () -> {
            userService.saveUserById(user.getId());
        });
    }

}
