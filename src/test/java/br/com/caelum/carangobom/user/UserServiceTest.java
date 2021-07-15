package br.com.caelum.carangobom.user;

import br.com.caelum.carangobom.exception.NotFoundException;
import br.com.caelum.carangobom.services.user.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void configuraMock() {
        openMocks(this);

        userService = new UserService(userRepository);
    }
}
