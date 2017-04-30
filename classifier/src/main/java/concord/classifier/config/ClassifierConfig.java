package concord.classifier.config;

import concord.appdao.repository.IClassificationModelRepository;
import concord.appdao.repository.IPhotoIndexBatchRepository;
import concord.commons.JmsManager;
import concord.classifier.impl.PhotoClassifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.Resource;

/**
 * Created by aboieriu on 4/18/17.
 */
@Configuration
@PropertySource("file:///${CONCORD_LIB}/classifier/routes.properties")
public class ClassifierConfig {

	@Resource
	private IPhotoIndexBatchRepository photoIndexBatchRepository;

	@Resource
	private IClassificationModelRepository classificationModelRepository;

	@Resource
	private JmsManager jmsManager;

	@Bean
	public PhotoClassifier getPhotoClassifier(){
		return new PhotoClassifier(photoIndexBatchRepository, jmsManager, classificationModelRepository);
	}
}
