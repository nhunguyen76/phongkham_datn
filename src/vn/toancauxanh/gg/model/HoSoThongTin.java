package vn.toancauxanh.gg.model;

import java.io.IOException;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.enums.GioiTinhEnum;
import vn.toancauxanh.model.Model;
import vn.toancauxanh.model.NguoiDung;

@Entity
@Table(name = "hosothongtin")
public class HoSoThongTin extends Model<HoSoThongTin> {

	private String hoVaTen;
	private Date ngaySinh;
	private String cmnd;
	private String diaChi;
	private String soDienThoai;
	private GioiTinhEnum gioiTinh;
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

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	@ManyToOne
	public HoSoThongTin getNguoiGiamHo() {
		return nguoiGiamHo;
	}

	public void setNguoiGiamHo(HoSoThongTin nguoiGiamHo) {
		this.nguoiGiamHo = nguoiGiamHo;
	}

	@Command
	public void saveHoSoThongTin(@BindingParam("wdn") final Window wdn, @BindingParam("vm") HoSoThongTin hoSoThongTin)
			throws IOException {
		if(nguoiGiamHo!=null) {
			nguoiGiamHo.save();
		}
		save();
	}

	@Transient
	public AbstractValidator getValidateHoVaTen() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
			}
		};
	}

	private boolean hasCmnd = true;

	@Transient
	public boolean getHasCmnd() {
		if(!hasCmnd) {
			nguoiGiamHo = new HoSoThongTin();
		}
		return hasCmnd;
	}

	public void setHasCmnd(boolean hasCmnd) {
		this.hasCmnd = hasCmnd;
	}

	@Enumerated(EnumType.STRING)
	public GioiTinhEnum getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(GioiTinhEnum gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	//Validation
	@Transient
	public AbstractValidator getValidatorStringUtitsNotBlank() {
		return new AbstractValidator() {
			@Override
			public void validate(ValidationContext ctx) {
				String value = (String) ctx.getProperty().getValue();
				if (value.isEmpty()) {
					addInvalidMessage(ctx, "Không được để trống trường này.");
				} else if (StringUtils.isBlank(value)) {
					addInvalidMessage(ctx, "Không được để khoảng trắng.");
				}
			}
		};
	}
	
	@Transient
	public AbstractValidator getValidatorCMND() {
		return new AbstractValidator() {
			@Override
			public void validate(ValidationContext ctx) {
				if(hasCmnd) {
					String value = (String) ctx.getProperty().getValue();
					if (value.isEmpty()) {
						addInvalidMessage(ctx, "Không được để trống trường này.");
					} else if (StringUtils.isBlank(value)) {
						addInvalidMessage(ctx, "Không được để khoảng trắng.");
					} else{
						JPAQuery<HoSoThongTin> q = find(HoSoThongTin.class).where(QHoSoThongTin.hoSoThongTin.cmnd.eq(value));
						if(!HoSoThongTin.this.noId()) {
							q.where(QHoSoThongTin.hoSoThongTin.id.ne(getId()));
						}
						if (q.fetchCount() > 0) {
							addInvalidMessage(ctx, "Đã tồn tại số CMND này.");
						}
					}
				}
				
				
			}
		};
	}
}
