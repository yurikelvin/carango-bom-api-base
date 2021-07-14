package br.com.caelum.carangobom.security;

import br.com.caelum.carangobom.user.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
public class AutenticacaoViaTokenFilterTest {

    @BeforeEach
    public void configuraMock() {
        openMocks(this);
    }
}
