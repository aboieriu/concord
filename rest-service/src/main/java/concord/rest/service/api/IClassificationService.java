package concord.rest.service.api;

import concord.appmodel.ClassificationModel;
import concord.appmodel.Photo;
import concord.appmodel.domain.PhotoCategories;
import concord.appmodel.domain.PhotoCategory;

import java.util.List;
import java.util.Map;

/**
 * Created by aboieriu on 4/20/17.
 */
public interface IClassificationService {
	ClassificationModel createNewClassificationModel(PhotoCategories photoCategories);
	ClassificationModel findNextUntrainedModel();
	List<Photo> getClassificationModelData(String modelId);
	Map<PhotoCategory, Integer> getClassificationModelDataSummary(String modelId);
	void refreshClassificationModelData(String modelId);
}
