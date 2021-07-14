package br.com.caelum.carangobom.user;

import java.util.List;
import java.util.stream.Collectors;

public class UserWithoutPasswordDTO {
    private Long id;
    private String username;

    public UserWithoutPasswordDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public static List<UserWithoutPasswordDTO> convert(List<User> users) {
        return users.stream().map(UserWithoutPasswordDTO::new).collect(Collectors.toList());
    }
}