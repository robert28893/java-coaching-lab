package vn.unigap.java.api.service;

import vn.unigap.java.api.dto.in.AuthLoginDtoIn;
import vn.unigap.java.api.dto.out.AuthLoginDtoOut;

public interface AuthService {
    AuthLoginDtoOut login(AuthLoginDtoIn loginDtoIn);
}
