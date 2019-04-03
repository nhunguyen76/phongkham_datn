package vn.toancauxanh.gg.model.enums;

public enum LyDoXinCapLaiChungChiEnum {
	THAT_LAC_HU_HONG("Do bị thất lạc, hư hỏng"),
	BI_THU_HOI("Do bị thu hồi"),
	HET_HAN("Hết hạn");
	
	private String lyDoXinCapLaiChungChi;

	public String getLyDoXinCapLaiChungChi() {
		return lyDoXinCapLaiChungChi;
	}

	public void setLyDoXinCapLaiChungChi(String lyDoXinCapLaiChungChi) {
		this.lyDoXinCapLaiChungChi = lyDoXinCapLaiChungChi;
	}

	private LyDoXinCapLaiChungChiEnum(String lyDoXinCapLaiChungChi) {
		this.lyDoXinCapLaiChungChi = lyDoXinCapLaiChungChi;
	}

	
}
