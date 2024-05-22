package vn.unigap.api.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.unigap.api.entity.jpa.User;
import vn.unigap.api.repository.jpa.user.UserRepository;
import vn.unigap.reqres.ReqresPageDtoOut;
import vn.unigap.reqres.ReqresService;
import vn.unigap.reqres.ReqresUserDtoOut;

@Service
public class SampleDataGenerationServiceImpl implements SampleDataGenerationService {
    private final ReqresService reqresService;
    private final UserRepository userRepository;

    @Autowired
    public SampleDataGenerationServiceImpl(ReqresService reqresService, UserRepository userRepository) {
        this.reqresService = reqresService;
        this.userRepository = userRepository;
    }

    @PostConstruct
    void init() {
        generate();
    }

    @Override
    public void generate() {
        int page = 1;
        int perPage = 10;
        ReqresPageDtoOut<ReqresUserDtoOut> pageDtoOut;
        do {
            pageDtoOut = reqresService.listUsers(page, perPage);
            List<ReqresUserDtoOut> users = pageDtoOut.getData();
            saveUsers(users);
            page++;
        } while (page <= pageDtoOut.getTotalPages());
    }

    private void saveUsers(List<ReqresUserDtoOut> users) {
        System.out.println(users);
        for (ReqresUserDtoOut user : users) {
            userRepository.save(User.builder().id(user.getId()).email(user.getEmail()).firstName(user.getFirstName())
                    .lastName(user.getLastName()).avatar(user.getAvatar()).build());
        }
    }
}
