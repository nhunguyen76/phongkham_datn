package vn.toancauxanh.service;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Nullable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;

import vn.toancauxanh.model.NguoiDung;

public class BasicService<T> extends BaseObject<T> {

	private @Nullable Date tuNgay;
	private @Nullable Date denNgay;

	public final Quyen getQuyen() {
		return new Quyen(core().getQuyen().getRealm(),
				MapUtils.getString(argDeco(), Labels.getLabel("param.resource"), ""));
	}

	public @Nullable Date getTuNgay() {
		return tuNgay;
	}

	public void setTuNgay(Date _tuNgay) {
		this.tuNgay = _tuNgay;
	}

	public @Nullable Date getFixTuNgay() {
		Date fixTuNgay = tuNgay;
		if (fixTuNgay != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fixTuNgay);
			//cal.add(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			fixTuNgay = cal.getTime();
		}
		return fixTuNgay;
	}

	public @Nullable Date getDenNgay() {
		return denNgay;
	}

	public void setDenNgay(Date _denNgay) {
		this.denNgay = _denNgay;
	}

	public @Nullable Date getFixDenNgay() {
		Date fixDenNgay = denNgay;
		if (getDenNgay() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fixDenNgay);
			//cal.add(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			fixDenNgay = cal.getTime();
		}
		return fixDenNgay;
	}
	
	public NguoiDung getNguoiDung(boolean isSave, boolean toLoginIfNull, HttpServletRequest req, HttpServletResponse res) {
		NguoiDung nguoiDung = null;
		String key = getClass() + "." + NguoiDung.class;
		nguoiDung = (NguoiDung) req.getAttribute(key);
		if (nguoiDung == null || nguoiDung.noId()) {
			Object token = null;
			Cookie[] cookies = req.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if ("email".equals(c.getName())) {
						token = c.getValue();
						break;
					}
				}
			}
			if (token == null) {
				HttpSession ses = req.getSession();
				token = ses.getAttribute("email");
			}
			if (token != null) {
				String[] parts = new String(Base64.decodeBase64(token.toString())).split(":");
				NguoiDung nguoiDungLogin = em().find(NguoiDung.class, NumberUtils.toLong(parts[0], 0));
				if (parts.length == 3 && nguoiDungLogin != null) {
					long expire = NumberUtils.toLong(parts[1], 0);
					if (expire > System.currentTimeMillis() && token.equals(nguoiDungLogin.getCookieToken(expire))) {
						nguoiDung = nguoiDungLogin;
					}
				}
			}
			if (!isSave && (nguoiDung == null)) {
				if (nguoiDung == null) {
					bootstrapNguoiDung();
				}
				nguoiDung = new NguoiDung();
				if (token != null) {
					req.getSession().removeAttribute("email");
				}
				if(toLoginIfNull) {
					redirectLogin(req, res);
				}
			}
			req.setAttribute(key, nguoiDung);
		}
		return isSave && nguoiDung != null && nguoiDung.noId() ? null : nguoiDung;
	}

}
