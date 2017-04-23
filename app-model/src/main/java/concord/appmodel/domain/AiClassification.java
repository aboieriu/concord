package concord.appmodel.domain;

import java.util.Map;

/**
 * Created by aboieriu on 4/23/17.
 */
public class AiClassification {
	String modelId;
	Map<PhotoCategory, Double> scores;

	public AiClassification(String modelId, Map<PhotoCategory, Double> scores) {
		this.modelId = modelId;
		this.scores = scores;
	}

	public AiClassification() {
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public Map<PhotoCategory, Double> getScores() {
		return scores;
	}

	public void setScores(Map<PhotoCategory, Double> scores) {
		this.scores = scores;
	}
}
