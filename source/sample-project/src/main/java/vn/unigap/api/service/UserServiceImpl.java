package vn.unigap.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.UpdateUserDtoIn;
import vn.unigap.api.dto.in.UserDtoIn;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.UserDtoOut;
import vn.unigap.api.entity.jpa.User;
import vn.unigap.api.repository.jpa.user.UserRepository;
import vn.unigap.common.Common;
import vn.unigap.common.errorcode.ErrorCode;
import vn.unigap.common.exception.ApiException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PageDtoOut<UserDtoOut> list(PageDtoIn pageDtoIn) {
        Page<User> users = this.userRepository
                .findAll(PageRequest.of(pageDtoIn.getPage() - 1, pageDtoIn.getPageSize(), Sort.by("id").ascending()));
        return PageDtoOut.from(pageDtoIn.getPage(), pageDtoIn.getPageSize(), users.getTotalElements(),
                users.stream().map(UserDtoOut::from).toList());
    }

    @Override
    public UserDtoOut get(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "user not found"));
        return UserDtoOut.from(user);
    }

    @Override
    public UserDtoOut create(UserDtoIn userDtoIn) {
        userRepository.findByEmail(userDtoIn.getEmail()).ifPresent(user -> {
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "email already existed");
        });
        User user = userRepository.save(User.builder().email(userDtoIn.getEmail()).firstName(userDtoIn.getFirstName())
                .lastName(userDtoIn.getLastName()).build());

        return UserDtoOut.from(user);
    }

    @Override
    public UserDtoOut update(Long id, UpdateUserDtoIn updateUserDtoIn) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "user not found"));

        user.setFirstName(updateUserDtoIn.getFirstName());
        user.setLastName(updateUserDtoIn.getLastName());
        user.setUpdatedDate(Common.currentTime());
        user = userRepository.save(user);

        return UserDtoOut.from(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "user not found"));

        userRepository.delete(user);
    }
}
