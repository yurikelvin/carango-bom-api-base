package br.com.caelum.carangobom.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;

    public UserDTO(User user) {
    }

    public static List<UserDTO> toUserList(List<User> users) {
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public static UserDTO toUser(User user){
        return new UserDTO(user);
    }
}