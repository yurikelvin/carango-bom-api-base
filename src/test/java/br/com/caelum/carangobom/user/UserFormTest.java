package br.com.caelum.carangobom.user;

import org.junit.Assert;
import org.junit.Test;

public class UserFormTest {
    @Test
    public void testingWithConstructor(){
        UserForm form = new UserForm("username", "password");
        Assert.assertEquals(form.getUsername(), "username");
        Assert.assertEquals(form.getPassword(), "password");
    }
}
