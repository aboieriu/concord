package concord.rest.controller;

import concord.rest.service.api.IPhotoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by aboieriu on 4/19/17.
 */
@RestController
@RequestMapping("/photos")
public class PhotoServiceController {

	@Resource
	private IPhotoService photoService;

	@RequestMapping("/rate/{photoId}")
	public void updatePhotoRating(@PathVariable("photoId") String photoId, @RequestParam("rating") String rating) {
		photoService.ratePhoto(photoId, rating);
	}

	@RequestMapping("/categories")
	public List<String> getPohotoCategories() {
		return photoService.getPhotoCategories();
	}

	@RequestMapping("/features")
	public List<String> getPohotoFeatures() {
		return photoService.getPhotoFeatures();
	}
}
