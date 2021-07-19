package br.com.caelum.carangobom.user;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private Long id;
    private String username;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public static List<UserDTO> convert(List<User> users) {
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public static UserDTO convertSingleUser(User user){
        return new UserDTO(user);
    }
}