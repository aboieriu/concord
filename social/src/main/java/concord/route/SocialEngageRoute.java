package concord.route;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import concord.processor.SocialEngageProcessor;

import javax.annotation.Resource;

/**
 * Created by aboieriu on 4/23/17.
 */
@Component
public class SocialEngageRoute extends SpringRouteBuilder {

	@Value("${social.engagement.route}")
	private String socialEngageRoute;

	@Resource
	private SocialEngageProcessor socialEngageProcessor;

	@Override
	public void configure() throws Exception {
		from(socialEngageRoute)
		.bean(socialEngageProcessor)
		.end();
	}
}
