package br.com.caelum.carangobom.user;

import br.com.caelum.carangobom.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

public class CreateUserService {

    @Autowired
    private final UserRepository repository;

    public CreateUserService(UserRepository repository) {
        this.repository = repository;
    }

    public  ResponseEntity<UserWithoutPasswordDTO> createNewUser(User user, UriComponentsBuilder uriBuilder){
        Optional<User> isCreated = repository.findByUsername(user.getUsername());

        if (isCreated.isPresent()) {
            String errorMessage = "Usuário já cadastrado";
            throw  new BadRequestException(errorMessage);
        }

        User encryptedUser = user;
        encryptedUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        System.out.println(encryptedUser);

        repository.save(encryptedUser);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserWithoutPasswordDTO(user));

    }
}
