package br.com.caelum.carangobom.auth;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class TestTokenDTO {

    @Test
    void testAddingValues(){
        String tokenValue = Mockito.anyString();
        String type = Mockito.anyString();

        TokenDTO token = new TokenDTO(tokenValue, type);

        Assert.assertEquals(token.getToken(), tokenValue);
        Assert.assertEquals(token.getType(), type);
    }

}
