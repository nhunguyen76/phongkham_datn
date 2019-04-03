package vn.toancauxanh.gg.model.enums;

public enum TinhTrangHoSoEnum {
	CHUA_DEN_HAN("Chưa đến hạn"),
	SOM_HAN("Sớm hạn"),
	DUNG_HAN("Đúng hạn"),
	TRE_HAN("Trễ hạn");
	private String text;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	private TinhTrangHoSoEnum(String text) {
		this.text = text;
	}
	
	
}
