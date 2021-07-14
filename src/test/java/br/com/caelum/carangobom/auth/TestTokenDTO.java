package br.com.caelum.carangobom.auth;

import br.com.caelum.carangobom.user.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class TestTokenDTO {

    @Test
    void testAddingValues(){
        String tokenValue = "TOKEN";
        String typeValue = "type";

        TokenDTO tokenDTO = new TokenDTO(tokenValue, typeValue);

        var token = tokenDTO.getToken();
        var type = tokenDTO.getType();


        Assert.assertEquals(token, tokenValue);
        Assert.assertEquals(type, typeValue);
    }

}
