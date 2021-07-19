package br.com.caelum.carangobom.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
    public List<UserDTO> listAll() {
        // TODO create the user pagination
        List<User> users = userRepository.findAll();
        return UserDTO.toUserList(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> details(@PathVariable Long id){
        var getuser = userService.findById(id);
        var formatedUser = UserDTO.toUser(getuser);
        return ResponseEntity.ok(formatedUser);
    }

    @PostMapping("/users")
    @Transactional
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserForm userForm, UriComponentsBuilder uriBuilder) {
        User convertedReceivedUser = userForm.toUser();
        var createdUser = userService.createNewUser(convertedReceivedUser);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(createdUser.getId()).toUri();
        var convertedUserDTO = UserDTO.toUser(createdUser);
        return ResponseEntity.created(uri).body(convertedUserDTO);
    }

    @DeleteMapping("/users/{id}")
    @Transactional
    public ResponseEntity<UserDTO>delete(@PathVariable Long id) {
        userService.removeUserById(id);
        return ResponseEntity.ok().build();
    }
}
