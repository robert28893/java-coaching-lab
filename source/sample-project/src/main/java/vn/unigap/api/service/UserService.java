package vn.unigap.api.service;

import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.UpdateUserDtoIn;
import vn.unigap.api.dto.in.UserDtoIn;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.UserDtoOut;

public interface UserService {
    PageDtoOut<UserDtoOut> list(PageDtoIn pageDtoIn);

    UserDtoOut get(Long id);

    UserDtoOut create(UserDtoIn userDtoIn);

    UserDtoOut update(Long id, UpdateUserDtoIn updateUserDtoIn);

    void delete(Long id);
}
