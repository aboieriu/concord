package concord.appmodel;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by aboieriu on 4/18/17.
 */
@Document
public class Photo {
	private Long photoId;
	private Long userId;
	private String name;
	private String description;
	private String camera;
	private String lens;
	private String focal_length;
	private String iso;
	private String shutterSpeed;
	private String aperture;
	private Long timesViewed;
	private Long rating;
	private Long category;
	private Long votesCount;
	private Long favoritesCount;
	private Long commentsCount;

	public Photo(Long photoId, Long userId, String name, String description, String camera, String lens,
					String focal_length, String iso, String shutterSpeed, String aperture, Long timesViewed,
					Long rating, Long category, Long votesCount, Long favoritesCount, Long commentsCount) {
		this.photoId = photoId;
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.camera = camera;
		this.lens = lens;
		this.focal_length = focal_length;
		this.iso = iso;
		this.shutterSpeed = shutterSpeed;
		this.aperture = aperture;
		this.timesViewed = timesViewed;
		this.rating = rating;
		this.category = category;
		this.votesCount = votesCount;
		this.favoritesCount = favoritesCount;
		this.commentsCount = commentsCount;
	}

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCamera() {
		return camera;
	}

	public void setCamera(String camera) {
		this.camera = camera;
	}

	public String getLens() {
		return lens;
	}

	public void setLens(String lens) {
		this.lens = lens;
	}

	public String getFocal_length() {
		return focal_length;
	}

	public void setFocal_length(String focal_length) {
		this.focal_length = focal_length;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getShutterSpeed() {
		return shutterSpeed;
	}

	public void setShutterSpeed(String shutterSpeed) {
		this.shutterSpeed = shutterSpeed;
	}

	public String getAperture() {
		return aperture;
	}

	public void setAperture(String aperture) {
		this.aperture = aperture;
	}

	public Long getTimesViewed() {
		return timesViewed;
	}

	public void setTimesViewed(Long timesViewed) {
		this.timesViewed = timesViewed;
	}

	public Long getRating() {
		return rating;
	}

	public void setRating(Long rating) {
		this.rating = rating;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public Long getVotesCount() {
		return votesCount;
	}

	public void setVotesCount(Long votesCount) {
		this.votesCount = votesCount;
	}

	public Long getFavoritesCount() {
		return favoritesCount;
	}

	public void setFavoritesCount(Long favoritesCount) {
		this.favoritesCount = favoritesCount;
	}

	public Long getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(Long commentsCount) {
		this.commentsCount = commentsCount;
	}

	@Override public String toString() {
		return "Photo{" +
						"photoId=" + photoId +
						", userId=" + userId +
						", name='" + name + '\'' +
						", description='" + description + '\'' +
						", camera='" + camera + '\'' +
						", lens='" + lens + '\'' +
						", focal_length='" + focal_length + '\'' +
						", iso='" + iso + '\'' +
						", shutterSpeed='" + shutterSpeed + '\'' +
						", aperture='" + aperture + '\'' +
						", timesViewed=" + timesViewed +
						", rating=" + rating +
						", category=" + category +
						", votesCount=" + votesCount +
						", favoritesCount=" + favoritesCount +
						", commentsCount=" + commentsCount +
						'}';
	}
}
