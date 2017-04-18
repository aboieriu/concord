package concord.indexer.config;

import concord.appdao.repository.IPhotoIndexBatchRepository;
import concord.appdao.repository.IPhotoRepository;
import concord.appdao.repository.IUserRepository;
import concord.fivepxapi.api.IFivepx;
import concord.indexer.api.IAppIndexer;
import concord.indexer.impl.AppIndexerImpl;
import concord.indexer.impl.internal.photo.PhotoIndexer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Created by aboieriu on 4/18/17.
 */
@Configuration
public class IndexerConfig {

	@Resource
	private IFivepx fivepx;

	@Resource
	private IUserRepository userRepository;

	@Resource
	private IPhotoIndexBatchRepository photoIndexBatchRepository;

	@Resource
	private IPhotoRepository photoRepository;

	@Bean
	public IAppIndexer getAppIndexer(){
		return new AppIndexerImpl(fivepx, userRepository, getPhotoIndexer());
	}

	@Bean
	public PhotoIndexer getPhotoIndexer(){
		return new PhotoIndexer(fivepx, photoIndexBatchRepository, photoRepository);
	}
}
