package vn.toancauxanh.gg.model.enums;

public enum LoaiGiayToEnum {
	CMND("Chứng minh nhân dân"),
	HO_CHIEU("Hộ chiếu"),
	THE_CAN_CUOC_CONG_DAN("Thẻ căn cước công dân");
	
	private String loaiGiayTo;

	public String getLoaiGiayTo() {
		return loaiGiayTo;
	}

	public void setLoaiGiayTo(String loaiGiayTo) {
		this.loaiGiayTo = loaiGiayTo;
	}

	private LoaiGiayToEnum(String loaiGiayTo) {
		this.loaiGiayTo = loaiGiayTo;
	}
	
	
}
