package concord.rest.service.api;

import concord.appmodel.Photo;

import java.util.List;

/**
 * Created by aboieriu on 4/19/17.
 */
public interface IPhotoService {
	List<Photo> getPhotosToRate();
	void ratePhoto(String photoId, String rating);
}
