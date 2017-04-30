package concord.appmodel.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by aboieriu on 4/23/17.
 */
public class AiClassification {
	private String modelId;
	private Map<PhotoCategory, Double> scores;

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

	public PhotoCategory getHighestScore(){
		// If classification is missing, return the lowest
		if (scores.entrySet().isEmpty()) {
			return PhotoCategory.STAGE1;
		}
		return scores.entrySet().stream().sorted((e2, e1) -> Double.compare(e1.getValue(), e2.getValue())).collect(
						Collectors.toList()).get(0).getKey();
	}

	public void setScores(Map<PhotoCategory, Double> scores) {
		this.scores = scores;
	}

	@Override public String toString() {
		return "AiClassification{" +
						"modelId='" + modelId + '\'' +
						", scores=" + scores +
						'}';
	}
}
