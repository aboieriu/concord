package concord.scheduler.job;

import concord.appdao.repository.IClassifiedPhotoRepository;
import concord.appmodel.ClassifiedPhoto;
import concord.appmodel.domain.PhotoCategory;
import concord.appmodel.domain.SocialEngagementStatus;
import concord.commons.JmsManager;
import concord.commons.SocialEngagementRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by aboieriu on 4/23/17.
 */
public class SocialEngagementJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClassificationJob.class);

	@Resource
	private IClassifiedPhotoRepository classifiedPhotoRepository;

	private String JMS_SOCIAL_ENGAGEMENT_ROUTE = "socialEngage";

	private String AlexUsrId = "11735713";

	private int lowLimit = 5;

	private int highLimit = 15;

	@Resource
	JmsManager jmsManager;

	public void executeInternal() throws Exception {
		LOGGER.info("Running social engagement job...");

		List<ClassifiedPhoto> photoList = classifiedPhotoRepository.findClassififedPhotosNotEngaged();

		Map<Long, List<ClassifiedPhoto>> grouppedPhotos = groupPhotos(photoList);

		// Alex user
		handleSocialEngagement(grouppedPhotos, JMS_SOCIAL_ENGAGEMENT_ROUTE, AlexUsrId);
	}

	private void handleSocialEngagement(Map<Long, List<ClassifiedPhoto>> grouppedPhotos, String route, String userId){
		for (List<ClassifiedPhoto> groupPhotoItems : grouppedPhotos.values()) {
			int count = 0;
			Random r = new Random();
			int limitForCategory = r.nextInt(highLimit-lowLimit) + lowLimit;
			for (ClassifiedPhoto classifiedPhoto : groupPhotoItems) {
				if (classifiedPhoto.getEngagementStatus() != null && !SocialEngagementStatus.WAITING
								.equals(classifiedPhoto.getEngagementStatus())) {
					LOGGER.info("Skiping photo " + classifiedPhoto.getPhoto().getPhotoId()
									+ " -> social engagement was already requested");
				} else {
					if (classifiedPhoto.getAiClassification().get(0).getHighestScore().getLevel() > PhotoCategory.STAGE2.getLevel()) {
						LOGGER.info("send request for " + classifiedPhoto);
						SocialEngagementRequest socialEngagementRequest = new SocialEngagementRequest(true, null,
										classifiedPhoto.getPhoto().getPhotoId());

						socialEngagementRequest.setUserId(userId);

						jmsManager.sendJmsMessage(route, socialEngagementRequest);

						classifiedPhoto.setEngagementStatus(SocialEngagementStatus.REQUESTED);
						classifiedPhoto.setEngagementDate(new Date());
						classifiedPhotoRepository.save(classifiedPhoto);

						count++;

						if (count == limitForCategory) {
							LOGGER.info("Breaking up social engagement for this photo category. Limit has been reached [" + limitForCategory + "]");
							break;
						}

					} else {
						LOGGER.info("Skiping photo " + classifiedPhoto.getPhoto().getPhotoId() + " -> classified lower than minimum level");
						classifiedPhoto.setEngagementStatus(SocialEngagementStatus.REJECTED);
						classifiedPhoto.setEngagementDate(new Date());
						classifiedPhotoRepository.save(classifiedPhoto);
					}
				}
			}
		}
	}


	public Map<Long, List<ClassifiedPhoto>> groupPhotos(List<ClassifiedPhoto> classifiedPhotos){
		Map<Long, List<ClassifiedPhoto>> result = new HashMap<>();
		for (ClassifiedPhoto classifiedPhoto: classifiedPhotos) {
			if (result.containsKey(classifiedPhoto.getPhoto().getCategory())) {
				result.get(classifiedPhoto.getPhoto().getCategory()).add(classifiedPhoto);
			} else {
				List<ClassifiedPhoto> itemList = new ArrayList<>();
				itemList.add(classifiedPhoto);
				result.put(classifiedPhoto.getPhoto().getCategory(), itemList);
			}
		}

		return result;
	}
}
