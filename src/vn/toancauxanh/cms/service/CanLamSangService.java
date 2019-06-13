package vn.toancauxanh.cms.service;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CanLamSang;
import vn.toancauxanh.gg.model.QCanLamSang;
import vn.toancauxanh.gg.model.QHoSoBenhAn;
import vn.toancauxanh.service.BasicService;

public class CanLamSangService extends BasicService<CanLamSang> {

	private Long idBenhAn;
	
	public Long getIdBenhAn() {
		return idBenhAn;
	}


	public void setIdBenhAn(Long idBenhAn) {
		this.idBenhAn = idBenhAn;
	}
	
	public JPAQuery<CanLamSang> getListCanLamSangByIdChiTietBenhAn() {
		JPAQuery<CanLamSang> q = find(CanLamSang.class).where(QCanLamSang.canLamSang.benhAn.id.eq(idBenhAn));
		q.orderBy(QCanLamSang.canLamSang.ngayKham.desc());
		return q;
	}


	
}
