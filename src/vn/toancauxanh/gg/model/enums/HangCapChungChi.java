package vn.toancauxanh.gg.model.enums;

public enum HangCapChungChi {
	HANG_II("Hạng II"),
	HANG_III("Hạng III");
	
	private String hangCapChungChi;

	public String getHangCapChungChi() {
		return hangCapChungChi;
	}

	public void setHangCapChungChi(String hangCapChungChi) {
		this.hangCapChungChi = hangCapChungChi;
	}

	private HangCapChungChi(String hangCapChungChi) {
		this.hangCapChungChi = hangCapChungChi;
	}

	
	
}
