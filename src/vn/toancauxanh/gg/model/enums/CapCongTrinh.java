package vn.toancauxanh.gg.model.enums;

public enum CapCongTrinh {
	
	CAP_I("Cấp I"),
	CAP_II("Cấp II"),
	CAP_III("Cấp III"),
	CAP_IV("Cấp IV"),
	DAC_BIET("Đặc Biệt");
	
	CapCongTrinh(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
