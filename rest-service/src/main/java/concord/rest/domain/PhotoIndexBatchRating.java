package concord.rest.domain;

import concord.appmodel.domain.PhotoIndexRequest;

import java.util.Date;

/**
 * Created by aboieriu on 4/21/17.
 */
public class PhotoIndexBatchRating {
	private String id;
	private PhotoIndexRequest photoIndexRequest;
	private Date date;
	private Integer totalPhotos;
	private Integer ratedPhotos;

	public PhotoIndexBatchRating(){}

	public PhotoIndexBatchRating(String id, PhotoIndexRequest photoIndexRequest, Date date, Integer totalPhotos, Integer ratedPhotos) {
		this.id = id;
		this.photoIndexRequest = photoIndexRequest;
		this.date = date;
		this.totalPhotos = totalPhotos;
		this.ratedPhotos = ratedPhotos;
	}

	public PhotoIndexRequest getPhotoIndexRequest() {
		return photoIndexRequest;
	}

	public void setPhotoIndexRequest(PhotoIndexRequest photoIndexRequest) {
		this.photoIndexRequest = photoIndexRequest;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getTotalPhotos() {
		return totalPhotos;
	}

	public void setTotalPhotos(Integer totalPhotos) {
		this.totalPhotos = totalPhotos;
	}

	public Integer getRatedPhotos() {
		return ratedPhotos;
	}

	public void setRatedPhotos(Integer ratedPhotos) {
		this.ratedPhotos = ratedPhotos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
