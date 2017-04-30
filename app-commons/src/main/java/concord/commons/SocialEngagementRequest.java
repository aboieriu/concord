package concord.commons;

/**
 * Created by aboieriu on 4/23/17.
 */
public class SocialEngagementRequest {
	private boolean like;
	private String comment;
	private String photoId;
	private String userId;

	public SocialEngagementRequest() {
	}

	public SocialEngagementRequest(boolean like, String comment, String photoId) {
		this.like = like;
		this.comment = comment;
		this.photoId = photoId;
	}

	public boolean isLike() {
		return like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "SocialEngagementRequest{" +
						"like=" + like +
						", comment='" + comment + '\'' +
						", photoId='" + photoId + '\'' +
						'}';
	}
}
