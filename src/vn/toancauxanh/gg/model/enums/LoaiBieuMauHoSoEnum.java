package vn.toancauxanh.gg.model.enums;

public enum LoaiBieuMauHoSoEnum {
	MAC_DINH("Mặc định"),
	HO_SO_THAM_GIA_Y_KIEN_SO("Hồ sơ tham gia ý kiến sở"),
	HO_SO_THAM_DINH_DU_AN_DAU_TU("Hồ sơ thẩm định dự án đầu tư xây dựng công trình"),
	HO_SO_THAM_DINH_THIET_KE_CONG_TRINH("Hồ sơ thẩm định thiết kế công trình");
	
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private LoaiBieuMauHoSoEnum(String text) {
		this.text = text;
	}
	
	
}
