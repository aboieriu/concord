package concord.rest.controller;

import concord.appmodel.ClassifiedPhoto;
import concord.appmodel.domain.PageableRequest;
import concord.rest.service.api.ISocialEngagementService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by aboieriu on 4/26/17.
 */
@RestController
@RequestMapping("/social")
public class SocialEngagementController {

	@Resource
	private ISocialEngagementService socialEngagementService;

	@RequestMapping("/all")
	public Page<ClassifiedPhoto> getClassififedPhotos(@RequestParam(required = false) Integer page,
					@RequestParam(required = false) Integer pageSize,
					@RequestParam(required = false) Integer offset){
		return socialEngagementService.getClassififedPhotos(new PageableRequest(page, pageSize, offset));
	}

	@RequestMapping("/successful-summary")
	public Map<String, Integer> getLastSuccesfull(){
		return socialEngagementService.getLastSuccessfullEngagement();
	}
}
