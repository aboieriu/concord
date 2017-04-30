package concord.rest.controller;

import concord.appdao.repository.IClassifiedPhotoRepository;
import concord.appdao.repository.IPhotoIndexBatchRepository;
import concord.appdao.repository.IPhotoRepository;
import concord.appmodel.ClassificationModel;
import concord.appmodel.ClassifiedPhoto;
import concord.appmodel.Photo;
import concord.appmodel.PhotoIndexBatch;
import concord.appmodel.domain.PageableRequest;
import concord.appmodel.domain.PhotoCategories;
import concord.appmodel.domain.PhotoCategory;
import concord.appmodel.domain.PhotoIndexRequestSource;
import concord.appmodel.domain.TrainingParameters;
import concord.commons.SocialEngagementRequest;
import concord.rest.service.api.IClassificationService;
import concord.rest.service.api.ISocialEngagementService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by aboieriu on 4/20/17.
 */
@RestController
@RequestMapping("/classification")
public class ClassificationController {

	@Resource
	private IClassificationService classificationService;

	@Resource
	private IPhotoRepository photoRepository;

	@Resource
	private IPhotoIndexBatchRepository photoIndexBatchRepository;

	@Resource
	private IClassifiedPhotoRepository classifiedPhotoRepository;


	@RequestMapping(method = RequestMethod.POST)
	public ClassificationModel createNewClassificationModel(
					@RequestParam("photoCategory") PhotoCategories photoCategory,
					@RequestBody List<String> indexBatchIds) {
		return classificationService.createNewClassificationModel(photoCategory, indexBatchIds);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<ClassificationModel> getModels(){
		return classificationService.getModels();
	}

	@RequestMapping("/untrained-model")
	public ClassificationModel getUntrainedModels(){
		return classificationService.findNextUntrainedModel();
	}

	@RequestMapping("/model/{modelId}")
	public ClassificationModel getModel(@PathVariable("modelId") String modelId){
		return classificationService.getClassificationModel(modelId);
	}

	@RequestMapping(value = "/model/{modelId}/train", method = RequestMethod.POST)
		 public void trainModel(@PathVariable("modelId") String modelId, @RequestBody TrainingParameters trainingParameters){
		classificationService.trainModel(modelId, trainingParameters);
	}

	@RequestMapping(value = "/model/{modelId}/retrain", method = RequestMethod.POST)
	public void retrainModel(@PathVariable("modelId") String modelId, @RequestBody TrainingParameters trainingParameters){
		classificationService.retrainModel(modelId, trainingParameters);
	}

	@RequestMapping(value = "/model/{modelId}", method = RequestMethod.DELETE)
	public void deleteModel(@PathVariable("modelId") String modelId){
		classificationService.deleteModel(modelId);
	}

	@RequestMapping("/model/{modelId}/data")
	public List<Photo> getModelData(@PathVariable("modelId") String modelId){
		return classificationService.getClassificationModelData(modelId);
	}

	@RequestMapping("/model/{modelId}/data/summary")
	public Map<PhotoCategory, Integer> getModelDataSummary(@PathVariable("modelId") String modelId){
		return classificationService.getClassificationModelDataSummary(modelId);
	}

	@RequestMapping(value = "/untrained-model/{modelId}/refresh-data", method = RequestMethod.POST)
	public void refreshData(@PathVariable("modelId") String modelId){
		classificationService.refreshClassificationModelData(modelId);
	}

	@RequestMapping(value = "/classify", method = RequestMethod.POST)
	public void classifyPhoto(@RequestParam("modelId") String modelId, @RequestParam("batchId") String batchId){
		classificationService.classifyPhotos(modelId, batchId);
	}

	@RequestMapping(value = "/trash-local-data", method = RequestMethod.GET)
	public void deleteLocalPhotoData(){
		List<Photo> photos = photoRepository.findAll();

		for (Photo photo : photos) {
			photo.setLocalFilePath(null);
			photo.setDownloadFailed(false);
			photoRepository.save(photo);
		}
	}

	@RequestMapping(value = "/mass-update", method = RequestMethod.GET)
	public void massUpdate(){
		//List<PhotoIndexBatch> photoIndexBatches = photoIndexBatchRepository.findAll();
		List<ClassifiedPhoto> classifiedPhotos = classifiedPhotoRepository.findAll();

		for (ClassifiedPhoto classifiedPhoto : classifiedPhotos) {
			if (classifiedPhoto.getEngagementDate() != null) {
				classifiedPhoto.setSocialEngagedUserId("11735713");
				classifiedPhotoRepository.save(classifiedPhoto);
			}
		}

		/*for (PhotoIndexBatch photo : photoIndexBatches) {
			if (photo.getPhotoIndexRequest().getSource().equals(PhotoIndexRequestSource.SYSTEM)) {
				photo.setProcessed(true);
				photoIndexBatchRepository.save(photo);
			}
		}*/


	}
}
