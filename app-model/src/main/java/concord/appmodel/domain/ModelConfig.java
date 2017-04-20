package concord.appmodel.domain;

/**
 * Created by aboieriu on 4/19/17.
 */
public class ModelConfig {
	private String graphPath;
	private String labelsPath;

	public ModelConfig() {
	}

	public String getGraphPath() {
		return graphPath;
	}

	public void setGraphPath(String graphPath) {
		this.graphPath = graphPath;
	}

	public String getLabelsPath() {
		return labelsPath;
	}

	public void setLabelsPath(String labelsPath) {
		this.labelsPath = labelsPath;
	}

	@Override
	public String toString() {
		return "ModelConfig{" +
						"graphPath='" + graphPath + '\'' +
						", labelsPath='" + labelsPath + '\'' +
						'}';
	}
}
