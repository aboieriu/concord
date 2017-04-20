package concord.appdao.repository;

import concord.appmodel.ClassifiedPhoto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aboieriu on 4/19/17.
 */
@Repository
public interface IClassifiedPhotoRepository extends MongoRepository<ClassifiedPhoto, String> {

	@Query("{'photo.photoId': ?0 }")
	ClassifiedPhoto findByPhotoId(String photoId);

	@Query("{'humanClassification': {$exists: true}, 'aiClassification':{$exists: false}}")
	List<ClassifiedPhoto> findPhotosForModelData();
}
