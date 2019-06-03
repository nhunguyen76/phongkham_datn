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
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.enums.GioiTinhEnum;
import vn.toancauxanh.model.Model;
import vn.toancauxanh.model.NguoiDung;
import vn.toancauxanh.service.HoSoThongTinService;

@Entity
@Table(name = "hosothongtin")
public class HoSoThongTin extends Model<HoSoThongTin> {

	private String maCaNhan;
	private String hoVaTen;
	private Date ngaySinh;
	private String cmnd;
	private String diaChi;
	private String soDienThoai;
	private GioiTinhEnum gioiTinh;
	private NguoiDung taiKhoan = new NguoiDung();

	public HoSoThongTin() {
		super();
	}
	
	public String getHoVaTen() {
		return hoVaTen;
	}

	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen != null ? hoVaTen.trim() : hoVaTen;
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
		this.cmnd = cmnd != null ? cmnd.trim() : cmnd;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi != null ? diaChi.trim() : diaChi;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai != null ? soDienThoai.trim() : soDienThoai;
	}
	
	public String getMaCaNhan() {
		return maCaNhan;
	}

	public void setMaCaNhan(String maCaNhan) {
		this.maCaNhan = maCaNhan != null ? maCaNhan.trim() : maCaNhan;
	}
	
	@Transient
	public void setTuDongMaCaNhan() {
		this.maCaNhan = "ND" + this.getId();
	}
	
	

	// Command save
	

	@Command
	public void saveThongTinBenhNhan(@BindingParam("wdn") final Window wdn, @BindingParam("vm") HoSoThongTin hoSoThongTin)
			throws IOException {
		taiKhoan.saveNguoiDungNotShowNotification(null, null, true, wdn);
		if(this.noId() || this.getId() == 0) {
			saveNotShowNotification();
			setTuDongMaCaNhan();
			save();
			wdn.detach();
			redirectPageSession("/hosobenhnhan/id", this, new HoSoThongTinService());
		} else {
			save();
			wdn.detach();
		}
	}
	
	@Command
	public void saveThongTinNhanVien(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn)
			throws IOException {
		taiKhoan.saveNguoiDung(null, null, true, wdn);
		if(this.noId() || this.getId() == 0) {
			save();
			setTuDongMaCaNhan();
			save();
		} else {
			save();
		}
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}

	@Transient
	public AbstractValidator getValidateHoVaTen() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
			}
		};
	}

	@Enumerated(EnumType.STRING)
	public GioiTinhEnum getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(GioiTinhEnum gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	@Transient
	public HoSoThongTin getById(long id) {
		if(id>0) {
			return find(HoSoThongTin.class).where(QHoSoThongTin.hoSoThongTin.id.eq(id)).fetchFirst();
		}else {
			return new HoSoThongTin();
		}
	} 
	
	/**
	 * Phần xử lsy hiển thị chi tiết bệnh án
	 */
	// Khởi tạo hiển thị chi tiết theo id bệnh án đầu tiên
	private Long selectedIdHoSoBenhAn = 0L;

	@Transient
	public Long getSelectedIdHoSoBenhAn() {
		return selectedIdHoSoBenhAn;
	}

	public void setSelectedIdHoSoBenhAn(Long selectedIdHoSoBenhAn) {
		this.selectedIdHoSoBenhAn = selectedIdHoSoBenhAn;
	}
	
	@Transient
	public JPAQuery<ChiTietBenhAn> getChiTietBenhAn() {
		JPAQuery<ChiTietBenhAn> q = find(ChiTietBenhAn.class).where(QChiTietBenhAn.chiTietBenhAn.benhAn.id.eq(selectedIdHoSoBenhAn));
		q.orderBy(QChiTietBenhAn.chiTietBenhAn.ngayTao.desc());
		return q;
	}
	
	@Transient
	public JPAQuery<HoSoBenhAn> getListHoSoBenhAn() {
		JPAQuery<HoSoBenhAn> q = find(HoSoBenhAn.class).where(QHoSoBenhAn.hoSoBenhAn.benhNhan.id.eq(this.getId()));
		q.orderBy(QHoSoBenhAn.hoSoBenhAn.ngayTao.desc());
		return q;
	}
	
	@Command
	public void showChiTietBenhAn(@BindingParam("idBenhAn") Long id)
			throws IOException {
		System.out.println("Đã vào đây" + id);
		this.selectedIdHoSoBenhAn = id;
		BindUtils.postNotifyChange(null, null, this, "chiTietBenhAn");
	}
	
	///
	
	@Command
	public void redirectPageSession(@BindingParam("url") String url, @BindingParam("vm") HoSoThongTin vm, @BindingParam("service") HoSoThongTinService hoSoThongTinService) {
		if (vm != null) {
			url = url.concat("/" + vm.getId());
		}
		putArgSessionPage();
		Executions.getCurrent().sendRedirect(url);
	}
	
	public void putArgSessionPage() {
		Session session = Sessions.getCurrent();
		// chỗ này là chỗ lưu session này, lưu cái getarg là lưu cả cái page
		session.setAttribute("tiemPhongVM", getArg());
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
				String value = (String) ctx.getProperty().getValue();
				if (!value.isEmpty()) {
					JPAQuery<HoSoThongTin> q = find(HoSoThongTin.class).where(QHoSoThongTin.hoSoThongTin.cmnd.eq(value));
					if (!HoSoThongTin.this.noId()) {
						q.where(QHoSoThongTin.hoSoThongTin.id.ne(getId()));
					}
					if (q.fetchCount() > 0) {
						addInvalidMessage(ctx, "Đã tồn tại số CMND này.");
					}
				}
			}
		};
	}
	
	@Transient
	public AbstractValidator getValidatorMaCaNhan() {
		return new AbstractValidator() {
			@Override
			public void validate(ValidationContext ctx) {
				String value = (String) ctx.getProperty().getValue();
				if (value.isEmpty()) {
					addInvalidMessage(ctx, "Không được để trống trường này.");
				} else if (StringUtils.isBlank(value)) {
					addInvalidMessage(ctx, "Không được để khoảng trắng.");
				} else {
					JPAQuery<HoSoThongTin> q = find(HoSoThongTin.class)
							.where(QHoSoThongTin.hoSoThongTin.maCaNhan.eq(value));
					if (!HoSoThongTin.this.noId()) {
						q.where(QHoSoThongTin.hoSoThongTin.id.ne(getId()));
					}
					if (q.fetchCount() > 0) {
						addInvalidMessage(ctx, "Đã tồn tại mã cá nhân này.");
					}
				}
			}
		};
	}
	
	@Transient
	public AbstractValidator getValidatorSoDienThoai() {
		return new AbstractValidator() {
			@Override
			public void validate(ValidationContext ctx) {
				String value = (String) ctx.getProperty().getValue();
				if (value.isEmpty()) {
					addInvalidMessage(ctx, "Không được để trống trường này.");
				} else if (StringUtils.isBlank(value)) {
					addInvalidMessage(ctx, "Không được để khoảng trắng.");
				} else if (!value.trim().matches("^\\+?\\d{1,3}?[- .]?\\(?(?:\\d{2,3})\\)?[- .]?\\d\\d\\d[- .]?\\d\\d\\d\\d$")) {
						addInvalidMessage(ctx, "error", "Số điện thoại không đúng định dạng.");
				}
			}
		};
	}
	
	@Command
	public void khoaTaiKhoan(@BindingParam("vm") final Object vm) {
		System.out.println("=============================" + vm + "==============");
		Messagebox.show("Bạn muốn khóa tài khoản này?", "Xác nhận", Messagebox.CANCEL | Messagebox.OK,
				Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							taiKhoan.setCheckApDung(false);
							taiKhoan.save();
							BindUtils.postNotifyChange(null, null, vm, "targetQuery");
						}
					}
				});
	}
	
	@Command
	public void moKhoaTaiKhoan(@BindingParam("vm") final Object vm) {
		Messagebox.show("Bạn muốn mở khóa tài khoản này?", "Xác nhận", Messagebox.CANCEL | Messagebox.OK,
				Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							taiKhoan.setCheckApDung(true);
							taiKhoan.save();
							BindUtils.postNotifyChange(null, null, vm, "targetQuery");
						}
					}
				});
	}
	
}
