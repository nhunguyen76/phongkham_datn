package vn.toancauxanh.gg.model.enums;

public enum ThamSoEnum {
	CAT_ID_THUONG_TRUC_HDND("Chủ đề thường trực HĐND"),
	CAT_ID_CAC_BAN_HDND_HUYEN("Chủ đề  các ban HĐND huyện"),
	CAT_ID_HDND_XA("Chủ đề  HĐND Xã"),
	CAT_ID_GIAM_SAT_CUA_HDND("Chủ đề  Giám sát của HĐND"),
	CAT_TRANGCHU_MENU_LEFT("Danh sách chủ đề menu trái trang chủ"),
	CAT_ID_NGHIEN_CUU_TRAO_DOI("Chủ đề nghiên cứu và trao đổi"),
	CAT_VAN_DE_CU_TRI_QUAN_TAM("Chủ đề vấn đề của tri quan tâm"),
	CAT_THAO_LUAN_CHAT_VAN("Chủ đề thảo luận chất vấn"),
	TIEU_DE_HOI_DAP("Tiêu đề hỏi đáp"),
	CAT_ID_GIOITHIEU("Chủ đề giới thiệu"),
	CAT_ID_TINTUC("Chủ đề tin tức"),
	CAT_ID_LIENHE("Chủ đề liên hệ"),
	TIEU_DE_DANH_SACH_DAI_BIEU("Tiêu đề trang danh sách đại biểu"),
	TT_KHAC_THU_TUC_HO_SO("Khác"),
	CO_SO_PHAP_LY("cosophaply"),
	THANH_PHAN_HO_SO("thanhphanhoso"),
	THEM("THÊM MỚI"),
	SUA("CHỈNH SỬA"),
	XEM("XEM THÔNG TIN"),
	THOI_GIAN_BAT_DAU("Thời gian bắt đầu"),
	THOI_GIAN_KET_THUC("Thời gian kết thúc"),
	THOI_GIAN_NGAY_CAP("Ngày cấp"),
	THOI_GIAN_BAT_DAU_HIEU_LUC("Thời gian bắt đầu hiệu lực"),
	THOI_GIAN_KET_THUC_HIEU_LUC("Thời gian kết thúc hiệu lực"),
	TRANG_THAI_HO_SO("HoSo"),
	TRANG_THAI_THANH_PHAN_HO_SO("ThanhPhanHoSo"),
	HO_SO_THAM_DINH_MAC_DINH("HoSoThamDinhMacDinh"),
	H0_SO_THAM_DINH_Y_KIEN_SO("HoSoThamGiaYKienCoSo"),
	HO_SO_THAM_DINH_THIET_KE_CONG_TRINH("HoSoThamDinhThietKeCongTrinh"),
	HO_SO_THAM_DINH_DU_AN_DAU_TU_XAY_DUNG("HoSoThamDinhDuAnDauTuXayDung");
	String text;
	ThamSoEnum(final String txt) {
		text = txt;
	}
	public String getText() {
		return text;
	}
}
