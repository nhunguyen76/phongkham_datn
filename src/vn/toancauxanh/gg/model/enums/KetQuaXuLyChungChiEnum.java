package vn.toancauxanh.gg.model.enums;

public enum KetQuaXuLyChungChiEnum {
	
	DONG_Y_CAP_CHUNG_CHI("Đồng ý cấp chứng chỉ"),
	KHONG_DONG_Y_CAP_CHUNG_CHI("Không đồng ý cấp chứng chỉ");
	
	KetQuaXuLyChungChiEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
