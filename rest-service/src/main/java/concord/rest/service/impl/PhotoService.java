package concord.rest.service.impl;

import concord.appdao.repository.IClassifiedPhotoRepository;
import concord.appdao.repository.IPhotoRepository;
import concord.appmodel.ClassifiedPhoto;
import concord.appmodel.Photo;
import concord.appmodel.domain.PhotoCategories;
import concord.appmodel.domain.PhotoCategory;
import concord.appmodel.domain.PhotoFeature;
import concord.rest.service.api.IPhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aboieriu on 4/19/17.
 */
@Component
public class PhotoService implements IPhotoService {

	private static Logger LOGGER = LoggerFactory.getLogger(PhotoService.class);

	@Resource
	private IPhotoRepository photoRepository;

	@Resource
	private IClassifiedPhotoRepository classifiedPhotoRepository;

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

	public List<String> getPhotoCategories() {
		return Arrays.asList(PhotoCategories.values()).stream().map(item -> item.name()).collect(Collectors.toList());
	}

	public List<String> getPhotoFeatures() {
		return Arrays.asList(PhotoFeature.values()).stream().map(item -> item.name()).collect(Collectors.toList());
	}


}
