package vn.toancauxanh.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Default;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.model.NguoiDung;
import vn.toancauxanh.model.QNguoiDung;
import vn.toancauxanh.model.QVaiTro;
import vn.toancauxanh.model.VaiTro;

public final class NhanVienService extends BasicService<NguoiDung> {

	public NguoiDung getNhanVien(boolean saving) {
		if (Executions.getCurrent() == null) {
			return null;
		}
		return getNhanVien(saving, (HttpServletRequest) Executions.getCurrent().getNativeRequest(),
				(HttpServletResponse) Executions.getCurrent().getNativeResponse());
	}

	public JPAQuery<NguoiDung> getTargetQueryNhanVien() {
		String paramTrangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "").trim();
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		Long paramVaiTro = (Long) argDeco().get(Labels.getLabel("param.vaitro"));
		Long paramPhongBan = (Long) argDeco().get(Labels.getLabel("param.phongban"));

		JPAQuery<NguoiDung> q = find(NguoiDung.class).where(QNguoiDung.nguoiDung.trangThai.ne(core().TT_DA_XOA));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			q.where(QNguoiDung.nguoiDung.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QNguoiDung.nguoiDung.tenDangNhap.containsIgnoreCase(tuKhoa)));
		}

		if (paramVaiTro != null) {
			VaiTro vaiTro = find(VaiTro.class).where(QVaiTro.vaiTro.id.eq(paramVaiTro)).fetchFirst();
			q.where(QNguoiDung.nguoiDung.vaiTros.contains(vaiTro));
		}

		if (paramTrangThai != null && !paramTrangThai.isEmpty()) {
			q.where(QNguoiDung.nguoiDung.trangThai.eq(paramTrangThai));
		}
		q.orderBy(QNguoiDung.nguoiDung.trangThai.asc());
		return q.orderBy(QNguoiDung.nguoiDung.ngaySua.desc());
	}

	@Command
	public void login(@BindingParam("email") final String email, @BindingParam("password") final String password) {
		NguoiDung nhanVien = new JPAQuery<NguoiDung>(em()).from(QNguoiDung.nguoiDung)
				.where(QNguoiDung.nguoiDung.daXoa.isFalse()).where(QNguoiDung.nguoiDung.trangThai.ne(core().TT_DA_XOA))
				.where(QNguoiDung.nguoiDung.tenDangNhap.eq(email))
				.fetchFirst();
		BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
		if (nhanVien != null
				&& encryptor.checkPassword(password.trim() + nhanVien.getSalkey(), nhanVien.getMatKhau())) {
			String cookieToken = nhanVien
					.getCookieToken(System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(6, TimeUnit.HOURS));
			Session zkSession = Sessions.getCurrent();
			zkSession.setAttribute("email", cookieToken);
			HttpServletResponse res = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
			Cookie cookie = new Cookie("email", cookieToken);
			cookie.setPath("/");
			cookie.setMaxAge(1000000000);
			res.addCookie(cookie);
			Date thoiGian = new Date();
			Executions.sendRedirect("/");
		} else {
			showNotification("Đăng nhập không thành công", "", "error");
		}
	}

	@Command
	public void logout() {
		NguoiDung NhanVienLogin = getNhanVien(true);
		if (NhanVienLogin != null && !NhanVienLogin.noId()) {
			Session zkSession = Sessions.getCurrent();
			zkSession.removeAttribute("email");
			HttpServletResponse res = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
			Cookie cookie = new Cookie("email", null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			res.addCookie(cookie);
			Executions.sendRedirect("/login");
		}
	}

	public List<NguoiDung> getTacGias() {
		// TODO add them dk nhan vien là tác giả
		return getTargetQueryNhanVien().fetch();
	}

	public List<NguoiDung> getTacGiasAndNull() {
		// TODO add them dk nhan vien là tác giả
		List<NguoiDung> list = new ArrayList<>();
		list.add(null);
		list.addAll(getTargetQueryNhanVien().fetch());
		return list;
	}
	
	public List<NguoiDung> getListNguoiDungs() {
		List<NguoiDung> list = new ArrayList<NguoiDung>();
		list = find(NguoiDung.class).fetch();
		return list;
	}
	
	public List<NguoiDung> getListNguoiDungsAndNull() {
		List<NguoiDung> list = new ArrayList<NguoiDung>();
		list.add(null);
		list.addAll(getListNguoiDungs());
		return list;
	}

	private List<NguoiDung> tacGiasTimKiem = new ArrayList<>();

	public List<NguoiDung> getTacGiasTimKiem() {
		if (tacGiasTimKiem.size() == 0) {
			tacGiasTimKiem = getTacGiasAndNull();
		}
		return tacGiasTimKiem;
	}

	@Command
	public void timKiems(@BindingParam("hoTenTacGia") @Default(value = "") final String name,
			@BindingParam("baiViet") final Object bv) {

		if (name.isEmpty()) {
			tacGiasTimKiem = getTacGiasAndNull();
		} else {
			tacGiasTimKiem.clear();
			tacGiasTimKiem.addAll(find(NguoiDung.class).where(QNguoiDung.nguoiDung.trangThai.ne(core().TT_DA_XOA))
					.where(QNguoiDung.nguoiDung.hoVaTen.like("%" + name + "%")).orderBy(QNguoiDung.nguoiDung.hoVaTen.asc())
					.fetch());
		}
		BindUtils.postNotifyChange(null, null, this, "tacGiasTimKiem");
	}
	
	@Command
	public void logoutNotRedirect(HttpServletRequest req, HttpServletResponse res) {
		NguoiDung nhanVienLogin = getNhanVien(false, false, req, res);
		if (nhanVienLogin != null && !nhanVienLogin.noId()) {
			HttpSession zkSession=req.getSession();
 			zkSession.removeAttribute("email");
 			Cookie cookie = new Cookie("email", null);
 			cookie.setPath("/");
 			cookie.setMaxAge(0);
 			res.addCookie(cookie);
 			Date thoiGian = new Date();
			try {
//				res.sendRedirect(Utils.getLogoutCasUrl());
				res.sendRedirect(req.getContextPath()+"/login");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
//				res.sendRedirect(req.getContextPath()+ "/cas/login");
				res.sendRedirect(req.getContextPath()+"/login");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
