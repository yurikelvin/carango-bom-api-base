package br.com.caelum.carangobom.user;

import org.junit.Assert;
import org.junit.Test;

public class UserEntityTest {

    @Test
    public void createNewUserWithConstructorWithIdPasswordAndUsername(){
        String username = "user";
        String password = "password";
        Long id = 1L;

        User user = new User(id, username, password);

        Assert.assertTrue(user.isEnabled());
        Assert.assertTrue(user.isAccountNonExpired());
        Assert.assertTrue(user.isAccountNonLocked());
        Assert.assertTrue(user.isCredentialsNonExpired());
        Assert.assertEquals(user.getUsername(), username);
        Assert.assertEquals(user.getPassword(), password);
        Assert.assertEquals(user.getId(), id);
    }

    @Test
    public void creatingWithConstructorUserAndPassword(){
        String username = "user";
        String password = "password";
        User user = new User(username, password);
        Assert.assertEquals(user.getUsername(), username);
        Assert.assertEquals(user.getPassword(), password);
    }
}
