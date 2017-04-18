package concord.scheduler.job;

import concord.classifier.api.IPhotoClassifier;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by aboieriu on 4/18/17.
 */
public class ClassificationJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassificationJob.class);

	@Resource
	private IPhotoClassifier photoClassifier;

	public void executeInternal() throws JobExecutionException {
		LOGGER.info("Running classification job...");
		photoClassifier.classifyPhotos();
	}
}
