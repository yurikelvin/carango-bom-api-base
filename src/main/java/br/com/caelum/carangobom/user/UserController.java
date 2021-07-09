package br.com.caelum.carangobom.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<UserDTO> listAll() {
        List<User> users = userRepository.findAll();
        return UserDTO.convert(users);
    }

    @PostMapping("/users")
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid UserForm userForm, UriComponentsBuilder uriBuilder) {
        User user = userForm.convert();

        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            String jsonError = "{\"message\": \"Usuário e senha são dadoos obrigatórios\"}";
            return new ResponseEntity<>(
                    jsonError,
                    HttpStatus.BAD_REQUEST);
        }

        List<User> isCreated = userRepository.findByUsername(user.getUsername());

        if (isCreated.size() > 0) {
            String jsonError = "{\"message\": \"Usuário já cadastrado na base de dados\"}";
            return new ResponseEntity<>(
                    jsonError,
                    HttpStatus.BAD_REQUEST);
        }

        userRepository.save(user);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDTO(user));
    }
}
