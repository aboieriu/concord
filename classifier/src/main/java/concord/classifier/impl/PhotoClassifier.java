package concord.classifier.impl;

import com.google.common.base.Preconditions;
import concord.appdao.repository.IPhotoIndexBatchRepository;
import concord.appmodel.PhotoIndexBatch;
import concord.classifier.JmsManager;
import concord.classifier.api.IPhotoClassifier;

import java.util.List;

/**
 * Created by aboieriu on 4/18/17.
 */
public class PhotoClassifier implements IPhotoClassifier {

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
		jmsManager.sendJmsMessage(JMS_QUEUE, photoIndexBatches.get(0));
	}
}
