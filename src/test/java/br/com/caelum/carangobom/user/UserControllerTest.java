package br.com.caelum.carangobom.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.net.URI;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;

    @Autowired
    private EntityManager entityManager;

    public void shouldReturnTheListOfUser() throws Exception {
        URI uri = new URI("/users");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Transactional
    @Test
    public void shouldNotCreateANewUserWithTheSameUsername() throws Exception {
        URI uri = new URI("/users");

        UserForm newUserForm = new UserForm("username", "password");
        User converted = newUserForm.convert();
        entityManager.persist(converted);
        entityManager.flush();

        String json = "{\"username\": \"username\", \"password\": \"newpassword\"}";
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void shouldReturnErrorWhenDoesNotHaveUsername() throws Exception {
        URI uri = new URI("/users");

        String json = "{\"username\": \"\", \"password\": \"newpassword\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void shouldReturnErrorWhenDoesNotHavePassword() throws Exception {
        URI uri = new URI("/users");

        String json = "{\"username\": \"username\", \"password\": \"\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void shouldReturnTheListOfUsers() throws Exception {

        URI uri = new URI("/users");
        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Transactional
    @Test
    public void shouldFindUserById() throws Exception {
        UserForm newUserForm = new UserForm("username", "password");
        User converted = newUserForm.convert();
        entityManager.persist(converted);
        entityManager.flush();

        URI uri = new URI("/users/" + converted.getId());

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}
