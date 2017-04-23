package concord.rest.service.api;

import concord.appmodel.ClassificationModel;
import concord.appmodel.Photo;
import concord.appmodel.domain.PhotoCategories;
import concord.appmodel.domain.PhotoCategory;
import concord.appmodel.domain.TrainingParameters;

import java.util.List;
import java.util.Map;

/**
 * Created by aboieriu on 4/20/17.
 */
public interface IClassificationService {
	ClassificationModel createNewClassificationModel(PhotoCategories photoCategories, List<String> indexBatchIds);
	ClassificationModel findNextUntrainedModel();
	ClassificationModel getClassificationModel(String modelId);
	void deleteModel(String modelId);
	List<ClassificationModel> getModels();
	List<Photo> getClassificationModelData(String modelId);
	Map<PhotoCategory, Integer> getClassificationModelDataSummary(String modelId);
	void refreshClassificationModelData(String modelId);
	void trainModel(String modelId, TrainingParameters trainingParameters);
	void retrainModel(String modelId, TrainingParameters trainingParameters);
	void classifyPhotos(String modelId, String batchId);
}
