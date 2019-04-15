package vn.toancauxanh.cms.service;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.HoSoBenhAn;
import vn.toancauxanh.gg.model.QHoSoBenhAn;
import vn.toancauxanh.service.BasicService;

public class HoSoBenhAnService extends BasicService<HoSoBenhAn>{

	public JPAQuery<HoSoBenhAn> getHoSoBenhAnByIdBenhNhan(Long id) {
		JPAQuery<HoSoBenhAn> q = find(HoSoBenhAn.class).where(QHoSoBenhAn.hoSoBenhAn.benhNhan.id.eq(id));
		q.orderBy(QHoSoBenhAn.hoSoBenhAn.ngayTao.desc());
		return q;
	}
	
}
