package concord.appmodel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by aboieriu on 4/18/17.
 */
@Document
public class PhotoIndexBatch {

	@Id
	private String id;
	@DBRef
	private List<Photo> photos;
	private Date date;
	private Boolean processed = Boolean.FALSE;

	public PhotoIndexBatch(List<Photo> photos, Date date, Boolean processed) {
		this.photos = photos;
		this.date = date;
		this.processed = processed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	@Override public String toString() {
		return "PhotoIndexBatch{" +
						"id='" + id + '\'' +
						", photos=" + photos +
						", date=" + date +
						", processed=" + processed +
						'}';
	}
}
