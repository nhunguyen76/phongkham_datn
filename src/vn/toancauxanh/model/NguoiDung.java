package vn.toancauxanh.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.sys.ValidationMessages;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;
import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.HoSoThongTin;
import vn.toancauxanh.gg.model.QHoSoThongTin;
import vn.toancauxanh.service.Quyen;

@Entity
@Table(name = "nguoidung", indexes = { @Index(columnList = "email"),
		@Index(columnList = "hinhDaiDien"), @Index(columnList = "tenDangNhap"), @Index(columnList = "checkKichHoat") })
public class NguoiDung extends Model<NguoiDung> {

	public static transient final Logger LOG = LogManager.getLogger(NguoiDung.class.getName());
	public static final String ADMIN = "admin";
	private String email = "";
	private String hinhDaiDien = "";
	private String matKhau = "";
	private String matKhau2 = "";
	private String salkey = "";
	private String tenDangNhap = "";
	private Date ngaySinh;
	private Set<String> quyens = new HashSet<>();
	private Set<String> tatCaQuyens = new HashSet<>();
	private Set<VaiTro> vaiTros = new HashSet<>();
	private Image imageContent;
	private String iconName = "";
	private String iconUrl = "";

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	@Transient
	private boolean flagImage = true;

	public void setImageContent(org.zkoss.image.Image _imageContent) {
		this.imageContent = _imageContent;
	}

	@Transient
	public org.zkoss.image.Image getImageContent() throws FileNotFoundException, IOException {
		if (imageContent == null && !core().TT_DA_XOA.equals(getTrangThai())) {
			if (flagImage) {
				loadImageIsView();
			}
		}
		return imageContent;
	}

	private void loadImageIsView() throws FileNotFoundException, IOException {
		String imgName = "";
		String path = "";
		path = folderStore() + getIconName();
		if (!"".equals(getIconUrl()) && new File(path).exists()) {
			try (FileInputStream fis = new FileInputStream(new File(path));) {
				setImageContent(new AImage(imgName, fis));
			}
		} else {
			String filePath = Executions.getCurrent().getDesktop().getWebApp()
					.getRealPath("/backend/assets/images/man.png");
			try (FileInputStream fis = new FileInputStream(new File(filePath));) {
				setImageContent(new AImage("imge", fis));
			}
		}
	}

	private boolean beforeSaveImg() throws IOException {
		if (getImageContent() == null) {
			return false;
		}
		saveImageToServer();
		return true;
	}

	@Command
	public void attachImages(@BindingParam("media") final Media media,
			@BindingParam("vmsgs") final ValidationMessages vmsgs) {
		if (media instanceof org.zkoss.image.Image) {
			if (media.getName().toLowerCase().endsWith(".png") || media.getName().toLowerCase().endsWith(".jpg")) {
				int lengthOfImage = media.getByteData().length;
				if (lengthOfImage > 10000000) {
					showNotification("Chọn hình ảnh có dung lượng nhỏ hơn 10MB.", "", "error");
					return;
				} else {
					String tenFile = media.getName();

					tenFile = tenFile.replace(" ", "");
					tenFile = unAccent(tenFile.substring(0, tenFile.lastIndexOf("."))) + "_"
							+ Calendar.getInstance().getTimeInMillis() + tenFile.substring(tenFile.lastIndexOf("."));
					setImageContent((org.zkoss.image.Image) media);
					setIconName(tenFile);
					if (vmsgs != null) {
						vmsgs.clearKeyMessages("errLabel");
					}
					BindUtils.postNotifyChange(null, null, this, "imageContent");
					BindUtils.postNotifyChange(null, null, this, "iconname");
				}
			} else {
				showNotification("Chọn hình ảnh theo đúng định dạng (*.png, *.jpg)", "", "error");
			}
		} else {
			showNotification("Không phải hình ảnh", "", "warning");
		}
	}
	
	@Command
	public void getAttachImages(@BindingParam("media") final Media media,
			@BindingParam("vmsgs") final ValidationMessages vmsgs) {
		if (media instanceof org.zkoss.image.Image) {
			if (media.getName().toLowerCase().endsWith(".png") || media.getName().toLowerCase().endsWith(".jpg")) {
				int lengthOfImage = media.getByteData().length;
				if (lengthOfImage > 10000000) {
					showNotification("Chọn hình ảnh có dung lượng nhỏ hơn 10MB.", "", "error");
					return;
				} else {
					String tenFile = media.getName();

					tenFile = tenFile.replace(" ", "");
					tenFile = unAccent(tenFile.substring(0, tenFile.lastIndexOf("."))) + "_"
							+ Calendar.getInstance().getTimeInMillis() + tenFile.substring(tenFile.lastIndexOf("."));
					setImageContent((org.zkoss.image.Image) media);
					setIconName(tenFile);
					if (vmsgs != null) {
						vmsgs.clearKeyMessages("errLabel");
					}
					BindUtils.postNotifyChange(null, null, this, "imageContent");
					BindUtils.postNotifyChange(null, null, this, "iconname");
				}
			} else {
				showNotification("Chọn hình ảnh theo đúng định dạng (*.png, *.jpg)", "", "error");
			}
		} else {
			showNotification("Không phải hình ảnh", "", "warning");
		}
	}

	protected void saveImageToServer() throws IOException {

		Image imageContent2 = getImageContent();
		if (imageContent2 != null) {
			// luu hinh
			if (getIconName() != null && !getIconName().isEmpty()) {
				setIconUrl(folderImageUrl().concat(getIconName()));
				final File baseDir = new File(folderStore().concat(getIconName()));
				Files.copy(baseDir, imageContent2.getStreamData());
			}
		}
	}

	@Transient
	public String folderImageUrl() {
		return "/" + Labels.getLabel("filestore.folder") + "/chuyenvien/";
	}

	private Quyen quyen = new Quyen(new SimpleAccountRealm() {
		@Override
		protected AuthorizationInfo getAuthorizationInfo(final PrincipalCollection arg0) {
			final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.setStringPermissions(getTatCaQuyens());
			return info;
		}
	});

	@Override
	public String toString() {
		return super.toString() + " " + tenDangNhap + " " + getVaiTros() + " " + getTatCaQuyens();
	}

	public String getSalkey() {
		return salkey;
	}

	public void setSalkey(String salkey) {
		this.salkey = salkey;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	// @Fetch(FetchMode.SUBSELECT)
	@CollectionTable(name = "nguoidung_quyens", joinColumns = { @JoinColumn(name = "nguoiDung_id") })
	public Set<String> getQuyens() {
		return quyens;
	}

	@Transient
	public Set<String> getTatCaQuyens() {
		if (tatCaQuyens.isEmpty()) {
			tatCaQuyens.addAll(quyens);
			for (VaiTro vaiTro : vaiTros) {
				if (!vaiTro.getAlias().isEmpty()) {
					// tatCaQuyens.add(vaiTro.getAlias());
				}
				tatCaQuyens.addAll(vaiTro.getQuyens());
			}
			if (Labels.getLabel("email.superuser").equals(tenDangNhap)) {
				tatCaQuyens.add("*");
			}
		}
		return tatCaQuyens;
	}

	public void setQuyens(final Set<String> dsChoPhep) {
		quyens = dsChoPhep;
	}

	@Transient
	public String getVaiTroText() {
		String result = "";
		for (VaiTro vt : getVaiTros()) {
			result += (result.isEmpty() ? "" : ", ") + vt.getTenVaiTro();
		}
		return result;
	}

	@Transient
	public String getFirstAlias() {
		String result = "";
		for (VaiTro vt : getVaiTros()) {
			result = vt.getAlias();
			break;
		}
		return result;
	}

	public NguoiDung() {

	}

	public NguoiDung(NguoiDung nguoiDung) {
		this.tenDangNhap = nguoiDung.getTenDangNhap();
	}

	public NguoiDung(final String tenDangNhap_, final String _hoTen) {
		super();
		tenDangNhap = tenDangNhap_;
	}

	@Override
	public void doSave() {
		super.doSave();
	}

	public String getEmail() {
		return email;
	}

	public String getHinhDaiDien() {
		return hinhDaiDien;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public String getTenDangNhap() {
		return tenDangNhap;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "nguoidung_vaitro", joinColumns = { @JoinColumn(name = "nguoidung_id") }, inverseJoinColumns = {
			@JoinColumn(name = "vaitros_id") })
	// @Fetch(value = FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	public Set<VaiTro> getVaiTros() {
		return vaiTros;
	}

	public void setEmail(final String _email) {
		email = Strings.nullToEmpty(_email) != null ? Strings.nullToEmpty(_email).trim() : Strings.nullToEmpty(_email);
	}

	public void setHinhDaiDien(final String _hinhDaiDien) {
		hinhDaiDien = Strings.nullToEmpty(_hinhDaiDien);
	}

	public void setMatKhau(final String _matKhau) {
		matKhau = Strings.nullToEmpty(_matKhau) != null ? Strings.nullToEmpty(_matKhau).trim()
				: Strings.nullToEmpty(_matKhau);
	}

	public void setNgaySinh(final Date _ngaySinh) {
		ngaySinh = _ngaySinh;
	}

	public void setTenDangNhap(final String _tenDangNhap) {
		tenDangNhap = Strings.nullToEmpty(_tenDangNhap) != null ? Strings.nullToEmpty(_tenDangNhap).trim()
				: Strings.nullToEmpty(_tenDangNhap);
	}

	public void setVaiTros(final Set<VaiTro> vaiTros1) {
		vaiTros = vaiTros1;
	}

	@Transient
	public Quyen getTatCaQuyen() {
		return quyen;
	}

	@Transient
	public boolean isAdmin() {
		return core().getQuyen().get(ADMIN);
	}

	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				if (getVaiTros().size() == 0) {
					addInvalidMessage(ctx, "lblErrVaiTros", "Bạn phải chọn vai trò cho người dùng");
				}
			}
		};
	}

	@Transient
	public AbstractValidator getValidator(boolean isBackend) {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				if (isBackend && (getVaiTros() == null || getVaiTros().size() == 0)) {
					addInvalidMessage(ctx, "lblErrVaiTros", "Bạn phải chọn vai trò cho người dùng.");
				}
			}
		};
	}

	@Transient
	public AbstractValidator getValidatePassword() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				final Object mKhau = ctx.getValidatorArg("password");
				if (mKhau == null) {
				} else {
					Object pass = ctx.getProperty().getValue();
					if (pass == null) {
						pass = "";
					}
					if (mKhau.equals(pass)) {
					} else {
						addInvalidMessage(ctx, "Xác nhận mật khẩu không trùng khớp!");
					}
				}
			}
		};
	}

	@Command
	public void khoaThanhVien(@BindingParam("vm") final Object vm) {
		if ("admin".equals(getTenDangNhap())) {
			showNotification("Không thể khóa SuperUser", "", "warning");
		} else {
			Messagebox.show("Bạn muốn khóa nhân viên này?", "Xác nhận", Messagebox.CANCEL | Messagebox.OK,
					Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(final Event event) {
							if (Messagebox.ON_OK.equals(event.getName())) {
								setCheckApDung(false);
								save();
								BindUtils.postNotifyChange(null, null, vm, "targetQueryNguoiDung");
								BindUtils.postNotifyChange(null, null, vm, "targetQueryNguoiDungThuocPhongBan");
							}
						}
					});

		}
	}

	@Command
	public void moKhoaThanhVien(@BindingParam("vm") final Object vm) {
		Messagebox.show("Bạn muốn mở khóa nhân viên này?", "Xác nhận", Messagebox.CANCEL | Messagebox.OK,
				Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							setCheckApDung(true);
							save();
							BindUtils.postNotifyChange(null, null, vm, "targetQueryNguoiDung");
							BindUtils.postNotifyChange(null, null, vm, "targetQueryNguoiDungThuocPhongBan");
						}
					}
				});
	}

	private boolean checkKichHoat;

	public boolean isCheckKichHoat() {
		return checkKichHoat;
	}

	public void setCheckKichHoat(boolean checkKichHoat) {
		this.checkKichHoat = checkKichHoat;
	}

	@Command
	public void toggleLock(@BindingParam("list") final Object obj) {
		String dialogText = "";
		if (!checkKichHoat) {
			dialogText = "Bạn muốn ngưng kích hoạt người dùng đã chọn?";
		} else {
			dialogText = "Bạn muốn kích hoạt người dùng đã chọn?";
		}
		Messagebox.show(dialogText, "Xác nhận", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							if (checkKichHoat) {
								setCheckKichHoat(false);
							} else {
								setCheckKichHoat(true);
							}
							save();
							BindUtils.postNotifyChange(null, null, obj, "targetQueryNguoiDung");
						}
					}
				});
	}

	@Command
	public void deleteNguoiDungInListVaiTro(@BindingParam("vaitro") final VaiTro vt,
			@BindingParam("nguoidung") final NguoiDung nv) {
		Messagebox.show("Bạn có chắc chắn muốn xóa vai trò " + vt.getTenVaiTro() + " của nhân viên " ,
				"Xác nhận", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							vaiTros.remove(vt);
							save();
							BindUtils.postNotifyChange(null, null, vt, "listNguoiDung");
						}
					}
				});
	}

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Command
	public void saveNguoiDung(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("isUpdateInfo") final boolean isUpdateInfo, @BindingParam("wdn") final Window wdn)
			throws IOException {
		beforeSaveImg();
		if (matKhau2 != null && !matKhau2.isEmpty()) {
			updatePassword(matKhau2);
		}
		save();
		if (isUpdateInfo) {
			BindUtils.postNotifyChange(null, null, this, "*");
		} else {
			BindUtils.postNotifyChange(null, null, listObject, attr);
		}
		wdn.detach();
	}

	public String getCookieToken(long expire) {
		String token = getId() + ":" + expire + ":";
		return Base64.encodeBase64String(token.concat(DigestUtils.md5Hex(token + matKhau + ":" + salkey)).getBytes());
	}

	public void updatePassword(String pass) {
		BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
		String salkey = getSalkey();
		if (salkey == null || salkey.equals("")) {
			salkey = encryptor.encryptPassword((new Date()).toString());
		}
		String passNoHash = pass + salkey;
		String passHash = encryptor.encryptPassword(passNoHash);
		setSalkey(salkey);
		setMatKhau(passHash);
	}

	@Transient
	public List<Object> getListThongBao() {
		List<Object> list = new ArrayList<Object>();
		return list;
	}

	@Transient
	public AbstractValidator getValidatorEmail() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				String value = (String) ctx.getProperty().getValue();
				if (value == null || "".equals(value)) {
					addInvalidMessage(ctx, "error", "Không được để trống trường này");
				} else if (!value.trim().matches(".+@.+\\.[a-z]+")) {
					addInvalidMessage(ctx, "error", "Email không đúng định dạng");
				} else {
					JPAQuery<NguoiDung> q = find(NguoiDung.class).where(QNguoiDung.nguoiDung.email.eq(value))
							.where(QNguoiDung.nguoiDung.trangThai.ne(core().TT_DA_XOA));
					if (!NguoiDung.this.noId()) {
						q.where(QNguoiDung.nguoiDung.id.ne(getId()));
					}
					if (q.fetchCount() > 0) {
						addInvalidMessage(ctx, "error", "Email đã được sử dụng");
					}
				}
			}
		};
	}

	public boolean change = false;
	public boolean editable = false;

	@Transient
	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	@Transient
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Command
	public void ChangePassword() {
		setChange(isChange() ? false : true);
		BindUtils.postNotifyChange(null, null, this, "change");
	}

//	@Command
//	public void saveNguoiDung() {
//		save();
//		setChange(false);
//		setEditable(false);
//		BindUtils.postNotifyChange(null, null, this, "change");
//		BindUtils.postNotifyChange(null, null, this, "editable");
//	}

	@Command
	public void editableStatus() {
		setEditable(true);
		setChange(true);
		BindUtils.postNotifyChange(null, null, this, "editable");
		BindUtils.postNotifyChange(null, null, this, "change");
	}

	

	@Transient
	public AbstractValidator getValidatorTenDangNhap() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {
				String tenDangNhap = (String) ctx.getProperty().getValue();
				if (tenDangNhap.isEmpty()) {
					addInvalidMessage(ctx, "Không được để trống trường này.");
				} else if (StringUtils.isBlank(tenDangNhap)) {
					addInvalidMessage(ctx, "Không được để khoảng trắng.");
				} else {
					JPAQuery<NguoiDung> q = find(NguoiDung.class)
							.where(QNguoiDung.nguoiDung.tenDangNhap.eq(tenDangNhap));

					if (!NguoiDung.this.noId()) {
						q.where(QNguoiDung.nguoiDung.id.ne(getId()));
					}
					if (q.fetchCount() > 0) {
						addInvalidMessage(ctx, "Đã tồn tại tên này.");
					}
				}

			}
		};
	}

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
	public String getMatKhau2() {
		return matKhau2;
	}

	public void setMatKhau2(String matKhau2) {
		this.matKhau2 = matKhau2 != null ? matKhau2.trim() : matKhau2;
	}
	
	// Lay ho so thong tin theo ten dang nhap cua nguoi dung
	@Transient
	public HoSoThongTin getHoSoThongTin() {
		JPAQuery<HoSoThongTin> q = find(HoSoThongTin.class).where(QHoSoThongTin.hoSoThongTin.taiKhoan.eq(this));
		return q.fetchOne();
	}
	
	// get ho ten tuong ung
	@Transient
    public String getHoVaTen() {
        JPAQuery<HoSoThongTin> q = find(HoSoThongTin.class).where(QHoSoThongTin.hoSoThongTin.taiKhoan.eq(this));
        if (q.fetchOne() != null) {
            return q.fetchOne().getHoVaTen();
        }
        return this.tenDangNhap;
    }
}
