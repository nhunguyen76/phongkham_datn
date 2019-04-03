package vn.toancauxanh.gg.model.enums;

public enum LoaiDuAnEnum {
	THUY_LOI("Thủy lợi");
	
	String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private LoaiDuAnEnum(String text) {
		this.text = text;
	}
	
	
}
