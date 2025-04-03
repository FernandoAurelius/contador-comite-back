package br.com.floresdev.contador_comite_back.domain.user.dto;

import br.com.floresdev.contador_comite_back.domain.user.User;
import br.com.floresdev.contador_comite_back.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String name;
    private String email;
    private String role;

    public User toEntity() {
        var user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setRole(UserRole.fromString(role));

        return user;
    }

    public static UserDTO fromEntity(User user) {
        return new UserDTO(
            user.getName(),
            user.getEmail(),
            user.getRole().toString()
        );
    }
}
