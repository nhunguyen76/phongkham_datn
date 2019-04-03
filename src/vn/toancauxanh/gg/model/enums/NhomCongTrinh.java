package vn.toancauxanh.gg.model.enums;

public enum NhomCongTrinh {
	
	NHOM_A("Nhóm A"),
	NHOM_B("Nhóm B"),
	NHOM_C("Nhóm C");
	
	NhomCongTrinh(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
