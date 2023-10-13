package vn.unigap.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import vn.unigap.common.Common;

@Data
public class AuthLoginDtoIn {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public String getUsername() {
        return Common.toLowerCase(username);
    }
}
