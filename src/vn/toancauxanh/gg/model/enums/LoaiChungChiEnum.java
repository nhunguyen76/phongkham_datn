package vn.toancauxanh.gg.model.enums;

public enum LoaiChungChiEnum {
	
	CHUNG_CHI_HANH_NGHE("Chứng chỉ hành nghề"),
	CHUNG_CHI_BDS("Chứng chỉ bất động sản");
	
	LoaiChungChiEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
