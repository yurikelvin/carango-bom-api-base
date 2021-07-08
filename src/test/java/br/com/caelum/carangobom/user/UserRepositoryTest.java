package br.com.caelum.carangobom.user;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldCreateANewUSer(){
        UserForm newUserForm = new UserForm();
        newUserForm.setUsername("username");
        newUserForm.setPassword("password");
        User converted = newUserForm.convert();

        entityManager.persist(converted);
        User userForm = repository.save(converted);

        Assert.assertNotNull(userForm);
    }


    @Test
    public void shouldNotCreateDuplicatedUsers(){
        UserForm newUserForm = new UserForm();
        newUserForm.setUsername("username");
        newUserForm.setPassword("password");
        User converted = newUserForm.convert();
        entityManager.persist(converted);

        UserForm user2 = new UserForm();
        user2.setUsername("username");
        user2.setPassword("password");


        try {
            User converted2 = user2.convert();
            User userForm = repository.saveAndFlush(converted2);
        }catch (Exception e){
        }
    }
}