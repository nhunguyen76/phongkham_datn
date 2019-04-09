package vn.toancauxanh.gg.model;

import java.io.IOException;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zul.Window;

import vn.toancauxanh.model.Model;
import vn.toancauxanh.model.NguoiDung;

@Entity
@Table(name = "hosothongtin")
public class HoSoThongTin extends Model<HoSoThongTin> {

	private String hoVaTen;
	private Date ngaySinh;
	private String cmnd;
	private String diaChi;
	private String soDiaChi;
	private HoSoThongTin nguoiGiamHo;
	private NguoiDung taiKhoan;

	public String getHoVaTen() {
		return hoVaTen;
	}

	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
	}

	
	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	@ManyToOne
	public NguoiDung getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(NguoiDung taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getSoDiaChi() {
		return soDiaChi;
	}

	public void setSoDiaChi(String soDiaChi) {
		this.soDiaChi = soDiaChi;
	}

	@ManyToOne
	public HoSoThongTin getNguoiGiamHo() {
		return nguoiGiamHo;
	}

	public void setNguoiGiamHo(HoSoThongTin nguoiGiamHo) {
		this.nguoiGiamHo = nguoiGiamHo;
	}
	
	@Command
	public void saveHoSoThongTin(@BindingParam("wdn") final Window wdn,
			@BindingParam("vm") HoSoThongTin hoSoThongTin) throws IOException {
		wdn.detach();
	}
	
	@Transient
	public AbstractValidator getValidateHoVaTen() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
			}
		};
	}

}
