package concord.indexer.impl;

import com.google.common.base.Preconditions;
import concord.appdao.repository.IUserRepository;
import concord.appmodel.User;
import concord.fivepxapi.api.IFivepx;
import concord.fivepxapi.api.response.UserResponse;
import concord.fivepxapi.constant.FivePxApiConstants;
import concord.indexer.api.IAppIndexer;
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

	public AppIndexerImpl(IFivepx fivepx, IUserRepository userRepository) {
		this.fivepx = Preconditions.checkNotNull(fivepx, "fivepx must be initialized");
		this.userRepository = Preconditions.checkNotNull(userRepository, "userRepository must be initialized");
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
}
