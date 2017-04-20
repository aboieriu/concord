package concord.appmodel.domain;

/**
 * Created by aboieriu on 4/19/17.
 */
public class TrainingParameters {
	private int steps;
	private String algorithm;

	public TrainingParameters() {
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	@Override public String toString() {
		return "TrainingParameters{" +
						"steps=" + steps +
						", algorithm='" + algorithm + '\'' +
						'}';
	}
}
