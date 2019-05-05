package vn.toancauxanh.gg.model.enums;

public enum TrangThaiXuLyEnum {
	
	DA_THUC_HIEN("Đã thực hiện"),
	HUY_HEN("Hủy hẹn"),
	DA_DUYET("Đã duyệt"),
	CHO_DUYET("Chờ duyệt");
	
	TrangThaiXuLyEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
