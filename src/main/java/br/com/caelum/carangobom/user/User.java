package br.com.caelum.carangobom.user;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Nome de usuário é um campo obrigatório")
    @Size(min = 3, max = 100, message = "O nome de usuário deve ser menor que {min} e menor que {max}")
    private String username;

    @NotBlank(message = "Senha é um campo obrigatório")
    @Size(min = 6, max = 50, message = "A senha deve ser menor que {min} e menor que {max}")
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
