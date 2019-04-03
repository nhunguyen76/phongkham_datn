package vn.toancauxanh.gg.model.enums;

public enum GioiTinhEnum {
	
	NAM("Nam"),
	NU("Nữ");
	
	GioiTinhEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
