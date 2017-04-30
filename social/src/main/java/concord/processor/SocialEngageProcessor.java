package concord.processor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import concord.appdao.repository.IClassifiedPhotoRepository;
import concord.appmodel.ClassifiedPhoto;
import concord.appmodel.domain.SocialEngagementStatus;
import concord.commons.SocialEngagementRequest;
import concord.fivepxapi.client.FivepxWebClient;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by aboieriu on 4/23/17.
 */
@Component
public class SocialEngageProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(SocialEngageProcessor.class);

	@Resource
	private IClassifiedPhotoRepository classifiedPhotoRepository;

	private FivepxWebClient client;
	private ObjectMapper objectMapper;

	@PostConstruct
	public void init() throws Exception {
		client = new FivepxWebClient();
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public void engage(Exchange exchange) {
		String message = exchange.getIn().getBody(String.class);
		SocialEngagementRequest request = null;
		try {
			request = objectMapper.readValue(message, SocialEngagementRequest.class);
			handleEngagementInternal(request);
		} catch (Exception e) {
			LOGGER.error("Something went wrong", e);
		}
	}

	private void handleEngagementInternal(SocialEngagementRequest request) {
		ClassifiedPhoto classifiedPhoto = classifiedPhotoRepository.findByPhotoId(request.getPhotoId());
		if (classifiedPhoto != null) {
			try {
				client.engage(request);
				classifiedPhoto.setEngagementStatus(SocialEngagementStatus.DONE);
			} catch (Exception e) {
				LOGGER.error("Unable to fulfill social engagement request", e);
				classifiedPhoto.setEngagementStatus(SocialEngagementStatus.FAILED);
			}
			classifiedPhoto.setEngagementDate(new Date());
			classifiedPhoto.setSocialEngagedUserId(request.getUserId());
			classifiedPhotoRepository.save(classifiedPhoto);
		} else {
			LOGGER.error("Unable to find classified photo " + request.getPhotoId());
		}
	}

}
