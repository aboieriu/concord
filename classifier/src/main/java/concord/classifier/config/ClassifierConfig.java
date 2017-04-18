package concord.classifier.config;

import concord.appdao.repository.IPhotoIndexBatchRepository;
import concord.classifier.JmsManager;
import concord.classifier.impl.PhotoClassifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.Resource;

/**
 * Created by aboieriu on 4/18/17.
 */
@Configuration
@PropertySource("classpath:/META-INF/routes.properties")
public class ClassifierConfig {

	@Resource
	private IPhotoIndexBatchRepository photoIndexBatchRepository;

	@Resource
	private JmsManager jmsManager;

	@Bean
	public PhotoClassifier getPhotoClassifier(){
		return new PhotoClassifier(photoIndexBatchRepository, jmsManager);
	}
}
