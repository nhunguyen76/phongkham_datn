package vn.toancauxanh.gg.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "chitietbenhan")
public class ChiTietBenhAn extends Model<ChiTietBenhAn> {

	private HoSoBenhAn benhAn;
	private Date ngayDieuTri;
	private String chanDoan;
	private String thuThuat;
	private String ghiChu;

	@ManyToOne
	public HoSoBenhAn getBenhAn() {
		return benhAn;
	}

	public void setBenhAn(HoSoBenhAn benhAn) {
		this.benhAn = benhAn;
	}

	public Date getNgayDieuTri() {
		return ngayDieuTri;
	}

	public void setNgayDieuTri(Date ngayDieuTri) {
		this.ngayDieuTri = ngayDieuTri;
	}

	public String getChanDoan() {
		return chanDoan;
	}

	public void setChanDoan(String chanDoan) {
		this.chanDoan = chanDoan;
	}

	public String getThuThuat() {
		return thuThuat;
	}

	public void setThuThuat(String thuThuat) {
		this.thuThuat = thuThuat;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

}
