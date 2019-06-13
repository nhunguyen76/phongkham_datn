package vn.toancauxanh.gg.model.enums;

public enum SapXepLichKhamEnum {

	NGAY_KHAM_GAN_NHAT("Ngày khám gần nhất"),
	NGAY_KHAM_XA_NHAT("Ngày khám Xa nhất"),
	NGAY_DAT_HEN_GAN_NHAT("Ngày đặt hẹn gần nhất"),
	NGAY_DAT_HEN_XA_NHAT("Ngày đặt hẹn xa nhất");
	
	SapXepLichKhamEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
