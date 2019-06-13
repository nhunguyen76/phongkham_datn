package vn.toancauxanh.gg.model.enums;

public enum ThoiGianDatHenEnum {

	GAN_NHAT("Gần nhất"),
	XA_NHAT("Xa nhất");
	
	ThoiGianDatHenEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
