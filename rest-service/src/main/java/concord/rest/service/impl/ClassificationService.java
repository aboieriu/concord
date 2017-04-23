package concord.rest.service.impl;

import concord.appdao.repository.IClassificationModelRepository;
import concord.appdao.repository.IClassifiedPhotoRepository;
import concord.appdao.repository.IPhotoIndexBatchRepository;
import concord.appmodel.ClassificationModel;
import concord.appmodel.ClassifiedPhoto;
import concord.appmodel.Photo;
import concord.appmodel.PhotoIndexBatch;
import concord.appmodel.domain.PhotoCategories;
import concord.appmodel.domain.PhotoCategory;
import concord.appmodel.domain.TrainingParameters;
import concord.appmodel.domain.TrainingResults;
import concord.appmodel.domain.TrainingStatus;
import concord.commons.ClassifyPhotoRequest;
import concord.rest.service.api.IClassificationService;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by aboieriu on 4/20/17.
 */
@Component
public class ClassificationService implements IClassificationService {

	private static final String MODEL_TRAIN_QUEUE = "modelTrain";
	private static final String CLASSIFY_PHOTO = "classifyPhotos";

	@Resource
	private IClassificationModelRepository classificationModelRepository;

	@Resource
	private IClassifiedPhotoRepository classifiedPhotoRepository;

	@Resource
	private JmsTemplate jmsTemplate;


	@Resource
	private IPhotoIndexBatchRepository photoIndexBatchRepository;

	@Override
	public ClassificationModel createNewClassificationModel(PhotoCategories photoCategories, List<String> indexBatchIds){
		ClassificationModel classificationModel = new ClassificationModel(new Date());
		classificationModel.setPhotoCategory(photoCategories);
		classificationModel.setTrainingStatus(TrainingStatus.WAITING);
		classificationModel.setData(getClassififedPhotosForModel(indexBatchIds));

		classificationModelRepository.save(classificationModel);
		return classificationModel;
	}

	@Override
	public ClassificationModel findNextUntrainedModel() {
		return classificationModelRepository.findOneByTrained(false);
	}

	@Override
	public List<Photo> getClassificationModelData(String modelId) {
		ClassificationModel classificationModel = classificationModelRepository.findOne(modelId);
		if (classificationModel != null) {
			return classificationModel.getData().stream().map(ClassifiedPhoto::getPhoto).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@Override
	public Map<PhotoCategory, Integer> getClassificationModelDataSummary(String modelId) {
		ClassificationModel classificationModel = classificationModelRepository.findOne(modelId);
		if (classificationModel != null) {
			Map<PhotoCategory, Integer> categories = new HashMap<>();

			for (ClassifiedPhoto photo : classificationModel.getData()){
				if (categories.containsKey(photo.getHumanClassification())) {
					Integer count = categories.get(photo.getHumanClassification());
					categories.put(photo.getHumanClassification(), count + 1);
				} else {
					categories.put(photo.getHumanClassification(), 1);
				}
			}
			return categories;
		}
		return new HashMap<>();
	}

	@Override
	public void refreshClassificationModelData(String modelId) {
		ClassificationModel classificationModel = classificationModelRepository.findOne(modelId);
		if (classificationModel != null && !classificationModel.isTrained()) {
			classificationModel.setData(classifiedPhotoRepository.findPhotosForModelData());
			classificationModelRepository.save(classificationModel);
		}
	}

	@Override
	public ClassificationModel getClassificationModel(String modelId){
		return classificationModelRepository.findOne(modelId);
	}

	@Override
	public List<ClassificationModel> getModels(){
		return classificationModelRepository.findAll();
	}

	@Override
	public void deleteModel(String modelId) {
		ClassificationModel classificationModel = classificationModelRepository.findOne(modelId);
		if (classificationModel != null) {
			classificationModelRepository.delete(classificationModel);
		}
	}

	@Override
	public void trainModel(String modelId, TrainingParameters trainingParameters) {
		ClassificationModel model = classificationModelRepository.findOne(modelId);
		if (model != null && !model.isTrained()) {
			model.setTrainingParameters(trainingParameters);
			classificationModelRepository.save(model);

			jmsTemplate.convertAndSend(MODEL_TRAIN_QUEUE, model.getId());
		}
	}

	@Override
	public void retrainModel(String modelId, TrainingParameters trainingParameters) {
		ClassificationModel model = classificationModelRepository.findOne(modelId);
		if (model != null) {
			model.setTrainingParameters(trainingParameters);
			model.setTrained(false);
			model.setTrainingStatus(TrainingStatus.WAITING);
			model.setTrainingResults(new TrainingResults());

			classificationModelRepository.save(model);

			jmsTemplate.convertAndSend(MODEL_TRAIN_QUEUE, model.getId());
		}
	}

	@Override
	public void classifyPhotos(String modelId, String batchId) {
		PhotoIndexBatch photoIndexBatch = photoIndexBatchRepository.findOne(batchId);
		for (Photo photo : photoIndexBatch.getPhotos()) {
			jmsTemplate.convertAndSend(CLASSIFY_PHOTO, new ClassifyPhotoRequest(modelId, photo.getPhotoId()));
		}

	}

	private List<ClassifiedPhoto> getClassififedPhotosForModel(List<String> indexBatchIds){
		List<ClassifiedPhoto> classifiedPhotos = new ArrayList<>();
		Iterable<PhotoIndexBatch> photoIndexBatches = photoIndexBatchRepository.findAll(indexBatchIds);

		for (PhotoIndexBatch photoIndexBatch : photoIndexBatches) {
			classifiedPhotos.addAll(getHumanRatedPhotos(photoIndexBatch.getPhotos()));
		}

		return classifiedPhotos;
	}

	private List<ClassifiedPhoto> getHumanRatedPhotos(List<Photo> photos) {
		List<ClassifiedPhoto> classifiedPhotos = new ArrayList<>();
		for (Photo photo : photos) {
			if (photo.getHumanRated() != null && photo.getHumanRated()) {
				classifiedPhotos.add(classifiedPhotoRepository.findByPhotoId(photo.getPhotoId()));
			}
		}
		return classifiedPhotos;
	}
}
