package pl.pecet.spblibrary.mobile.domain;

public enum ItemCategory {

	BOOK(0), MAP(1);

	private int code;

	private ItemCategory(final int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
