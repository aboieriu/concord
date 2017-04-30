package concord.rest.controller;

import concord.appmodel.PhotoIndexBatch;
import concord.appmodel.domain.PageableRequest;
import concord.appmodel.domain.PhotoIndexRequest;
import concord.appmodel.domain.PhotoIndexRequestSource;
import concord.rest.domain.PhotoIndexBatchRating;
import concord.rest.service.api.IPhotoIndexService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by aboieriu on 4/20/17.
 */
@RestController
@RequestMapping("/index")
public class PhotoIndexServiceController {

	@Resource
	private IPhotoIndexService photoIndexService;

	@RequestMapping(method = RequestMethod.GET)
	public Page<PhotoIndexBatch> getPhotoIndexBatches(@RequestParam(required = false) Integer page,
					@RequestParam(required = false) Integer pageSize,
					@RequestParam(required = false) Integer offset,
					@RequestParam(required = false) PhotoIndexRequestSource source) {
		return photoIndexService.getPhotoIndexBatches(new PageableRequest(page, pageSize, offset), source);
	}

	@RequestMapping(value = "/rate", method = RequestMethod.GET)
	public Page<PhotoIndexBatchRating> getPhotoIndexBatchesForRating(@RequestParam(required = false) Integer page,
					@RequestParam(required = false) Integer pageSize,
					@RequestParam(required = false) Integer offset) {
		return photoIndexService.getPhotoIndexBatchesForRating(new PageableRequest(page, pageSize, offset));
	}

	@RequestMapping(method = RequestMethod.POST)
	public PhotoIndexBatch createPhotoIndexBatch(@RequestBody  PhotoIndexRequest photoIndexRequest) {
		photoIndexRequest.setSource(PhotoIndexRequestSource.USER);
		return photoIndexService.createPhotoIndexBatch(photoIndexRequest);
	}

	@RequestMapping(value = "/{batchId}", method = RequestMethod.DELETE)
	public void deletePhotoIndexBatch(@PathVariable("batchId") String batchId) {
		photoIndexService.deleteBatch(batchId);
	}

	@RequestMapping("/{batchId}")
	public PhotoIndexBatch getPhotosForRating(@PathVariable("batchId") String batchId) {
		return photoIndexService.getPhotoIndexBatch(batchId);
	}

}
