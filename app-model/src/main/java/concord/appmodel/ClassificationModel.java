package concord.appmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import concord.appmodel.domain.ClassificationStatistics;
import concord.appmodel.domain.ModelConfig;
import concord.appmodel.domain.PhotoCategories;
import concord.appmodel.domain.TrainingParameters;
import concord.appmodel.domain.TrainingResults;
import concord.appmodel.domain.TrainingStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aboieriu on 4/19/17.
 */
@Document
public class ClassificationModel {
	@Id
	private String id;
	private Date date;
	private Date trainedDate;
	private TrainingParameters trainingParameters;
	private TrainingResults trainingResults;
	private ClassificationStatistics classificationStatistics;
	private ModelConfig modelConfig;
	private PhotoCategories photoCategory;
	private boolean trained;
	private TrainingStatus trainingStatus;

	@DBRef
	private List<ClassifiedPhoto> data;

	public ClassificationModel(){}

	public ClassificationModel(Date date){
		this.date = date;
	}

	@JsonCreator
	public ClassificationModel(
					@JsonProperty("id") String id,
					@JsonProperty("date") Date date,
					@JsonProperty("trainingParameters") TrainingParameters trainingParameters,
					@JsonProperty("trainingResults") TrainingResults trainingResults,
					@JsonProperty("classificationStatistics") ClassificationStatistics classificationStatistics,
					@JsonProperty("modelConfig") ModelConfig modelConfig,
					@JsonProperty("trained") boolean trained,
					@JsonProperty("photoCategory") PhotoCategories photoCategory) {
		this.id = id;
		this.date = date;
		this.trainingParameters = trainingParameters;
		this.trainingResults = trainingResults;
		this.classificationStatistics = classificationStatistics;
		this.modelConfig = modelConfig;
		this.trained = trained;
		this.photoCategory = photoCategory;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TrainingParameters getTrainingParameters() {
		return trainingParameters;
	}

	public void setTrainingParameters(TrainingParameters trainingParameters) {
		this.trainingParameters = trainingParameters;
	}

	public TrainingResults getTrainingResults() {
		return trainingResults;
	}

	public void setTrainingResults(TrainingResults trainingResults) {
		this.trainingResults = trainingResults;
	}

	public ClassificationStatistics getClassificationStatistics() {
		return classificationStatistics;
	}

	public void setClassificationStatistics(ClassificationStatistics classificationStatistics) {
		this.classificationStatistics = classificationStatistics;
	}

	public ModelConfig getModelConfig() {
		return modelConfig;
	}

	public void setModelConfig(ModelConfig modelConfig) {
		this.modelConfig = modelConfig;
	}

	public boolean isTrained() {
		return trained;
	}

	public void setTrained(boolean trained) {
		this.trained = trained;
	}

	@JsonIgnore
	public List<ClassifiedPhoto> getData() {
		return data;
	}

	@JsonIgnore
	public List<ClassifiedPhoto> getValidData(){
		return getData().stream().filter(dataItem -> dataItem.getPhoto().getLocalFilePath() != null).collect(
						Collectors.toList());
	}

	public int getDataCount(){
		return data != null ? data.size() : 0;
	}

	public void setData(List<ClassifiedPhoto> data) {
		this.data = data;
	}

	public PhotoCategories getPhotoCategory() {
		return photoCategory;
	}

	public void setPhotoCategory(PhotoCategories photoCategory) {
		this.photoCategory = photoCategory;
	}

	public Date getTrainedDate() {
		return trainedDate;
	}

	public void setTrainedDate(Date trainedDate) {
		this.trainedDate = trainedDate;
	}

	public TrainingStatus getTrainingStatus() {
		return trainingStatus;
	}

	public void setTrainingStatus(TrainingStatus trainingStatus) {
		this.trainingStatus = trainingStatus;
	}

	@Override public String toString() {
		return "ClassificationModel{" +
						"id='" + id + '\'' +
						", date=" + date +
						", trainingParameters=" + trainingParameters +
						", trainingResults=" + trainingResults +
						", classificationStatistics=" + classificationStatistics +
						", modelConfig=" + modelConfig +
						", photoCategory=" + photoCategory +
						", trained=" + trained +
						", data=" + data +
						'}';
	}


}
