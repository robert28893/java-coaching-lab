package vn.unigap.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.entity.jpa.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoOut {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;

    public static UserDtoOut from(User u) {
        return UserDtoOut.builder()
                .id(u.getId())
                .email(u.getEmail())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .avatar(u.getAvatar())
                .build();
    }
}
