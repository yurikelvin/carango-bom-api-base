package br.com.caelum.carangobom.user;

import br.com.caelum.carangobom.exception.BadRequestException;
import br.com.caelum.carangobom.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userService = new UserService(this.userRepository);
    }

    @GetMapping("/users")
    public List<UserWithoutPasswordDTO> listAll() {
        // TODO create the user pagination
        List<User> users = userRepository.findAll();
        return UserWithoutPasswordDTO.convert(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserWithoutPasswordDTO> details(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    @Transactional
    public ResponseEntity<UserWithoutPasswordDTO> create(@RequestBody @Valid UserForm userForm, UriComponentsBuilder uriBuilder) {
        User user = userForm.convert();
        return userService.createNewUser(user, uriBuilder);
    }

    @DeleteMapping("/users/{id}")
    @Transactional
    public ResponseEntity<UserWithoutPasswordDTO>delete(@PathVariable Long id) {
        return userService.removeUserById(id);
    }
}
