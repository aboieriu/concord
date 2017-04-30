package concord.rest.service.api;

import concord.appmodel.ClassifiedPhoto;
import concord.appmodel.domain.PageableRequest;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * Created by aboieriu on 4/26/17.
 */
public interface ISocialEngagementService {
	Page<ClassifiedPhoto> getClassififedPhotos(PageableRequest pageableRequest);
	Map<String, Integer> getLastSuccessfullEngagement();
}
