package vn.toancauxanh.gg.model.enums;

public enum VaiTroEnum {
	BACSI("Bác sĩ"), 
	NHANVIEN("Nhân viên"), 
	BENHNHAN("Bệnh nhân");

	VaiTroEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;

}
