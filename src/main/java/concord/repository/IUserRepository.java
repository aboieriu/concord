package concord.repository;

import concord.entity.EUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * Created by aboieriu on 9/13/16.
 */
@Component
public interface IUserRepository extends MongoRepository<EUser, String> {

    EUser findById(String id);

    EUser findByEmail(String email);

}
