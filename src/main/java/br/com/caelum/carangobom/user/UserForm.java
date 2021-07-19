package br.com.caelum.carangobom.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UserForm {

    @NotEmpty(message = "Usuário é um campo obrigatório")
    @NotNull
    @Size(min = 3, message = "O nome de usuário deve ser maior que {min}")
    private String username;

    @NotEmpty(message = "A senha é um campo obrigatório")
    @NotNull
    @Size(min = 3, max = 50, message = "A senha deve ser maior que {min} e menor que {max}")
    private String password;

    public UserForm(User user) {
    }

    public User toUser(){
        return new User(username, password);
    }
}
