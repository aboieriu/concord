package concord.appmodel.domain;

/**
 * Created by aboieriu on 4/18/17.
 */
public class PhotoIndexRequest {
	private int page;
	private PhotoCategories category;
	private PhotoFeature feature;

	public PhotoIndexRequest(){}

	public PhotoIndexRequest(int page, PhotoCategories category, PhotoFeature feature) {
		this.page = page;
		this.category = category;
		this.feature = feature;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PhotoCategories getCategory() {
		return category;
	}

	public void setCategory(PhotoCategories category) {
		this.category = category;
	}

	public PhotoFeature getFeature() {
		return feature;
	}

	public void setFeature(PhotoFeature feature) {
		this.feature = feature;
	}

	@Override
	public String toString() {
		return "PhotoIndexRequest{" +
						"page=" + page +
						", category=" + category +
						", feature=" + feature +
						'}';
	}
}
