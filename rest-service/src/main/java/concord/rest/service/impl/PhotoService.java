package concord.rest.service.impl;

import concord.appdao.repository.IClassifiedPhotoRepository;
import concord.appdao.repository.IPhotoRepository;
import concord.appmodel.ClassifiedPhoto;
import concord.appmodel.Photo;
import concord.appmodel.domain.PhotoCategory;
import concord.rest.service.api.IPhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by aboieriu on 4/19/17.
 */
@Component
public class PhotoService implements IPhotoService {

	private static Logger LOGGER = LoggerFactory.getLogger(PhotoService.class);

	private static final int MAX_PHOTOS_FOR_HUMAN_RATING = 100;

	@Resource
	private IPhotoRepository photoRepository;

	@Resource
	private IClassifiedPhotoRepository classifiedPhotoRepository;

	@Override
	public List<Photo> getPhotosToRate() {

		List<Photo> photosToRate = photoRepository.findPhotosForRating();
		if (photosToRate.size() > MAX_PHOTOS_FOR_HUMAN_RATING) {
			return photosToRate.subList(0, MAX_PHOTOS_FOR_HUMAN_RATING);
		}
		return photosToRate;
	}

	@Override
	public void ratePhoto(String photoId, String rating) {
		Photo photo = photoRepository.findOne(photoId);
		if (photo != null) {
			ClassifiedPhoto classifiedPhoto = classifiedPhotoRepository.findByPhotoId(photoId);
			if (classifiedPhoto != null) {
				classifiedPhoto.setHumanClassification(PhotoCategory.buildByRating(rating));
			} else {
				classifiedPhoto = new ClassifiedPhoto(photo, PhotoCategory.buildByRating(rating));
			}
			classifiedPhotoRepository.save(classifiedPhoto);
			photo.setHumanRated(Boolean.TRUE);
			photoRepository.save(photo);
		}
	}


}
