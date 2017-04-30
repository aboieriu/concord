package concord.rest.service.impl;

import concord.appdao.repository.IPhotoIndexBatchRepository;
import concord.appdao.repository.IPhotoRepository;
import concord.appmodel.Photo;
import concord.appmodel.PhotoIndexBatch;
import concord.appmodel.domain.PageableRequest;
import concord.appmodel.domain.PhotoIndexRequest;
import concord.appmodel.domain.PhotoIndexRequestSource;
import concord.indexer.impl.internal.photo.PhotoIndexer;
import concord.rest.domain.PhotoIndexBatchRating;
import concord.rest.service.api.IPhotoIndexService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aboieriu on 4/20/17.
 */
@Component
public class PhotoIndexService implements IPhotoIndexService {

	@Resource
	private PhotoIndexer photoIndexer;

	@Resource
	private IPhotoIndexBatchRepository photoIndexBatchRepository;

	public Page<PhotoIndexBatch> getPhotoIndexBatches(PageableRequest pageable, PhotoIndexRequestSource source) {
		return photoIndexBatchRepository.findAllBySource(source,pageable);
	}

	@Override
	public PhotoIndexBatch createPhotoIndexBatch(PhotoIndexRequest photoIndexRequest) {
		return photoIndexer.indexPhotos(photoIndexRequest);
	}

	@Override
	public Page<PhotoIndexBatchRating> getPhotoIndexBatchesForRating(PageableRequest pageable) {
		Page<PhotoIndexBatch> photoIndexBatches = getPhotoIndexBatches(pageable, PhotoIndexRequestSource.USER);

		List<PhotoIndexBatchRating> resultItems = photoIndexBatches.getContent()
						.stream().map(batch -> new PhotoIndexBatchRating(batch.getId(), batch.getPhotoIndexRequest(), batch.getDate(),
										countTotalPhotos(batch.getPhotos()), countRatedPhotos(batch.getPhotos())))
						.collect(Collectors.toList());

		return new PageImpl<>(resultItems, pageable, photoIndexBatches.getTotalElements());
	}

	public PhotoIndexBatch getPhotoIndexBatch(String batchId) {
		return photoIndexBatchRepository.findOne(batchId);
	}

	public int countRatedPhotos(List<Photo> photos) {
		if (photos == null) {
			return 0;
		}
		return photos.stream().filter(photo -> photo.getHumanRated() != null && photo.getHumanRated()).collect(Collectors.toList()).size();
	}

	public int countTotalPhotos(List<Photo> photos) {
		return  photos != null ? photos.size() : 0;
	}

	@Override
	public void deleteBatch(String batchId) {
		photoIndexBatchRepository.delete(batchId);
	}
}
