package concord.commons;

/**
 * Created by aboieriu on 4/21/17.
 */
public class ClassifyPhotoRequest {
	private String modelId;
	private String photoId;

	public ClassifyPhotoRequest() {
	}

	public ClassifyPhotoRequest(String modelId, String photoId) {
		this.modelId = modelId;
		this.photoId = photoId;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	@Override public String toString() {
		return "PhotoDownloadRequest{" +
						"modelId='" + modelId + '\'' +
						", photoId='" + photoId + '\'' +
						'}';
	}
}
