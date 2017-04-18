package concord.fivepxapi.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import concord.appmodel.Photo;

import java.util.List;

/**
 * Created by aboieriu on 4/18/17.
 */
public class PhotoResponse {

	public int currentPage;
	public int totalPages;
	public int totalItems;
	public List<Photo> photoList;

	@JsonCreator
	public PhotoResponse(
					@JsonProperty("current_page") int currentPage,
					@JsonProperty("total_pages") int totalPages,
					@JsonProperty("total_items") int totalItems,
					@JsonProperty("photos") List<Photo> photoList) {
		this.currentPage = currentPage;
		this.totalPages = totalPages;
		this.totalItems = totalItems;
		this.photoList = photoList;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public List<Photo> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}

	@Override
	public String toString() {
		return "PhotoResponse{" +
						"currentPage=" + currentPage +
						", totalPages=" + totalPages +
						", totalItems=" + totalItems +
						", photoList=" + photoList +
						'}';
	}
}
