package vn.toancauxanh.cms.service;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.HoSoBenhAn;
import vn.toancauxanh.gg.model.HoSoThongTin;
import vn.toancauxanh.gg.model.QHoSoBenhAn;
import vn.toancauxanh.service.BasicService;

public class HoSoBenhAnService extends BasicService<HoSoBenhAn>{
	
	private HoSoThongTin hoSoThongTin;

	public HoSoThongTin getHoSoThongTin() {
		return hoSoThongTin;
	}

	public void setHoSoThongTin(HoSoThongTin hoSoThongTin) {
		this.hoSoThongTin = hoSoThongTin;
	}

	public JPAQuery<HoSoBenhAn> getHoSoBenhAnByIdBenhNhan(Long id) {
		JPAQuery<HoSoBenhAn> q = find(HoSoBenhAn.class).where(QHoSoBenhAn.hoSoBenhAn.benhNhan.id.eq(id));
		q.orderBy(QHoSoBenhAn.hoSoBenhAn.ngayTao.desc());
		return q;
	}

	public JPAQuery<HoSoBenhAn> getListHoSoBenhAn() {
		JPAQuery<HoSoBenhAn> q = find(HoSoBenhAn.class).where(QHoSoBenhAn.hoSoBenhAn.benhNhan.id.eq(hoSoThongTin.getId()));
		q.orderBy(QHoSoBenhAn.hoSoBenhAn.ngayTao.desc());
		return q;
	}

	
	
}
