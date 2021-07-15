package br.com.caelum.carangobom.user;

import br.com.caelum.carangobom.exception.BadRequestException;
import br.com.caelum.carangobom.exception.NotFoundException;
import br.com.caelum.carangobom.services.user.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
class UserServiceTest {

    private UserService userService;

    private UriComponentsBuilder uriBuilder;

    @Mock
    private UserRepository userRepository;

    User newUser = new User(1L, "username1", "password1");
    UserForm userForm = new UserForm("username1", "password1");


    @BeforeEach
    public void configuraMock() {
        openMocks(this);
        userService = new UserService(userRepository);
        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
    }

    @Test
    void shouldRemoveUser(){
        User newUser = new User(1L, "username1", "password1");
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(newUser));

        var userServiceAction = userService.removeUserById(newUser.getId());

        Assert.assertEquals(userServiceAction.getStatusCodeValue(), 200);
    }

    @Test
    void shouldNotRemoveUser(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assert.assertThrows(
                NotFoundException.class, ()->
                userService.removeUserById(1L)
        );
    }

    @Test
    void shouldCreateOneUser(){
        when(userRepository.save(newUser)).thenReturn(newUser);
        var userServiceAction = userService.createNewUser(newUser, uriBuilder);
        Assert.assertEquals(userServiceAction.getStatusCodeValue(), 201);
    }

    @Test
    void shouldNotCreateOneUser(){
        when(userRepository.save(newUser))
                .thenThrow(new BadRequestException());
        Assert.assertThrows(
                BadRequestException.class, ()->
                        userService.createNewUser(newUser, uriBuilder)
        );
    }

    @Test
    void shouldFindOneUser(){
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(newUser));
        var userServiceAction = userService.getUserById(newUser.getId());
        Assert.assertEquals(userServiceAction.getStatusCodeValue(), 200);
    }

    @Test
    void shouldNotFindOneUser(){
        when(userRepository.save(newUser))
                .thenThrow(new NotFoundException());
        Assert.assertThrows(
                NotFoundException.class, ()->
                        userService.createNewUser(newUser, uriBuilder)
        );
    }


}