package concord.appmodel.domain;

/**
 * Created by aboieriu on 4/18/17.
 */
public enum PhotoCategories {

	ANIMALS(11);

	private int code;

	PhotoCategories(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
