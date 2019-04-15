package vn.toancauxanh.gg.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

}
