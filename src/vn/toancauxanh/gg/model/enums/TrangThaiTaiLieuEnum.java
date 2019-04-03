package vn.toancauxanh.gg.model.enums;

public enum TrangThaiTaiLieuEnum {
	BAN_GOC("Bản gốc"),
	BAN_SAO("Bản sao"),
	BAN_PHOTO("Bản photo"),
	VAN_BAN_KHAC("Văn bản khác");
	
	String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private TrangThaiTaiLieuEnum(String text) {
		this.text = text;
	}

	private TrangThaiTaiLieuEnum() {
	}
	
	
}
