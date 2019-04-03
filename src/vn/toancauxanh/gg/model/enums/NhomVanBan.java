package vn.toancauxanh.gg.model.enums;

public enum NhomVanBan {
	
	QUY_PHAM("Văn bản quy phạm"),
	XU_LY("Văn bản xử lý");
	
	NhomVanBan(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
