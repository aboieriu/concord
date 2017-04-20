package concord.appmodel.domain;

/**
 * Created by aboieriu on 4/19/17.
 */
public class ClassificationStatistics {
	private int nbImageClassified;
	private int nbFalsePositive;

	public ClassificationStatistics() {
	}

	public int getNbImageClassified() {
		return nbImageClassified;
	}

	public void setNbImageClassified(int nbImageClassified) {
		this.nbImageClassified = nbImageClassified;
	}

	public int getNbFalsePositive() {
		return nbFalsePositive;
	}

	public void setNbFalsePositive(int nbFalsePositive) {
		this.nbFalsePositive = nbFalsePositive;
	}

	@Override public String toString() {
		return "ClassificationStatistics{" +
						"nbImageClassified=" + nbImageClassified +
						", nbFalsePositive=" + nbFalsePositive +
						'}';
	}
}
