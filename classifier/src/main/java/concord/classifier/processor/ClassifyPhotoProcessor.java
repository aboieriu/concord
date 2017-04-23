package concord.classifier.processor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import concord.appdao.repository.IClassificationModelRepository;
import concord.appdao.repository.IClassifiedPhotoRepository;
import concord.appdao.repository.IPhotoIndexBatchRepository;
import concord.appdao.repository.IPhotoRepository;
import concord.appmodel.ClassificationModel;
import concord.appmodel.ClassifiedPhoto;
import concord.appmodel.Photo;
import concord.appmodel.PhotoIndexBatch;
import concord.appmodel.domain.AiClassification;
import concord.classifier.TensorFlowClassifier;
import concord.commons.ClassifyPhotoRequest;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Created by aboieriu on 4/23/17.
 */
@Component
public class ClassifyPhotoProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClassifyPhotoProcessor.class);

	private ObjectMapper objectMapper;

	@Resource
	private PhotoDownloadProcessor photoDownloadProcessor;

	@Resource
	IPhotoRepository photoRepository;

	@Resource
	IPhotoIndexBatchRepository photoIndexBatchRepository;

	@Resource
	IClassificationModelRepository classificationModelRepository;

	@Resource
	IClassifiedPhotoRepository classifiedPhotoRepository;

	@Resource
	TensorFlowClassifier classifier;

	@PostConstruct
	public void init(){
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public void classifyPhoto(Exchange exchange){
		String message = exchange.getIn().getBody(String.class);
		ClassifyPhotoRequest request = null;
		try {
			request = objectMapper.readValue(message, ClassifyPhotoRequest.class);
			handlePhotosClassificationInternal(request);
		} catch (Exception e) {
			LOGGER.error("Something went wrong", e);
		}
	}

	private void handlePhotosClassificationInternal(ClassifyPhotoRequest request) throws Exception {
		Photo photo = photoRepository.findOne(request.getPhotoId());
		ClassificationModel model = classificationModelRepository.findOne(request.getModelId());

		if (photo != null && model != null) {
			photoDownloadProcessor.handlePhoto(photo);
			// Refresh entity
			photo = photoRepository.findOne(request.getPhotoId());
			if (photo.getLocalFilePath() != null) {
				AiClassification classification = classifier.classifyPhoto(photo, model);
				ClassifiedPhoto classifiedPhoto = classifiedPhotoRepository.findByPhotoId(photo.getPhotoId());
				if (classifiedPhoto == null) {
					classifiedPhotoRepository.save(new ClassifiedPhoto(photo, Arrays.asList(classification)));
				} else {
					if (classifiedPhoto.getAiClassification() == null) {
						classifiedPhoto.setAiClassification(Arrays.asList(classification));
					} else {
						classifiedPhoto.getAiClassification().add(classification);
					}

					classifiedPhotoRepository.save(classifiedPhoto);
				}

			}
		} else {
			LOGGER.error("Wrong photo or model id" + request.getPhotoId() + " / " + request.getModelId());
		}
	}

}
