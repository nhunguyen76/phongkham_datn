package vn.toancauxanh.cms.service;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.QThamSo;
//import vn.toancauxanh.gg.model.QThamSo;
import vn.toancauxanh.gg.model.ThamSo;
import vn.toancauxanh.service.BasicService;

public class ThamSoService extends BasicService<ThamSo> {
	
	public JPAQuery<ThamSo> getTargetQuery() {
		JPAQuery<ThamSo> q = find(ThamSo.class)
				.where(QThamSo.thamSo.trangThai.ne(core().TT_DA_XOA));
		return q.orderBy(QThamSo.thamSo.ngaySua.desc());
	}
	
	public JPAQuery<ThamSo> getThamSoByKey(String key) {
		JPAQuery<ThamSo> q = find(ThamSo.class)
				.where(QThamSo.thamSo.trangThai.ne(core().TT_DA_XOA).and(QThamSo.thamSo.ten.eq(key)));
		return q.orderBy(QThamSo.thamSo.ngaySua.desc());
	}
	
	public Long getIdDonViHanhChinhDaNang() {
		ThamSo thamSo = find(ThamSo.class).where(QThamSo.thamSo.ten.eq("DVHC_TP_DA_NANG")).fetchFirst();
		String stringValue = "0";
		
		if(thamSo != null) {
			stringValue = thamSo.getGiaTri();
		}
		
		return Long.valueOf(stringValue);
	}
}
