package concord.rest.controller;

import concord.appmodel.ClassificationModel;
import concord.appmodel.Photo;
import concord.appmodel.domain.PhotoCategories;
import concord.appmodel.domain.PhotoCategory;
import concord.rest.service.api.IClassificationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by aboieriu on 4/20/17.
 */
@RestController
@RequestMapping("/classification")
public class ClassificationController {

	@Resource
	private IClassificationService classificationService;

	@RequestMapping(method = RequestMethod.POST)
	public ClassificationModel createNewClassificationModel(@RequestParam("photoCategory") PhotoCategories photoCategory) {
		return classificationService.createNewClassificationModel(photoCategory);
	}

	@RequestMapping("/untrained-model")
	public ClassificationModel getUntrainedModels(){
		return classificationService.findNextUntrainedModel();
	}

	@RequestMapping("/untrained-model/{modelId}/data")
	public List<Photo> getModelData(@PathVariable("modelId") String modelId){
		return classificationService.getClassificationModelData(modelId);
	}

	@RequestMapping("/untrained-model/{modelId}/data/summary")
	public Map<PhotoCategory, Integer> getModelDataSummary(@PathVariable("modelId") String modelId){
		return classificationService.getClassificationModelDataSummary(modelId);
	}

	@RequestMapping(value = "/untrained-model/{modelId}/refresh-data", method = RequestMethod.POST)
	public void refreshData(@PathVariable("modelId") String modelId){
		classificationService.refreshClassificationModelData(modelId);
	}
}
