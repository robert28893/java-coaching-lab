package vn.unigap.reqres;

public interface ReqresService {
    ReqresPageDtoOut<ReqresUserDtoOut> listUsers(int page, int perPage);
}
