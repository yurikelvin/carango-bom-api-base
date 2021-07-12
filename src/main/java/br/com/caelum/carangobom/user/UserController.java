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
    public ResponseEntity<UserWithoutPasswordDTO> create(@RequestBody @Valid UserForm userForm, UriComponentsBuilder uriBuilder) {

        CreateUserService createService = new CreateUserService(userRepository);

        User user = userForm.convert();

        return createService.createNewUser(user, uriBuilder);
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<UserWithoutPasswordDTO>delete(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        throw new BadRequestException("Usuário informado não é válido");
    }
}
