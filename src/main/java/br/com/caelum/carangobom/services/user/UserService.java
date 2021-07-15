package br.com.caelum.carangobom.services.user;

import br.com.caelum.carangobom.exception.BadRequestException;
import br.com.caelum.carangobom.exception.NotFoundException;
import br.com.caelum.carangobom.user.User;
import br.com.caelum.carangobom.user.UserRepository;
import br.com.caelum.carangobom.user.UserWithoutPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    private UserWithoutPasswordDTO getValidatedUserWithoutPasswordDTO(Long id){
        var validatedUser = userRepository.findById(id);
        if(validatedUser.isPresent()){
            return UserWithoutPasswordDTO.convertSingleUser(validatedUser.get());
        }
        return null;
    }

    public ResponseEntity getUserById(Long id){
        var validatedUser = userRepository.findById(id);
        if(validatedUser.isPresent()){
            var convertedUser = UserWithoutPasswordDTO.convertSingleUser(validatedUser.get());
            return ResponseEntity.status(HttpStatus.OK).body(convertedUser);
        }
        throw new NotFoundException("Usuário não encontrado.");
    }

    public  ResponseEntity<UserWithoutPasswordDTO> createNewUser(User user, UriComponentsBuilder uriBuilder){
        Optional<User> isCreated = userRepository.findByUsername(user.getUsername());

        if (isCreated.isPresent()) {
            String errorMessage = "Usuário já cadastrado";
            throw  new BadRequestException(errorMessage);
        }

        User encryptedUser = user;
        encryptedUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        userRepository.save(encryptedUser);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserWithoutPasswordDTO(user));

    }

    public ResponseEntity removeUserById(Long id){
        var user = getValidatedUserWithoutPasswordDTO(id);
        if(user == null){
            throw new NotFoundException("Usuário não encontrado.");
        }
        userRepository.deleteById(user.getId());
        return ResponseEntity.ok().build();
    }
}