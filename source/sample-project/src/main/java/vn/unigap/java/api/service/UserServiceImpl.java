package vn.unigap.java.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.unigap.java.api.dto.in.PageDtoIn;
import vn.unigap.java.api.dto.in.UpdateUserDtoIn;
import vn.unigap.java.api.dto.in.UserDtoIn;
import vn.unigap.java.api.dto.out.PageDtoOut;
import vn.unigap.java.api.dto.out.UserDtoOut;
import vn.unigap.java.api.repository.user.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(
			UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public PageDtoOut<UserDtoOut> list(PageDtoIn pageDtoIn) {
		return null;
	}

	@Override
	public UserDtoOut get(Integer id) {
		return null;
	}

	@Override
	public UserDtoOut create(UserDtoIn userDtoIn) {
		return null;
	}

	@Override
	public UserDtoOut update(Integer id, UpdateUserDtoIn updateUserDtoIn) {
		return null;
	}

	@Override
	public void delete(Integer id) {

	}
}
