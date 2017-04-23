package concord.classifier.processor;

import concord.appdao.repository.IClassificationModelRepository;
import concord.appmodel.ClassificationModel;
import concord.appmodel.domain.TrainingStatus;
import concord.classifier.trainer.TensorflowTrainer;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by aboieriu on 4/21/17.
 */
@Component
public class PrepareModelDataProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrepareModelDataProcessor.class);

	@Value("${photo.download.jsm.queue}")
	private String photoDownloadQueue;

	@Value("${photo.download.finish.notification.jms.queue}")
	private String photoDownloadFinishQueue;

	@Resource
	private TensorflowTrainer tensorflowTrainer;

	@Resource
	private PhotoDownloadProcessor photoDownloadProcessor;

	@Resource
	private IClassificationModelRepository classificationModelRepository;

	public void prepareModelData(Exchange exchange){
		String modelId = exchange.getIn().getBody(String.class).replaceAll("\"", "");

		LOGGER.info("Received model training request for " + modelId);

		ClassificationModel classificationModel = classificationModelRepository.findOne(modelId);

		if (!classificationModel.isTrained() && TrainingStatus.WAITING.equals(classificationModel.getTrainingStatus())) {
			classificationModel.getTrainingResults().setStartTime(new Date());
			classificationModelRepository.save(classificationModel);

			updateModelStatus(classificationModel, TrainingStatus.IN_PROGRESS);
			prepareModelData(classificationModel);
			// train model
			try {
				updateModelStatus(classificationModel, TrainingStatus.IN_TRAINING);
				tensorflowTrainer.trainModel(classificationModel);
			} catch (Exception e) {
				updateModelStatus(classificationModel, TrainingStatus.FAILED);
				LOGGER.info("Something went wrong with model training ", e);
			}

		} else {
			LOGGER.info("This model is either training or already trained. [SKIPPING] ");
		}
	}

	private void updateModelStatus(ClassificationModel classificationModel, TrainingStatus trainingStatus) {
		classificationModel.setTrainingStatus(TrainingStatus.IN_PROGRESS);
		classificationModelRepository.save(classificationModel);
	}

	private void prepareModelData(ClassificationModel model){
		model.getData().stream().forEach(photoDownloadProcessor::handlePhoto);
	}
}
