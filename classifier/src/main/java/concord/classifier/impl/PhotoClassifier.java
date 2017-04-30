package concord.classifier.impl;

import com.google.common.base.Preconditions;
import concord.appdao.repository.IClassificationModelRepository;
import concord.appdao.repository.IPhotoIndexBatchRepository;
import concord.appmodel.ClassificationModel;
import concord.appmodel.PhotoIndexBatch;
import concord.appmodel.domain.PageableRequest;
import concord.appmodel.domain.PhotoCategories;
import concord.appmodel.domain.PhotoIndexRequestSource;
import concord.commons.ClassifyPhotoRequest;
import concord.commons.JmsManager;
import concord.classifier.api.IPhotoClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by aboieriu on 4/18/17.
 */
public class PhotoClassifier implements IPhotoClassifier {
	private static final Logger LOGGER = LoggerFactory.getLogger(PhotoClassifier.class);

	private IPhotoIndexBatchRepository photoIndexBatchRepository;

	private IClassificationModelRepository classificationModelRepository;

	private static final String CLASSIFY_PHOTO = "classifyPhotos";

	private JmsManager jmsManager;

	public PhotoClassifier(IPhotoIndexBatchRepository photoIndexBatchRepository, JmsManager jmsManager, IClassificationModelRepository classificationModelRepository) {
		this.photoIndexBatchRepository = Preconditions.checkNotNull(photoIndexBatchRepository,"photoIndexBatchRepository must not be null");
		this.jmsManager = Preconditions.checkNotNull(jmsManager,"jmsManager must not be null");
		this.classificationModelRepository = Preconditions.checkNotNull(classificationModelRepository,"classificationModelRepository must not be null");
	}

	@Override
	public void classifyPhotos() {
		List<PhotoIndexBatch> photoIndexBatches = photoIndexBatchRepository.findAllBySource(
						PhotoIndexRequestSource.SYSTEM);
		if (photoIndexBatches != null && photoIndexBatches.size() > 0) {
			for (PhotoIndexBatch photoIndexBatch : photoIndexBatches) {
				List<ClassificationModel> models = classificationModelRepository.findSuitableModelsForCategory(photoIndexBatch.getPhotoIndexRequest().getCategory());

				if (models.size() > 0) {

					ClassificationModel model = pickOne(models);

					photoIndexBatch.getPhotos().stream().forEach(photo -> {
						jmsManager.sendJmsMessage(CLASSIFY_PHOTO, new ClassifyPhotoRequest(model.getId(), photo.getPhotoId()));
					});
					photoIndexBatch.setProcessed(Boolean.TRUE);
					photoIndexBatchRepository.save(photoIndexBatch);
					LOGGER.info("Sent events for batch " + photoIndexBatch.getId() + " -> marking as done ");
				} else {
					LOGGER.error("Unable to find a suitable classification model for batch " + photoIndexBatch.getId() + " [category -> " + photoIndexBatch.getPhotoIndexRequest().getCategory() + "]");
				}
			}
		} else {
			LOGGER.info("PhotoIndexBatches is empty. No work to be done");
		}
	}

	private ClassificationModel pickOne(List<ClassificationModel> classificationModels) {
		return classificationModels
						.stream()
						.sorted((e1, e2) -> e1.getDate()
										.compareTo(e2.getDate())).findFirst().get();
	}
}
