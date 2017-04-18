package concord.appdao.repository;

import concord.appmodel.PhotoIndexBatch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by aboieriu on 4/18/17.
 */
@Repository
public interface IPhotoIndexBatchRepository extends MongoRepository<PhotoIndexBatch, String> {
}
