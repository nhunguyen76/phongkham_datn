package vn.toancauxanh.cms.service;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.DanhGia;
import vn.toancauxanh.gg.model.QDanhGia;
import vn.toancauxanh.service.BasicService;

public class DanhGiaService extends BasicService<DanhGia>{

	public JPAQuery<DanhGia> getTargetQuery() {
		
		JPAQuery<DanhGia> query = find(DanhGia.class);
		query.orderBy(QDanhGia.danhGia.ngaySua.desc());
		return query;
	}
}
