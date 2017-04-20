package concord.appdao.repository;

import concord.appmodel.ClassificationModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by aboieriu on 4/19/17.
 */
@Repository
public interface IClassificationModelRepository extends MongoRepository<ClassificationModel, String> {
	ClassificationModel findOneByTrained(Boolean trained);
}
