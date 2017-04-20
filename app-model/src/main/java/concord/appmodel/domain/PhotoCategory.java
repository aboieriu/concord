package concord.appmodel.domain;

/**
 * Created by aboieriu on 4/19/17.
 */
public enum PhotoCategory {
	STAGE1,
	STAGE2,
	STAGE3,
	STAGE4,
	STAGE5;

	public static PhotoCategory buildByRating(String rating) {
		return PhotoCategory.valueOf("STAGE"+rating);
	}

}
