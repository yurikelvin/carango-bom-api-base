package br.com.caelum.carangobom.services.user;

import br.com.caelum.carangobom.exception.NotFoundException;
import br.com.caelum.carangobom.user.UserRepository;
import br.com.caelum.carangobom.user.UserWithoutPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ResponseEntity saveUserById(Long id){

            var validatedUser = userRepository.findById(id);
            if(validatedUser.isPresent()){
                var convertedUser =  UserWithoutPasswordDTO.convertSingleUser(validatedUser.get());
                return ResponseEntity.status(HttpStatus.OK).body(convertedUser);
            }
            throw new NotFoundException("Erro ao criar usuário");
    }

    public ResponseEntity getUserById(Long id){
        var validatedUser = userRepository.findById(id);
        if(validatedUser.isPresent()){
            var convertedUser = UserWithoutPasswordDTO.convertSingleUser(validatedUser.get());
            return ResponseEntity.status(HttpStatus.OK).body(convertedUser);
        }
        throw new NotFoundException("Usuário não encontrado.");
    }

    public UserWithoutPasswordDTO getUser(Long id){
        var validatedUser = userRepository.findById(id);
        if(validatedUser.isPresent()){
            var convertedUser = UserWithoutPasswordDTO.convertSingleUser(validatedUser.get());
            return convertedUser;
        }
        return null;
    }

}
