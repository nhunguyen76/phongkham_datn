package vn.toancauxanh.gg.model.enums;

public enum LoaiHanhDongEnum {
	
	LOGIN("Đăng nhập"),
	LOGOUT("Đăng xuất");
	
	LoaiHanhDongEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
