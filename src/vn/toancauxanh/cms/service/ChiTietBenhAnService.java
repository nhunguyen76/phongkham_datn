package vn.toancauxanh.cms.service;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.ChiTietBenhAn;
import vn.toancauxanh.gg.model.QChiTietBenhAn;
import vn.toancauxanh.service.BasicService;

public class ChiTietBenhAnService extends BasicService<ChiTietBenhAn>{

	private Long idBenhAn;
	

	public Long getIdBenhAn() {
		return idBenhAn;
	}

	public void setIdBenhAn(Long idBenhAn) {
		this.idBenhAn = idBenhAn;
	}
	
	public JPAQuery<ChiTietBenhAn> getChiTietBenhAnByIdBenhAn(Long id) {
		JPAQuery<ChiTietBenhAn> q = find(ChiTietBenhAn.class).where(QChiTietBenhAn.chiTietBenhAn.benhAn.id.eq(id));
		q.orderBy(QChiTietBenhAn.chiTietBenhAn.ngayKham.desc());
		return q;
	}
	
	public JPAQuery<ChiTietBenhAn> getChiTietBenhAn() {
		JPAQuery<ChiTietBenhAn> q = find(ChiTietBenhAn.class).where(QChiTietBenhAn.chiTietBenhAn.benhAn.id.eq(this.idBenhAn));
		q.orderBy(QChiTietBenhAn.chiTietBenhAn.ngayKham.desc());
		return q;
	}

}
