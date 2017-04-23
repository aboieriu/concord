package concord.appmodel.domain;

/**
 * Created by aboieriu on 4/18/17.
 */
public enum PhotoCategories {

	ANIMALS(11),
	CITY_AND_ARCHITECTURE(9),
	LANDSCAPES(8),
	MACRO(12),
	NATURE(18),
	STREET(21),
	STILL_LIFE(6),
	BLACK_AND_WHITE(5)
	;

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
