package vn.toancauxanh.gg.model.enums;

public enum PhanLoaiDoanhNghiepEnum {
	
	DOANH_NGHIEP_TU_NHAN("Doanh nghiệp tư nhân"),
	CONG_TY_TNHH("Công ty trách nhiệm hữu hạn"),
	CONG_TY_CO_PHAN("Công ty cổ phần"),
	CONG_TY_HOP_DANH("Công ty hợp danh"),
	CONG_TY_I_THANH_VIEN("Công ty một thành viên");
	
	PhanLoaiDoanhNghiepEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
