package br.com.caelum.carangobom.user;

import br.com.caelum.carangobom.exception.BadRequestException;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UserUnitTest {

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
    void shouldCreateANewUser(){
        UserForm userForm = new UserForm("1", "validaPassword");
        User user = userForm.convert();

        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<UserDTO> createUserContorller = userController.create(userForm, uriBuilder);

        Assert.assertEquals(createUserContorller.getBody().getId(), user.getId());
        Assert.assertEquals(createUserContorller.getBody().getUsername(), user.getUsername());
        Assert.assertEquals(createUserContorller.getBody().getPassword(), user.getPassword());
    }

    @Test
    void shouldNotCreateANewUserWithTheSameUsername() {
        UserForm userForm = new UserForm("1", "validaPassword");
        User user = userForm.convert();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(new User(2L, "username123", "odksaod"));

        Assert.assertThrows(BadRequestException.class, () -> {
            userController.create(userForm, uriBuilder);
        });
    }

    @Test
    void shouldIncrementUserForm() {
        UserForm userForm = new UserForm();

        userForm.setUsername("username");
        userForm.setPassword("password");

        Assert.assertEquals("username", userForm.getUsername());
        Assert.assertEquals("password", userForm.getPassword());
    }

    @Test
    void shouldTestIncrementUserWithoutConstructor() {
        Mockito.mock(User.class);
        User newUser = new User();

        newUser.setId(1L);
        newUser.setUsername("username");
        newUser.setPassword("password");

        Assert.assertEquals(java.util.Optional.of(1L).get(), newUser.getId());
        Assert.assertEquals("username", newUser.getUsername());
        Assert.assertEquals("password", newUser.getPassword());
    }

    @Test
    void shouldTestIncrementUserWithConstructor() {
        Mockito.mock(User.class);
        User newUser = new User(1L, "username", "password");

        Assert.assertEquals(java.util.Optional.of(1L).get(), newUser.getId());
        Assert.assertEquals("username", newUser.getUsername());
        Assert.assertEquals("password", newUser.getPassword());
    }

    @Test
    void shouldTestUserDTO() {
        User newUser = new User(1L, "username", "password");
        Mockito.mock(UserDTO.class);

        UserDTO userDTO = new UserDTO(newUser);

        Assert.assertEquals(newUser.getId(), userDTO.getId());
        Assert.assertEquals(newUser.getUsername(), userDTO.getUsername());
        Assert.assertEquals(newUser.getPassword(), userDTO.getPassword());
    }

    @Test
    void shouldTestUserDTO_Convert() {
        List<User> userList = List.of(
                new User(1L, "username1", "password1"),
                new User(2L, "username2", "password2")
        );

        List<UserDTO> userConvertDTO = UserDTO.convert(userList);
        Assert.assertEquals(userConvertDTO.size(), userList.size());
    }

    @Test
    void shouldTestListAll() {
        List<User> userList = List.of(
                new User(1L, "username1", "password1"),
                new User(2L, "username2", "password2")
        );

        when(userRepository.findAll()).thenReturn(userList);

        List<UserWithoutPasswordDTO> userListController = userController.listAll();

        assertEquals(userList.size(),userListController.size());
    }

    @Test
    void shouldFindUserWithPathId(){
        User newUser = new User(1L, "username1", "password1");
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(newUser));
        ResponseEntity<UserWithoutPasswordDTO> findById = userController.details(1L);
        Assert.assertEquals(findById.getBody().getId(), newUser.getId());
        Assert.assertEquals(findById.getBody().getUsername(), newUser.getUsername());
    }

    @Test
    void shouldNotFindUserWithInvalidPathId(){
        Assert.assertThrows(BadRequestException.class, () -> {
            userController.details(1L);
        });
    }


    @Test
    void shouldReceiveTheUserFormValues(){
        User newUser = new User(1L, "username", "password");
        Mockito.mock(UserWithoutPasswordDTO.class);

        UserWithoutPasswordDTO userDTO = new UserWithoutPasswordDTO(newUser);

        Assert.assertEquals(newUser.getId(), userDTO.getId());
        Assert.assertEquals(newUser.getUsername(), userDTO.getUsername());
    }
}
