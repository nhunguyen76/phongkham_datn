package vn.toancauxanh.cms.service;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CanLamSang;
import vn.toancauxanh.gg.model.ChiTietBenhAn;
import vn.toancauxanh.gg.model.DonThuoc;
import vn.toancauxanh.gg.model.HoSoBenhAn;
import vn.toancauxanh.gg.model.HoSoThongTin;
import vn.toancauxanh.gg.model.QCanLamSang;
import vn.toancauxanh.gg.model.QChiTietBenhAn;
import vn.toancauxanh.gg.model.QDonThuoc;
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
	
	public HoSoBenhAn getHoSoBenhAnById(Long id) {
		HoSoBenhAn entity = find(HoSoBenhAn.class).where(QHoSoBenhAn.hoSoBenhAn.id.eq(id)).fetchOne();
		return entity;
	}
	
	// Get list đơn thuốc của mỗi bệnh nhân
	public JPAQuery<DonThuoc> getListDonthuoc() {
		JPAQuery<DonThuoc> q = find(DonThuoc.class).where(QDonThuoc.donThuoc.benhNhan.id.eq(hoSoThongTin.getId()));
		q.orderBy(QDonThuoc.donThuoc.ngayXuat.desc());
		return q;
	}

	private Long selectedIdBenhAn;

	public Long getSelectedIdBenhAn() {
		return selectedIdBenhAn;
	}

	public void setSelectedIdBenhAn(Long selectedIdBenhAn) {
		this.selectedIdBenhAn = selectedIdBenhAn;
	}
	
	// lấy thông tin chi tiết bệnh án
	public JPAQuery<ChiTietBenhAn> getListChiTietBenhAnByIdBenhAn() {
		JPAQuery<ChiTietBenhAn> q = find(ChiTietBenhAn.class).where(QChiTietBenhAn.chiTietBenhAn.benhAn.id.eq(selectedIdBenhAn));
		q.orderBy(QChiTietBenhAn.chiTietBenhAn.ngayTao.desc());
		return q;
	}
	
	// lấy danh sách cận lâm sàng
	public JPAQuery<CanLamSang> getListCanLamSangByIdChiTietBenhAn() {
		JPAQuery<CanLamSang> q = find(CanLamSang.class).where(QCanLamSang.canLamSang.benhAn.id.eq(selectedIdBenhAn));
		q.orderBy(QCanLamSang.canLamSang.ngayTao.desc());
		return q;
	}
	
	// Show thông tin chi tiết bệnh án, cận lâm sàng
	@Command
	public void showChiTietBenhAn(@BindingParam ("idBenhAn") Long id) {
		this.selectedIdBenhAn = id;
		BindUtils.postNotifyChange(null, null, this, "selectedIdBenhAn");
		BindUtils.postNotifyChange(null, null, this, "listChiTietBenhAnByIdBenhAn");
		BindUtils.postNotifyChange(null, null, this, "listCanLamSangByIdChiTietBenhAn");
	}

}
