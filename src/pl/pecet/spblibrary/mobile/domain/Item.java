package pl.pecet.spblibrary.mobile.domain;

public class Item {
	private Long id;

	private String title;

	private String author;

	private Integer year;

	private byte[] photo;

	private ItemCategory category;

	private String barCode;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(final String author) {
		this.author = author;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(final Integer year) {
		this.year = year;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(final byte[] photo) {
		this.photo = photo;
	}

	public ItemCategory getCategory() {
		return category;
	}

	public void setCategory(final ItemCategory category) {
		this.category = category;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(final String barCode) {
		this.barCode = barCode;
	}
}
