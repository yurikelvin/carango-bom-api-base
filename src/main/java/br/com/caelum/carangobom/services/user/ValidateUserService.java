package br.com.caelum.carangobom.services.user;

import br.com.caelum.carangobom.user.User;
import br.com.caelum.carangobom.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ValidateUserService {

    @Autowired
    private UserRepository userRepository;

    ValidateUserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User execute(Long id){
        var validatedUser = userRepository.findById(id);

        if(validatedUser.isPresent()){
            return validatedUser.get();
        }

        throw new UsernameNotFoundException("Usuário informado não é válido");
    }

}
