package concord.appdao.repository;

import concord.appmodel.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by aboieriu on 4/18/17.
 */
@Repository
public interface IUserRepository extends MongoRepository<User, String> {
	User findByUserId(Long userId);
}
