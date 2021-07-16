package br.com.caelum.carangobom.user;

import br.com.caelum.carangobom.exception.BadRequestException;
import br.com.caelum.carangobom.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


// TODO REFACTOR THE HANDLE OF THE SERVICES AND CONTROLLER
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    User findById(Long id){
        var validatedUser = userRepository.findById(id);
        if(validatedUser.isPresent()){
            return validatedUser.get();
        }
        throw new NotFoundException("Usuário não encontrado.");
    }

    public void usernameAlreadyInUse(User user){
        Optional<User> isCreated = userRepository.findByUsername(user.getUsername());
        if (isCreated.isPresent()) {
            throw  new BadRequestException("Usuário já cadastrado");
        }
    }

    public User createNewUser(User user){
        usernameAlreadyInUse(user);
        User encryptedUser = user;
        encryptedUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(encryptedUser);
        return user;
    }

    public boolean removeUserById(Long id){
        var user = findById(id);
        userRepository.deleteById(user.getId());
        return true;
    }
}