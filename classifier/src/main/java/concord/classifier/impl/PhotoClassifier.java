package concord.classifier.impl;

import com.google.common.base.Preconditions;
import concord.appdao.repository.IPhotoIndexBatchRepository;
import concord.appmodel.Photo;
import concord.appmodel.PhotoIndexBatch;
import concord.classifier.JmsManager;
import concord.classifier.api.IPhotoClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by aboieriu on 4/18/17.
 */
public class PhotoClassifier implements IPhotoClassifier {
	private static final Logger LOGGER = LoggerFactory.getLogger(PhotoClassifier.class);

	private IPhotoIndexBatchRepository photoIndexBatchRepository;

	private final String JMS_QUEUE = "concordPhotoInput";

	private JmsManager jmsManager;

	public PhotoClassifier(IPhotoIndexBatchRepository photoIndexBatchRepository, JmsManager jmsManager) {
		this.photoIndexBatchRepository = Preconditions.checkNotNull(photoIndexBatchRepository,"photoIndexBatchRepository must not be null");
		this.jmsManager = Preconditions.checkNotNull(jmsManager,"jmsManager must not be null");
	}

	@Override
	public void classifyPhotos() {
		List<PhotoIndexBatch> photoIndexBatches = photoIndexBatchRepository.findByProcessed(Boolean.FALSE);
		if (photoIndexBatches != null && photoIndexBatches.size() > 0) {
			for (PhotoIndexBatch photoIndexBatch : photoIndexBatches) {
				photoIndexBatch.getPhotos().stream().forEach(photo -> jmsManager.sendJmsMessage(JMS_QUEUE, photo));
				photoIndexBatch.setProcessed(Boolean.TRUE);
				photoIndexBatchRepository.save(photoIndexBatch);
				LOGGER.info("Sent events for batch " + photoIndexBatch.getId() + " -> marking as done ");
			}
		} else {
			LOGGER.info("PhotoIndexBatches is empty. No work to be done");
		}
	}
}
