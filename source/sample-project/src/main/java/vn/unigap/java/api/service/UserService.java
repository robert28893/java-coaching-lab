package vn.unigap.java.api.service;

import vn.unigap.java.api.dto.in.PageDtoIn;
import vn.unigap.java.api.dto.in.UpdateUserDtoIn;
import vn.unigap.java.api.dto.in.UserDtoIn;
import vn.unigap.java.api.dto.out.PageDtoOut;
import vn.unigap.java.api.dto.out.UserDtoOut;

public interface UserService {
    PageDtoOut<UserDtoOut> list(PageDtoIn pageDtoIn);

    UserDtoOut get(Long id);

    UserDtoOut create(UserDtoIn userDtoIn);

    UserDtoOut update(Long id, UpdateUserDtoIn updateUserDtoIn);

    void delete(Long id);
}
