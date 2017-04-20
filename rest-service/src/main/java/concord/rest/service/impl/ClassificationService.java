package concord.rest.service.impl;

import concord.appdao.repository.IClassificationModelRepository;
import concord.appdao.repository.IClassifiedPhotoRepository;
import concord.appdao.repository.IPhotoRepository;
import concord.appmodel.ClassificationModel;
import concord.appmodel.ClassifiedPhoto;
import concord.appmodel.Photo;
import concord.appmodel.domain.PhotoCategories;
import concord.appmodel.domain.PhotoCategory;
import concord.rest.service.api.IClassificationService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

	@Resource
	private IClassificationModelRepository classificationModelRepository;

	@Resource
	private IClassifiedPhotoRepository classifiedPhotoRepository;

	@Resource
	private IPhotoRepository photoRepository;

	@Override
	public ClassificationModel createNewClassificationModel(PhotoCategories photoCategories){
		ClassificationModel classificationModel = new ClassificationModel(new Date());
		classificationModel.setPhotoCategory(photoCategories);
		classificationModel.setData(classifiedPhotoRepository.findPhotosForModelData());
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
}
