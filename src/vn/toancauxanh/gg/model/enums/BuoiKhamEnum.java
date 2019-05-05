package vn.toancauxanh.gg.model.enums;

public enum BuoiKhamEnum {

	BUOI_SANG("Sáng"),
	BUOI_CHIEU("Chiều");
	
	BuoiKhamEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
