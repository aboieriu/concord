package concord.fivepxapi.api;

import concord.fivepxapi.api.response.PhotoResponse;
import concord.fivepxapi.api.response.UserResponse;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by aboieriu on 4/18/17.
 */
public interface IFivepx {

	/**
	 * returns a user's details. Works with a targeted userId
	 * @return
	 */
	Optional<UserResponse> getUserDetails(Serializable userId);

	/**
	 * returns a list of photos based on input params
	 * @param category
	 * @param page
	 * @return
	 */
	Optional<PhotoResponse> getPhotos(String category, String feature, int page);

}
