package concord.appmodel.domain;

/**
 * Created by aboieriu on 4/19/17.
 */
public enum PhotoCategory {
	STAGE1(1),
	STAGE2(2),
	STAGE3(3),
	STAGE4(4),
	STAGE5(5);

	private int level;

	PhotoCategory(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public static PhotoCategory buildByRating(String rating) {
		return PhotoCategory.valueOf("STAGE"+rating);
	}

}
