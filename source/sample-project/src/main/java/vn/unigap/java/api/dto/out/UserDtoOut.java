package vn.unigap.java.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoOut {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
}
