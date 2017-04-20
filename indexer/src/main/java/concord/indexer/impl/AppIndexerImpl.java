package concord.indexer.impl;

import com.google.common.base.Preconditions;
import concord.appdao.repository.IUserRepository;
import concord.appmodel.PhotoIndexBatch;
import concord.appmodel.User;
import concord.fivepxapi.api.IFivepx;
import concord.fivepxapi.api.response.UserResponse;
import concord.fivepxapi.constant.FivePxApiConstants;
import concord.appmodel.domain.PhotoCategories;
import concord.fivepxapi.constant.PhotoFeature;
import concord.indexer.api.IAppIndexer;
import concord.indexer.impl.internal.photo.PhotoIndexer;
import concord.indexer.impl.internal.photo.domain.PhotoIndexRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Created by aboieriu on 4/18/17.
 */
public class AppIndexerImpl implements IAppIndexer {
	private static Logger LOGGER = LoggerFactory.getLogger(AppIndexerImpl.class);

	private final IFivepx fivepx;
	private final IUserRepository userRepository;
	private final PhotoIndexer photoIndexer;

	public AppIndexerImpl(IFivepx fivepx, IUserRepository userRepository, PhotoIndexer photoIndexer) {
		this.fivepx = Preconditions.checkNotNull(fivepx, "fivepx must be initialized");
		this.userRepository = Preconditions.checkNotNull(userRepository, "userRepository must be initialized");
		this.photoIndexer = Preconditions.checkNotNull(photoIndexer, "photoIndexer must be initialized");
	}

	@Override
	public void indexUser() {
		Optional<UserResponse> userResponse = fivepx.getUserDetails(FivePxApiConstants.targetUserId);
		if (userResponse.isPresent()) {
			User targetUser = userResponse.get().getUser();
			User persistedUser = userRepository.findByUserId(targetUser.getUserId());
			if (persistedUser != null) {
				persistedUser.merge(targetUser);
				userRepository.save(persistedUser);
			} else {
				userRepository.save(targetUser);
			}
		} else {
			LOGGER.error("Unable to get user response for indexation");
		}
	}

	public void indexPhotos(){
		PhotoIndexRequest photoIndexRequest = new PhotoIndexRequest(1, PhotoCategories.ANIMALS, PhotoFeature.UPCOMING);
		PhotoIndexBatch photoIndexBatch = photoIndexer.indexPhotos(photoIndexRequest);

		String x = "test";
	}
}
