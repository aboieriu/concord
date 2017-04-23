package concord.appmodel.domain;

import java.util.Date;

/**
 * Created by aboieriu on 4/19/17.
 */
public class TrainingResults {
	private Date startTime;
	private Date endTime;
	private double testAccuracy;

	public TrainingResults(){}

	public double getTestAccuracy() {
		return testAccuracy;
	}

	public void setTestAccuracy(double testAccuracy) {
		this.testAccuracy = testAccuracy;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
