package br.com.caelum.carangobom.user;

import br.com.caelum.carangobom.exception.BadRequestException;
import br.com.caelum.carangobom.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;


// TODO REFACTOR THE HANDLE OF THE SERVICES AND CONTROLLER
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    private User findById(Long id){
        var validatedUser = userRepository.findById(id);
        if(validatedUser.isPresent()){
            return validatedUser.get();
        }
        throw new NotFoundException("Usuário não encontrado.");
    }

    public void validateUser(User user){
        Optional<User> isCreated = userRepository.findByUsername(user.getUsername());
        if (isCreated.isPresent()) {
            throw  new BadRequestException("Usuário já cadastrado");
        }
    }

    public  ResponseEntity<UserDTO> createNewUser(User user, UriComponentsBuilder uriBuilder){
        User encryptedUser = user;
        encryptedUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        userRepository.save(encryptedUser);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDTO(user));
    }

    public ResponseEntity removeUserById(Long id){
        var user = findById(id);
        userRepository.deleteById(user.getId());
        return ResponseEntity.ok().build();
    }
}