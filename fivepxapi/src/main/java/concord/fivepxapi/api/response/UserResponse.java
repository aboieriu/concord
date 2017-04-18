package concord.fivepxapi.api.response;

import concord.appmodel.User;

/**
 * Created by aboieriu on 4/18/17.
 */
public class UserResponse {
	private User user;

	public UserResponse(){}

	public UserResponse(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
