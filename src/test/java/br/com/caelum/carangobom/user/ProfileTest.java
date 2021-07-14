package br.com.caelum.carangobom.user;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
public class ProfileTest {

    @Test
    void shouldTestProfileConstructor(){
        Mockito.mock(Profile.class);

        Optional<Profile> profile = Optional.of(new Profile());
        profile.get().setId(1L);
        profile.get().setName("Test name");

        var profileId = profile.get().getId();
        var profileName = profile.get().getName();
        var profileAuth = profile.get().getAuthority();

        Assert.assertEquals(profileId.intValue(), 1L);
        Assert.assertEquals(profileName.toString(), "Test name");
        Assert.assertEquals(profileAuth.toString(), "Test name");

    }
}
