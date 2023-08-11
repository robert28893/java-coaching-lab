package vn.unigap.java.reqres;

public interface ReqresService {
    ReqresPageDtoOut<ReqresUserDtoOut> listUsers(int page, int perPage);
}
