package concord.classifier.routes;

import concord.classifier.processor.PrepareModelDataProcessor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by aboieriu on 4/21/17.
 */
@Component
public class PrepareTrainingRoute extends SpringRouteBuilder {

	@Value("${model.train.input.route}")
	private String photoInput;

	@Resource
	private PrepareModelDataProcessor prepareModelDataProcessor;

	@Override
	public void configure() throws Exception {
		from(photoInput)
		.bean(prepareModelDataProcessor)
		.end();
	}
}
