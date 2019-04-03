package vn.toancauxanh.gg.model.enums;

public enum QuocTichEnum {
	
	VIET_NAM("Việt Nam"),
	KHAC("Khác");
	
	QuocTichEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
