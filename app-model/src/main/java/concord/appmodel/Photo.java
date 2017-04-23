package concord.appmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by aboieriu on 4/18/17.
 */
@Document
public class Photo {
	@Id
	private String photoId;
	private Long userId;
	private String name;
	private String description;
	private String camera;
	private String lens;
	private String focalLength;
	private String iso;
	private String shutterSpeed;
	private String aperture;
	private Long timesViewed;
	private Long rating;
	private Long category;
	private Long votesCount;
	private Long favoritesCount;
	private Long commentsCount;
	private List<ImageItem> images;
	private String localFilePath;
	private Boolean humanRated = Boolean.FALSE;
	private Boolean aiRated = Boolean.FALSE;
	private Boolean downloadFailed = Boolean.FALSE;

	@JsonCreator
	public Photo(
					@JsonProperty("id") String photoId,
					@JsonProperty("user_id") Long userId,
					@JsonProperty("name") String name,
					@JsonProperty("description") String description,
					@JsonProperty("camera") String camera,
					@JsonProperty("lens") String lens,
					@JsonProperty("focal_length") String focalLength,
					@JsonProperty("iso") String iso,
					@JsonProperty("shutter_speed") String shutterSpeed,
					@JsonProperty("aperture") String aperture,
					@JsonProperty("times_viewed") Long timesViewed,
					@JsonProperty("rating") Long rating,
					@JsonProperty("category") Long category,
					@JsonProperty("votes_count") Long votesCount,
					@JsonProperty("favorites_count") Long favoritesCount,
					@JsonProperty("comments_count") Long commentsCount,
					@JsonProperty("images") List<ImageItem> images,
					@JsonProperty("humanRated") Boolean humanRated,
					@JsonProperty("aiRated") Boolean aiRated,
					@JsonProperty("localFilePath") String localFilePath) {
		this.photoId = photoId;
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.camera = camera;
		this.lens = lens;
		this.focalLength = focalLength;
		this.iso = iso;
		this.shutterSpeed = shutterSpeed;
		this.aperture = aperture;
		this.timesViewed = timesViewed;
		this.rating = rating;
		this.category = category;
		this.votesCount = votesCount;
		this.favoritesCount = favoritesCount;
		this.commentsCount = commentsCount;
		this.images = images;
		this.localFilePath = localFilePath;
		this.humanRated = humanRated;
		this.aiRated = aiRated;
	}

	public String getLocalFilePath() {
		return localFilePath;
	}

	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
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

	public String getFocalLength() {
		return focalLength;
	}

	public void setFocalLength(String focalLength) {
		this.focalLength = focalLength;
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

	public List<ImageItem> getImages() {
		return images;
	}

	public void setImages(List<ImageItem> images) {
		this.images = images;
	}

	public Boolean getHumanRated() {
		return humanRated;
	}

	public void setHumanRated(Boolean humanRated) {
		this.humanRated = humanRated;
	}

	public Boolean getAiRated() {
		return aiRated;
	}

	public void setAiRated(Boolean aiRated) {
		this.aiRated = aiRated;
	}

	public Boolean getDownloadFailed() {
		return downloadFailed != null && downloadFailed;
	}

	public void setDownloadFailed(Boolean downloadFailed) {
		this.downloadFailed = downloadFailed;
	}

	@Override public String toString() {
		return "Photo{" +
						"photoId='" + photoId + '\'' +
						", userId=" + userId +
						", name='" + name + '\'' +
						", description='" + description + '\'' +
						", camera='" + camera + '\'' +
						", lens='" + lens + '\'' +
						", focalLength='" + focalLength + '\'' +
						", iso='" + iso + '\'' +
						", shutterSpeed='" + shutterSpeed + '\'' +
						", aperture='" + aperture + '\'' +
						", timesViewed=" + timesViewed +
						", rating=" + rating +
						", category=" + category +
						", votesCount=" + votesCount +
						", favoritesCount=" + favoritesCount +
						", commentsCount=" + commentsCount +
						", images=" + images +
						", localFilePath='" + localFilePath + '\'' +
						", humanRated=" + humanRated +
						", aiRated=" + aiRated +
						'}';
	}
}
