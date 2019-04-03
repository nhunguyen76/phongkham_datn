package vn.toancauxanh.gg.model.enums;

public enum DoiTuongXuLyEnum {
	
	CONG_TRINH("Công trình"),
	THANH_PHAN_HO_SO("Quản lý hồ sơ"),
	CO_SO_PHAP_LY("Cơ sở pháp lý");
	
	DoiTuongXuLyEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
