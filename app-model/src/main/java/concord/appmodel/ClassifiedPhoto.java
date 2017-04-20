package concord.appmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import concord.appmodel.domain.PhotoCategory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * Created by aboieriu on 4/19/17.
 */
@Document
public class ClassifiedPhoto {

	@Id
	private String id;
	@DBRef
	private Photo photo;
	private PhotoCategory humanClassification;
	Map<PhotoCategory, Long> aiClassification;

	public ClassifiedPhoto(){}

	@JsonCreator
	public ClassifiedPhoto(
					@JsonProperty("id") String id,
					@JsonProperty("photo") Photo photo,
					@JsonProperty("humanClassification") PhotoCategory humanClassification,
					@JsonProperty("aiClassification")  Map<PhotoCategory, Long> aiClassification) {
		this.id = id;
		this.photo = photo;
		this.humanClassification = humanClassification;
		this.aiClassification = aiClassification;
	}

	public ClassifiedPhoto(Photo photo, PhotoCategory humanClassification) {
		this.photo = photo;
		this.humanClassification = humanClassification;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public PhotoCategory getHumanClassification() {
		return humanClassification;
	}

	public void setHumanClassification(PhotoCategory humanClassification) {
		this.humanClassification = humanClassification;
	}

	public Map<PhotoCategory, Long> getAiClassification() {
		return aiClassification;
	}

	public void setAiClassification(Map<PhotoCategory, Long> aiClassification) {
		this.aiClassification = aiClassification;
	}

	@Override
	public String toString() {
		return "ClassifiedPhoto{" +
						"id='" + id + '\'' +
						", photo=" + photo +
						", humanClassification=" + humanClassification +
						", aiClassification=" + aiClassification +
						'}';
	}
}
