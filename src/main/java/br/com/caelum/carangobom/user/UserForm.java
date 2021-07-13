package br.com.caelum.carangobom.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserForm {

    @NotEmpty(message = "Usuário é um campo obrigatório")
    @NotNull
    @Size(min = 3, message = "O nome de usuário deve ser maior que {min}")
    private String username;

    @NotEmpty(message = "A senha é um campo obrigatório")
    @NotNull
    @Size(min = 3, max = 50, message = "A senha deve ser maior que {min} e menor que {max}")
    private String password;

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

    public UserForm() {
    }

    public UserForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User convert(){
        return new User(username, password);
    }
}
