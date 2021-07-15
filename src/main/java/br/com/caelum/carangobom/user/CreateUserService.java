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


}
