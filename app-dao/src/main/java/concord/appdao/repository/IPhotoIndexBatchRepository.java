package concord.appdao.repository;

import concord.appmodel.PhotoIndexBatch;
import concord.appmodel.domain.PhotoIndexRequestSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aboieriu on 4/18/17.
 */
@Repository
public interface IPhotoIndexBatchRepository extends MongoRepository<PhotoIndexBatch, String> {
	List<PhotoIndexBatch> findByProcessed(Boolean processed);

	@Query("{'photoIndexRequest.source': ?0}")
	Page<PhotoIndexBatch> findAllBySource(PhotoIndexRequestSource source, Pageable pageable);

	@Query("{'photoIndexRequest.source': ?0, 'processed':false}")
	List<PhotoIndexBatch> findAllBySource(PhotoIndexRequestSource source);
}
