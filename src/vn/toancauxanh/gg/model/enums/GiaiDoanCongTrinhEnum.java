package vn.toancauxanh.gg.model.enums;

public enum GiaiDoanCongTrinhEnum {
	THONG_TIN_CONG_TRINH("congtrinh/add-cong-trinh.zul"),
	THANH_PHAN_HO_SO("congtrinh/add-thanh-phan-ho-so.zul"),
	CO_SO_PHAP_LY("congtrinh/add-co-so-phap-ly.zul"),
	LICH_SU_XU_LY("congtrinh/lich-su-xu-ly.zul");
	String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private GiaiDoanCongTrinhEnum() {
	}

	private GiaiDoanCongTrinhEnum(String text) {
		this.text = text;
	}
	
	
}
