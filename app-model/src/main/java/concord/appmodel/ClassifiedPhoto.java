package concord.appmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import concord.appmodel.domain.AiClassification;
import concord.appmodel.domain.PhotoCategory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
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
	List<AiClassification> aiClassification = new ArrayList<>();

	public ClassifiedPhoto(){}

	@JsonCreator
	public ClassifiedPhoto(
					@JsonProperty("id") String id,
					@JsonProperty("photo") Photo photo,
					@JsonProperty("humanClassification") PhotoCategory humanClassification) {
		this.id = id;
		this.photo = photo;
		this.humanClassification = humanClassification;
	}

	public ClassifiedPhoto(Photo photo, List<AiClassification> aiClassification) {
		this.photo = photo;
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

	public List<AiClassification> getAiClassification() {
		return aiClassification;
	}

	public void setAiClassification(List<AiClassification> aiClassification) {
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
