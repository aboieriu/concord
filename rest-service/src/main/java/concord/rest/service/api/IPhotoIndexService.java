package concord.rest.service.api;

import concord.appmodel.PhotoIndexBatch;
import concord.appmodel.domain.PageableRequest;
import concord.appmodel.domain.PhotoIndexRequest;
import concord.appmodel.domain.PhotoIndexRequestSource;
import concord.rest.domain.PhotoIndexBatchRating;
import org.springframework.data.domain.Page;

/**
 * Created by aboieriu on 4/20/17.
 */
public interface IPhotoIndexService {
	Page<PhotoIndexBatch> getPhotoIndexBatches(PageableRequest pageable, PhotoIndexRequestSource source);
	Page<PhotoIndexBatchRating> getPhotoIndexBatchesForRating(PageableRequest pageable);
	PhotoIndexBatch createPhotoIndexBatch(PhotoIndexRequest photoIndexRequest);
	void deleteBatch(String batchId);
	PhotoIndexBatch getPhotoIndexBatch(String batchId);
}
