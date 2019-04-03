package vn.toancauxanh.gg.model.enums;

public enum HinhThucCapChungChiEnum {
	CAP_MOI_NANG_HANG("Cấp mới/ Nâng hạng"),
	CAP_LAI("Cấp lại (Do hết hạn)"),
	CHUYEN_DOI("Chuyển đổi (Đối với cá nhân người nước ngoài đã được cấp CCHN do tổ chức nước ngoài cấp)");
	private String hinhThucCapChungChi;

	public String getHinhThucCapChungChi() {
		return hinhThucCapChungChi;
	}

	public void setHinhThucCapChungChi(String hinhThucCapChungChi) {
		this.hinhThucCapChungChi = hinhThucCapChungChi;
	}

	private HinhThucCapChungChiEnum(String hinhThucCapChungChi) {
		this.hinhThucCapChungChi = hinhThucCapChungChi;
	}
	
	
}
