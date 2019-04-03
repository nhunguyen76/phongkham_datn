package vn.toancauxanh.gg.model.enums;

public enum TrangThaiXuLyEnum {
	
	THEM("Thêm mới"),
	SUA("Sửa"),
	XOA("Xóa");
	
	TrangThaiXuLyEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
