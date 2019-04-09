package vn.toancauxanh.service;

import java.text.Normalizer;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.persistence.Transient;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.SystemPropertyUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Default;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.greenglobal.core.CoreObject;
import vn.toancauxanh.cms.service.HomeService;
import vn.toancauxanh.gg.model.enums.NhomGopY;
import vn.toancauxanh.model.NguoiDung;
import vn.toancauxanh.model.QNguoiDung;
import vn.toancauxanh.model.Setting;

public class BaseObject<T> extends CoreObject<T> {
	
	@Override
	public Map<Object, Object> getArg() {
		Map<Object, Object> arg = super.getArg();
		return arg;
	}

	public void setActivePage(int value) {
		getArg().put(SystemPropertyUtils.resolvePlaceholders(PH_KEYPAGE), value + 1);
	}

	public <A> JPAQuery<A> page1(JPAQuery<A> q) {
		String kPage = SystemPropertyUtils.resolvePlaceholders(PH_KEYPAGE);
		int len = MapUtils.getIntValue(getArg(), SystemPropertyUtils.resolvePlaceholders(PH_KEYPAGESIZE));
		int page = Math.max(0, MapUtils.getIntValue(getArg(), kPage) - 1);
		if (q.fetchCount() <= page * len) {
			getArg().put(kPage, page = 0);
			BindUtils.postNotifyChange(null, null, getArg(), kPage);
		}
		return q.offset(page * len).limit(len);
	}

	public <A> JPAQuery<A> pageVideo(JPAQuery<A> q) {
		int len = 9;
		String kPage = SystemPropertyUtils.resolvePlaceholders(PH_KEYPAGE);
		int page = Math.max(0, MapUtils.getIntValue(getArg(), kPage) - 1);
		if (q.fetchCount() <= page * len) {
			getArg().put(kPage, page = 0);
			BindUtils.postNotifyChange(null, null, getArg(), kPage);
		}
		return q.offset(page * len).limit(len);
	}

	@Command
	public final void cmd(@BindingParam("ten") @Default(value = "") final String ten,
			@BindingParam("notify") Object beanObject, @BindingParam("attr") @Default(value = "*") String fields) {
		invoke(null, ten, null, beanObject, fields, null, false);
	}

	@Transient
	public Entry core() {
		return Entry.instance;
	}

	public Date date(Object key) {
		Object result = argDeco().get(key);
		if (!(result instanceof Date) && result != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CoreObject.DATE_FORMAT);
			result = simpleDateFormat.parse(result.toString(), new ParsePosition(0));
		}
		if (result != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime((Date) result);
			cal.add(Calendar.HOUR, 7);
			result = cal.getTime();
		}
		return (Date) result;
	}
	
	@Transient
	public final HomeService getHomeService() {
		return new HomeService();
	}


	public NguoiDung getNguoiDung() {
		return fetchNguoiDung(false);
	}

	public NguoiDung fetchNguoiDungSaving() {
		return fetchNguoiDung(true);
	}

	public NguoiDung fetchNguoiDung(boolean saving) {
		if (Executions.getCurrent() == null) {
			return null;
		}
		return getNguoiDung(saving, (HttpServletRequest) Executions.getCurrent().getNativeRequest(),
				(HttpServletResponse) Executions.getCurrent().getNativeResponse());
	}

	public NguoiDung getNguoiDung(boolean isSave, HttpServletRequest req, HttpServletResponse res) {
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
//					if (expire > System.currentTimeMillis() && token.equals(nguoiDungLogin.getCookieToken(expire))) {
					if (expire > System.currentTimeMillis()) {
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
				redirectLogin(req, res);
			}
			req.setAttribute(key, nguoiDung);
		}

		return isSave && nguoiDung != null && nguoiDung.noId() ? null : nguoiDung;
	}
	
	public void redirectLogin(HttpServletRequest req, HttpServletResponse res) {
		StringBuilder url = new StringBuilder();
		url.append(req.getScheme()) // http (https)
				.append("://") // ://
				.append(req.getServerName()); // localhost (domain name)
		if (req.getServerPort() != 80 && req.getServerPort() != 443) {
			url.append(":").append(req.getServerPort()); // app name
		}
		try {
//			res.sendRedirect(url + req.getContextPath() + "/dang-nhap-sso");
			res.sendRedirect(url + req.getContextPath() + "/login");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void bootstrapNguoiDung() {
		JPAQuery<NguoiDung> q = find(NguoiDung.class)
				.where(QNguoiDung.nguoiDung.daXoa.isFalse())
				.where(QNguoiDung.nguoiDung.trangThai.eq(core().TT_AP_DUNG));
		if (q.fetchCount() == 0) {
			final NguoiDung NguoiDung = new NguoiDung("admin", "Super Admin");
			NguoiDung.getQuyens().add("*");
			NguoiDung.updatePassword("tcx@123");
			NguoiDung.saveNotShowNotification();
		}
	}

	@Transient
	public NguoiDungService getNguoiDungs() {
		return new NguoiDungService();
	}

	@Transient
	public Setting getSetting() {
		String key = BaseObject.class + "." + Setting.class;
		Setting result = (Setting) Executions.getCurrent().getAttribute(key);
		if (result == null || result.noId()) {
			result = find(Setting.class).fetchFirst();
			if (result == null) {
				result = new Setting();
				result.save();
			}
			Executions.getCurrent().setAttribute(key, result);
		}
		return result;
	}

	@Transient
	public final Map<String, String> getTinhTrangTBAndNull() {
		HashMap<String, String> result = new HashMap<>();
		result.put(null, "");
		result.put("moi", "Đã gửi");
		result.put("dang_soan", "Đang soạn");
		return result;
	}
	
	@Transient
	public final Map<String, String> getTrangThaiList() {
		HashMap<String, String> result = new HashMap<>();
		result.put("khong_ap_dung", "Không áp dụng");
		result.put("ap_dung", "Áp dụng");
		return result;
	}

	@Transient
	public final Map<String, String> getTrangThaiListAndNull() {
		HashMap<String, String> result = new HashMap<>();
		result.put(null, "");
		result.put("khong_ap_dung", "Không áp dụng");
		result.put("ap_dung", "Áp dụng");
		return result;
	}
	
	@Transient
	public final Map<String, String> getTinhTrangListAndNull() {
		HashMap<String, String> result = new HashMap<>();
		result.put(null, "");
		result.put("nhap", "Bản nháp");
		result.put("dang_xu_ly", "Chưa xử lý");
		result.put("da_xu_ly", "Đã xử lý");
		result.put("hoan_tat", "Đã xử lý dứt điểm");
		result.put("tre_han", "Trễ hạn");
		return result;
	}
	
	@Transient
	public final Map<String, String> getNguoiDungTrangThaiListAndNull() {
		HashMap<String, String> result = new HashMap<>();
		result.put(null, "");
		result.put("khong_ap_dung", "Đã khóa");
		result.put("ap_dung", "Áp dụng");
		return result;
	}
	
	public Map<String, String> getTrangThaiSoanList() {
		HashMap<String, String> result = new HashMap<>();
		result.put(null, "      ");
		result.put("dang_soan", "Đang soạn");
		result.put("cho_duyet", "Chờ duyệt");
		result.put("da_duyet", "Đã duyệt");
		return result;
	}
	
	public Map<String, String> getTrangThaiXuatBanList() {
		HashMap<String, String> result = new HashMap<>();
		result.put(null, "      ");
		result.put("false", "Không");
		result.put("true", "Có");
		return result;
	}
	
	public Map<String, String> getTrangThaiTraLoiList() {
		HashMap<String, String> result = new HashMap<>();
		result.put(null, "      ");
		result.put("false", "Chưa trả lời");
		result.put("true", "Đã trả lời");
		return result;
	}
	
	public Map<String, String> getTrangThaiNoiBatList() {
		HashMap<String, String> result = new HashMap<>();
		result.put(null, "      ");
		result.put("false", "Không");
		result.put("true", "Có");
		return result;
	}
	
	public Map<String, String> getTrangThaiDuyetList() {
		HashMap<String, String> result = new HashMap<>();
		result.put(null, "      ");
		result.put("false", "Chưa duyệt");
		result.put("true", "Đã duyệt");
		return result;
	}
	public List<String> getListGioiTinh() {
		List<String> result = new ArrayList<String>();
		result.add("Nam");
		result.add("Nữ");
		return result;
	}
	public List<NhomGopY> getListNhomGopY() {
		List<NhomGopY> list = new ArrayList<>(Arrays.asList(NhomGopY.values()));
		return list;
	}
	
	public List<NhomGopY> getListNhomGopYNull() {
		List<NhomGopY> list = new ArrayList<NhomGopY>();
		list.add(null);
		list.addAll(getListNhomGopY());
		return list;
	}
	 
	@Transient
	public boolean isNguoiDungDaKhoa() {
		return !getNguoiDung().isCheckApDung();
	}
	

	@Transient
	public boolean isNguoiDungDaKichHoat() {
		return !getNguoiDung().isCheckKichHoat();
	}

	@Command
	public void redirectPage(@BindingParam("zul") String zul, @BindingParam("vmArgs") Object vmArgs,
			@BindingParam("vm") Object vm, @BindingParam("nhom") Object nhom,  @BindingParam("dvc") Object dvc) {
		Map<String, Object> args = new HashMap<>();
		args.put("vmArgs", vmArgs);
		args.put("vm", vm);
		args.put("nhom", nhom);
		args.put("dvc", dvc);
		Executions.createComponents(zul, null, args);
	}
	
	protected CellStyle setBorderAndFont(final Workbook workbook, final int borderSize, final boolean isTitle,
			final int fontSize, final String fontColor, final String textAlign, final boolean boil) {
		final CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderTop((short) borderSize); // single line border
		cellStyle.setBorderBottom((short) borderSize); // single line border
		cellStyle.setBorderLeft((short) borderSize); // single line border
		cellStyle.setBorderRight((short) borderSize); // single line border

		if (textAlign.equals("RIGHT")) {
			cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		} else if (textAlign.equals("CENTER")) {
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		} else if (textAlign.equals("LEFT")) {
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		} else {
			// do nothing
		}

		if (boil) {
			final Font font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			if (isTitle) {
				if (fontColor.equals("RED")) {
					font.setColor(Font.COLOR_RED);
				} else if (fontColor.equals("BLUE")) {
					font.setColor((short) 4);
				} else {
					// do no thing
				}
			}
			font.setFontHeightInPoints((short) fontSize);
			cellStyle.setFont(font);
		}
		return cellStyle;
	}

	public String unAccent(String s) {
		String temp = Normalizer.normalize(s.toLowerCase(), Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("").replaceAll("đ", "d").replaceAll(" ", "")
				.replaceAll("[^a-zA-Z0-9 -]", "");
	}

	public void showNotification(String content, String title, String type) {
		switch (type) {
			case "success":
				Clients.evalJavaScript("toastrSuccess('" + content + "', '" + title + "');");
				break;
			case "info":
				Clients.evalJavaScript("toastrInfo('" + content + "', '" + title + "');");
				break;
			case "warning":
				Clients.evalJavaScript("toastrWarning('" + content + "', '" + title + "');");
				break;
			case "error":
				Clients.evalJavaScript("toastrError('" + content + "', '" + title + "');");
				break;
			default:
				break;
		}
	}
	
	@Command
	public void invokeGG(@BindingParam("notify") Object vmArgs, @BindingParam("attr") String attrs,
			@BindingParam("detach") final Window wdn) {
		for (final String field : attrs.split("\\|")) {
			if (!field.isEmpty()) {
				BindUtils.postNotifyChange(null, null, vmArgs, field);
			}
		}
		if (wdn != null) {
			wdn.detach();
		}
	}
	public String getHomeUrl(String code) {
		String url = Executions.getCurrent().getHeader("host");
		if ("en".equals(code)) {
			return "http://" + url + "/web/en";
		}
		return "http://" + url;
	}
	
	@Transient
	public Date getBeginToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	@Transient
	public Date getBeginDateOf(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}
	
	@Transient
	public Date getEndToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}
	
	@Transient
	public Date getEndDateOf(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}
	
	@Transient
	public Date getToday() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	@Command
	public void notificate(String title, String content) {
		Map<String, Object> args = new HashMap<>();
		args.put("title", title);
		args.put("content", content);
		Executions.createComponents("/frontend/common/notification.zul", null, args);
	}
	@Command
	public void notificateError(String title, String content) {
		Map<String, Object> args = new HashMap<>();
		args.put("title", title);
		args.put("content", content);
		Executions.createComponents("/frontend/common/notification-error.zul", null, args);
	}

}