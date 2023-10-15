package vn.unigap.api.repository.mongodb.reqres;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.mongodb.RequestResponse;

@Repository
public interface RequestResponseRepository extends MongoRepository<RequestResponse, String> {
}
