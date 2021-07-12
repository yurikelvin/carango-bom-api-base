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
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserWithoutPasswordDTO> listAll() {
        // TODO create the user pagination
        List<User> users = userRepository.findAll();
        return UserWithoutPasswordDTO.convert(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserWithoutPasswordDTO>details(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(new UserWithoutPasswordDTO(user.get()));
        }
        throw new BadRequestException("Usuário informado não é válido");
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserForm userForm, UriComponentsBuilder uriBuilder) {
        User user = userForm.convert();

        // TODO - Create a service to validate user
        User isCreated = userRepository.findByUsername(user.getUsername());

        if (isCreated != null) {
            String errorMessage = "Usuário já cadastrado";
            throw  new BadRequestException(errorMessage);
        }

        userRepository.save(user);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDTO(user));
    }
}
