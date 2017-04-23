package concord.classifier.routes;

import concord.classifier.processor.ClassifyPhotoProcessor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by aboieriu on 4/23/17.
 */
@Component
public class ClassifyPhotoRoute extends SpringRouteBuilder {

	@Value("${model.classify.input.route}")
	private String classifyInput;

	@Resource
	private ClassifyPhotoProcessor classifyPhotoProcessor;

	@Override
	public void configure() throws Exception {
		from(classifyInput)
		.bean(classifyPhotoProcessor)
		.end();
	}
}
