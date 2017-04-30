package concord.rest.service.impl;

import concord.appdao.repository.IClassifiedPhotoRepository;
import concord.appmodel.ClassifiedPhoto;
import concord.appmodel.domain.PageableRequest;
import concord.appmodel.domain.SocialEngagementStatus;
import concord.rest.service.api.ISocialEngagementService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by aboieriu on 4/26/17.
 */
@Component
public class SocialEngagementService implements ISocialEngagementService {

	@Resource
	private IClassifiedPhotoRepository classifiedPhotoRepository;

	@Override
	public Page<ClassifiedPhoto> getClassififedPhotos(PageableRequest pageableRequest) {
		return classifiedPhotoRepository.findClassififedAndEngagedPhotos(pageableRequest);
	}

	@Override
	public Map<String, Integer> getLastSuccessfullEngagement() {
		Page<ClassifiedPhoto> photos = classifiedPhotoRepository.findPhotosEngagedByDateAndStatus(SocialEngagementStatus.DONE, new PageableRequest(1, 100000, 0));
		return getCountsPerDay(photos);
	}

	public Map<String, Integer> getCountsPerDay(Page<ClassifiedPhoto> photos){
		Map<String, Integer> counts = new HashMap<>();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		// Initially we put 0 for today
		counts.put(df.format(new Date()), 0);
		int distinctDays = 10;

		List<ClassifiedPhoto> sortdDates =  photos.getContent().stream().sorted((d1, d2) -> d1.getEngagementDate().compareTo(d2.getEngagementDate())).collect(
						Collectors.toList());

		for (ClassifiedPhoto classifiedPhoto : sortdDates) {
			if (classifiedPhoto.getEngagementDate() != null) {
				String date = df.format(classifiedPhoto.getEngagementDate());
				if (counts.containsKey(date)) {
					counts.put(date, counts.get(date) + 1);
				} else {
					counts.put(date, 1);
				}
				if (counts.keySet().size() > distinctDays) {
					break;
				}
			}
		}

		return counts;
	}
}
