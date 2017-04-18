package concord.appdao.repository;

import concord.appmodel.PhotoIndexBatch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aboieriu on 4/18/17.
 */
@Repository
public interface IPhotoIndexBatchRepository extends MongoRepository<PhotoIndexBatch, String> {
	List<PhotoIndexBatch> findByProcessed(Boolean processed);
}
