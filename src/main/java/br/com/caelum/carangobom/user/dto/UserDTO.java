package br.com.caelum.carangobom.user.dto;

import br.com.caelum.carangobom.user.model.User;

public class UserDTO {

    private String username;
    private String password;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
