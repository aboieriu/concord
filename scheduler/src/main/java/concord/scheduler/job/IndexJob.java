package concord.scheduler.job;

import concord.indexer.api.IAppIndexer;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by aboieriu on 4/18/17.
 */
public class IndexJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexJob.class);

	@Resource
	IAppIndexer appIndexer;

	public void executeInternal() throws JobExecutionException {
		LOGGER.info("Running index job...");
		appIndexer.indexPhotos();
	}
}
