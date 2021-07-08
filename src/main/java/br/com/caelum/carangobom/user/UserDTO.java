package br.com.caelum.carangobom.user;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {

    private Long id;
    private String username;
    private String password;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public Long getId() {  return id; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static List<UserDTO> convert(List<User> users) {
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }
}
