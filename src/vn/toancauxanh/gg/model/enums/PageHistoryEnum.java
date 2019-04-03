package vn.toancauxanh.gg.model.enums;

public enum PageHistoryEnum {
	PAGE_HISTORY_COMPARE_TPHS("congtrinh/page_history_compare_tphs.zul"),
	PAGE_HISTORY_DETAIL_VANBAN("congtrinh/page_history_detail_vanban.zul"),
	PAGE_HISTORY_COMPARE_DETAIL_VANBAN("congtrinh/page_history_compare_detail_vanban.zul"),
	PAGE_HISTORY_COMPARE_COSOPHAPLY("congtrinh/page_history_compare_cosophaply.zul"),
	PAGE_HISTORY_DETAIL_VANBAN_COSOPHAPLY("congtrinh/page_history_detail_vanban_cosophaply.zul"),
	PAGE_HISTORY_COMPARE_DETAIL_VANBAN_COSOPHAPLY("congtrinh/page_history_compare_detail_vanban_cosophaply.zul");
	String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private PageHistoryEnum() {
	}

	private PageHistoryEnum(String url) {
		this.url = url;
	}
	
	
}
