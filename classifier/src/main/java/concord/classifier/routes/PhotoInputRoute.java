package concord.classifier.routes;

import concord.classifier.processor.PhotoProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by aboieriu on 4/18/17.
 */
@Component
public class PhotoInputRoute extends SpringRouteBuilder {
	private static final Logger LOGGER = LoggerFactory.getLogger(PhotoInputRoute.class);

	@Value("${photo.input.route}")
	private String photoInput;

	@Resource
	private PhotoProcessor photoProcessor;

	@Override
	public void configure() throws Exception {
		from(photoInput)
		.bean(photoProcessor)
		.end();
	}
}
