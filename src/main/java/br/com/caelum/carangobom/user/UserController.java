package br.com.caelum.carangobom.user;

import br.com.caelum.carangobom.exception.BadRequestException;
import com.sun.xml.bind.v2.TODO;
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
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<UserDTO> listAll() {
        // TODO create the user pagination
        List<User> users = userRepository.findAll();
        return UserDTO.convert(users);
    }

    @PostMapping("/users")
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid UserForm userForm, UriComponentsBuilder uriBuilder) {
        User user = userForm.convert();

        // TODO - Create a service to validate user
        List<User> isCreated = userRepository.findByUsername(user.getUsername());

        if (isCreated.size() > 0) {
            String errorMessage = "Usuário já cadastrado";
            throw  new BadRequestException(errorMessage);
        }

        userRepository.save(user);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDTO(user));
    }
}
