package concord.appdao.repository;

import concord.appmodel.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aboieriu on 4/18/17.
 */
@Repository
public interface IPhotoRepository extends MongoRepository<Photo, String> {

	@Query("{$or: [{ 'humanRated' : false }, { 'humanRated' : null }]}")
	List<Photo> findPhotosForRating();

	List<Photo> findByHumanRated(Boolean humanRated);
	List<Photo> findByAiRated(Boolean aiRated);
}
