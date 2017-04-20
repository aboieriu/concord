package concord.appmodel.domain;

/**
 * Created by aboieriu on 4/19/17.
 */
public class TrainingResults {
	private long testAccuracy;

	public TrainingResults(){}

	public long getTestAccuracy() {
		return testAccuracy;
	}

	public void setTestAccuracy(long testAccuracy) {
		this.testAccuracy = testAccuracy;
	}
}
