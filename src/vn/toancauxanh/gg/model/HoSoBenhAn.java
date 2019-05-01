package vn.toancauxanh.gg.model;

import java.io.IOException;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "hosobenhan")
public class HoSoBenhAn extends Model<HoSoBenhAn> {

	private HoSoThongTin benhNhan;
	private DichVu dichVu;
	private Date ngayBatDau;

	@ManyToOne
	public HoSoThongTin getBenhNhan() {
		return benhNhan;
	}

	public void setBenhNhan(HoSoThongTin benhNhan) {
		this.benhNhan = benhNhan;
	}

	@ManyToOne
	public DichVu getDichVu() {
		return dichVu;
	}

	public void setDichVu(DichVu dichVu) {
		this.dichVu = dichVu;
	}

	public Date getNgayBatDau() {
		return ngayBatDau;
	}

	public void setNgayBatDau(Date ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	@Command
	public void saveHoSoBenhAn(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn,
			@BindingParam("vm") HoSoBenhAn hoSoBenhAn)
			throws IOException {
		System.out.println("objeclis" + listObject);
		System.out.println("attr" + attr);
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	
	private Long selectedIdBenhAn;

	@Transient
	public Long getSelectedIdBenhAn() {
		return selectedIdBenhAn;
	}

	public void setSelectedIdBenhAn(Long selectedIdBenhAn) {
		this.selectedIdBenhAn = selectedIdBenhAn;
	}
	
	@Transient
	public JPAQuery<ChiTietBenhAn> getListChiTietBenhAnByIdBenhAn() {
		JPAQuery<ChiTietBenhAn> q = find(ChiTietBenhAn.class).where(QChiTietBenhAn.chiTietBenhAn.benhAn.id.eq(selectedIdBenhAn));
		q.orderBy(QChiTietBenhAn.chiTietBenhAn.ngayTao.desc());
		return q;
	}
	
	@Command
	public void showChiTietBenhAn(@BindingParam ("idBenhAn") Long id, @BindingParam("vmArgs") final Object vmArgs) {
		this.selectedIdBenhAn = id;
		BindUtils.postNotifyChange(null, null, vmArgs, "listChiTietBenhAnByIdBenhAn");
	}
}
