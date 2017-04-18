package concord.indexer.impl.internal.photo;

import com.google.common.base.Preconditions;
import concord.appdao.repository.IPhotoIndexBatchRepository;
import concord.appdao.repository.IPhotoRepository;
import concord.appmodel.Photo;
import concord.appmodel.PhotoIndexBatch;
import concord.fivepxapi.api.IFivepx;
import concord.fivepxapi.api.response.PhotoResponse;
import concord.indexer.impl.internal.photo.domain.PhotoIndexRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by aboieriu on 4/18/17.
 */
public class PhotoIndexer {

	private static Logger LOGGER = LoggerFactory.getLogger(PhotoIndexer.class);

	private IFivepx fivepx;

	private IPhotoIndexBatchRepository photoIndexBatchRepository;

	private IPhotoRepository photoRepository;

	public PhotoIndexer(IFivepx fivepx, IPhotoIndexBatchRepository iPhotoIndexBatchRepository, IPhotoRepository photoRepository) {
		this.fivepx = Preconditions.checkNotNull(fivepx, "fivepx must be initialized");
		this.photoIndexBatchRepository = Preconditions.checkNotNull(iPhotoIndexBatchRepository, "iPhotoIndexBatchRepository must be initialized");
		this.photoRepository = Preconditions.checkNotNull(photoRepository, "photoRepository must be initialized");
	}

	public PhotoIndexBatch indexPhotos(PhotoIndexRequest photoIndexRequest) {
		PhotoIndexBatch photoIndexBatch = new PhotoIndexBatch(new ArrayList<>(), new Date(), Boolean.FALSE);

		Optional<PhotoResponse> photoResponseOptional = fivepx.getPhotos(photoIndexRequest.getCategory().name(), photoIndexRequest.getFeature().name(), photoIndexRequest.getPage());

		if (photoResponseOptional.isPresent()) {
			PhotoResponse photoResponse = photoResponseOptional.get();
			List<Photo> unknownPhotos = filterOutKnownPhotos(photoResponse.getPhotoList());
			photoIndexBatch.setPhotos(unknownPhotos);
		} else {
			LOGGER.error("Something went wrong while trying to obtain 500px photos");
		}

		saveIndexBatch(photoIndexBatch);

		LOGGER.info("NEW PHOTO INDEX BATCH CREATED [ "+photoIndexBatch.getPhotos().size()+" photos added ]");

		return photoIndexBatch;
	}


	private List<Photo> filterOutKnownPhotos(List<Photo> photos){
		List<Photo> unknownPhotos = new ArrayList<Photo>();

		for (Photo photo : photos) {
			if (photoRepository.findOne(photo.getPhotoId()) == null) {
				unknownPhotos.add(photo);
			}
		}

		return unknownPhotos;
	}

	private void saveIndexBatch(PhotoIndexBatch photoIndexBatch) {
		photoIndexBatch.getPhotos().stream().forEach(this.photoRepository::save);
		// Save final details
		photoIndexBatchRepository.save(photoIndexBatch);
	}
}
