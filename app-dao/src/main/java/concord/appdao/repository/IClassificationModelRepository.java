package concord.appdao.repository;

import concord.appmodel.ClassificationModel;
import concord.appmodel.domain.PhotoCategories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aboieriu on 4/19/17.
 */
@Repository
public interface IClassificationModelRepository extends MongoRepository<ClassificationModel, String> {
	ClassificationModel findOneByTrained(Boolean trained);

	@Query("{'photoCategory' : ?0 , 'trained':true}")
	List<ClassificationModel> findSuitableModelsForCategory(PhotoCategories category);
}
