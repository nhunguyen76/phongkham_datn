package vn.toancauxanh.gg.model.enums;

public enum GioiTinhEnum {
	
	MALE("Nam"),
	FEMALE("Nữ");
	
	GioiTinhEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
