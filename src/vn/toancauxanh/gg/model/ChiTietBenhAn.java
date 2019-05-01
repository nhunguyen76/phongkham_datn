package vn.toancauxanh.gg.model;

import java.io.IOException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import vn.toancauxanh.model.Model;
import vn.toancauxanh.service.HoSoThongTinService;

@Entity
@Table(name = "chitietbenhan")
public class ChiTietBenhAn extends Model<ChiTietBenhAn> {

	private HoSoBenhAn benhAn;
	private Date ngayKham;
	private String chanDoan;
	private String thuThuat;
	private String ghiChu;
	private HoSoThongTin bacSi;

	@ManyToOne
	public HoSoBenhAn getBenhAn() {
		return benhAn;
	}

	public void setBenhAn(HoSoBenhAn benhAn) {
		this.benhAn = benhAn;
	}

	public Date getNgayKham() {
		return ngayKham;
	}

	public void setNgayKham(Date ngayKham) {
		this.ngayKham = ngayKham;
	}

	@Column(columnDefinition="TEXT")
	public String getChanDoan() {
		return chanDoan;
	}

	public void setChanDoan(String chanDoan) {
		this.chanDoan = chanDoan;
	}

	@Column(columnDefinition="TEXT")
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

	@ManyToOne
	public HoSoThongTin getBacSi() {
		return bacSi;
	}

	public void setBacSi(HoSoThongTin bacSi) {
		this.bacSi = bacSi;
	}
	
	@Command
	public void saveChiTietBenhAn(@BindingParam("wdn") final Window wdn, 
			@BindingParam("vmArgs") Object object,
			@BindingParam("attr") final String attr)
			throws IOException {
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, object, attr);
	}

}
