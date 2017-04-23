package concord.rest.service.api;

import java.util.List;

/**
 * Created by aboieriu on 4/19/17.
 */
public interface IPhotoService {
	void ratePhoto(String photoId, String rating);
	List<String> getPhotoCategories();
	List<String> getPhotoFeatures();
}
