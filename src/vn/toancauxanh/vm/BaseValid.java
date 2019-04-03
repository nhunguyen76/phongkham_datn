package vn.toancauxanh.vm;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.sys.ValidationMessages;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.SimpleConstraint;
import org.zkoss.zul.mesg.MZul;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.model.Model;
import vn.toancauxanh.model.QModel;
import vn.toancauxanh.service.BasicService;

public class BaseValid extends AbstractValidator {
	private static transient final Logger log = LogManager.getLogger(BaseValid.class.getName());

	@Override
	public void validate(final ValidationContext ctx) {
		final ValidationMessages vmsgs = (ValidationMessages) ctx.getValidatorArg("vmsg");
		if (vmsgs != null) {
			vmsgs.clearKeyMessages(Throwable.class.getSimpleName());
			vmsgs.clearMessages(ctx.getBindContext().getComponent());
		}
		validateConstraint(ctx);
		validateNamSinh(ctx);
		validateNgaySinh(ctx);
		validateUnique(ctx);
		validatePasswords(ctx);
		validateEmail(ctx);
		validateNgayKetThuc(ctx);
		validateNgayBatDau(ctx);
		validateSoDienThoai(ctx);
		validateNgayHetHieuLuc(ctx);
		validateNgayHieuLuc(ctx);
		validateThoiGianKetThuc(ctx);
		validateNgayHieuLucChungChi(ctx);
	}
	private boolean validateSoDienThoai(final ValidationContext ctx) {
		String sdt = (String) ctx.getValidatorArg("sdt");
		boolean result;
		if (sdt == null || sdt.trim().matches("/^\\+?\\d{1,3}?[- .]?\\(?(?:\\d{2,3})\\)?[- .]?\\d\\d\\d[- .]?\\d\\d\\d\\d$/")) {
			result = true;
		} else {
			addInvalidMessage(ctx, "Số điện thoại không đúng định dạng.");
			result = false;
		}
		return result;
	}
	
	private boolean validateNgayKetThuc(final ValidationContext ctx) {
		boolean result = true;
		final Boolean flag = (Boolean) ctx.getValidatorArg("flagNgayKetThuc");
		Date ngayBatDau = (Date) ctx.getValidatorArg("ngayBatDau");
		Date ngayKetThuc = (Date) ctx.getValidatorArg("ngayKetThuc");
		if (flag == null || flag==false) {
			return true;
		}
		if (ngayBatDau != null && ngayKetThuc != null) {
			if (ngayKetThuc.before(ngayBatDau)) {
				addInvalidMessage(ctx, "Ngày kết thúc không được bé hơn ngày bắt đầu.");
				result = false;
			}
		}
		return result;
	}
	
	private boolean validateNgayHetHieuLuc(final ValidationContext ctx) {
		boolean result = true;
		final Boolean flag = (Boolean) ctx.getValidatorArg("flagNgayHetHieuLuc");
		Date ngayHieuLuc = (Date) ctx.getValidatorArg("ngayHieuLuc");
		Date ngayHetHieuLuc = (Date) ctx.getValidatorArg("ngayHetHieuLuc");
		if (flag == null || flag==false) {
			return true;
		}
		if (ngayHieuLuc != null && ngayHetHieuLuc != null) {
			if (ngayHetHieuLuc.before(ngayHieuLuc)) {
				addInvalidMessage(ctx, "Ngày hết hiệu lực không được bé hơn ngày hiệu lực.");
				result = false;
			}
		}
		return result;
	}
	
	private boolean validateNgayHieuLuc(final ValidationContext ctx) {
		boolean result = true;
		final Boolean flag = (Boolean) ctx.getValidatorArg("flagNgayHieuLuc");
		Date ngayBanHanh = (Date) ctx.getValidatorArg("ngayBanHanh");
		Date ngayHieuLuc = (Date) ctx.getValidatorArg("ngayHieuLuc");
		if (flag == null || flag==false) {
			return true;
		}
		if (ngayBanHanh != null && ngayHieuLuc != null) {
			if (ngayHieuLuc.before(ngayBanHanh)) {
				addInvalidMessage(ctx, "Ngày ngày hiệu lực không được bé hơn ngày ban hành.");
				result = false;
			}
		}
		return result;
	}
	
	private boolean validateNgayHieuLucChungChi(final ValidationContext ctx) {
		boolean result = true;
		final Boolean flag = (Boolean) ctx.getValidatorArg("flagNgayHieuLuc");
		Date ngayCapChungChi = (Date) ctx.getValidatorArg("ngayCapChungChi");
		Date ngayHieuLuc = (Date) ctx.getValidatorArg("ngayHieuLuc");
		if (flag == null || flag==false) {
			return true;
		}
		if (ngayCapChungChi != null && ngayHieuLuc != null) {
			if (ngayHieuLuc.before(ngayCapChungChi)) {
				addInvalidMessage(ctx, "Ngày ngày hiệu lực không được bé hơn ngày cấp.");
				result = false;
			}
		}
		return result;
	}
	
	private boolean validateNgayBatDau(final ValidationContext ctx) {
		boolean result = true;
		final Boolean flag = (Boolean) ctx.getValidatorArg("flagNgayBatDau");
		Date ngayCapPhep = (Date) ctx.getValidatorArg("ngayCapPhep");
		Date ngayBatDau = (Date) ctx.getValidatorArg("ngayBatDau");
		if (flag == null || flag==false) {
			return true;
		}
		if (ngayCapPhep != null && ngayBatDau != null) {
			if (ngayBatDau.before(ngayCapPhep)) {
				addInvalidMessage(ctx, "Ngày bắt đầu không được bé hơn ngày cấp phép.");
				result = false;
			}
		}
		return result;
	}
	
	private boolean validateThoiGianKetThuc(final ValidationContext ctx) {
		final Boolean flag = (Boolean) ctx.getValidatorArg("flagValidateThoiGian") == null ? false : true;
		if (flag) {
			Date thoiGianBatDau = (Date) ctx.getValidatorArg("thoiGianBatDau");
			Date thoiGianKetThuc = (Date) ctx.getValidatorArg("thoiGianKetThuc");
			if(thoiGianBatDau != null && thoiGianKetThuc != null) {
				if(thoiGianKetThuc.before(thoiGianBatDau)) {
					addInvalidMessage(ctx, "Đến ngày phải lớn hơn hoặc bằng từ ngày.");
					return false;
				}
				return true;
			}
		}
		
		return true;
	}
	
	private boolean validateConstraint(final ValidationContext ctx) {
		boolean result;
		final Object constraint = ctx.getValidatorArg("constraint");
		if (constraint == null) {
			result = true;
		} else {
			try {
				new SimpleConstraint(constraint.toString()).validate(null, ctx.getProperty().getValue());
				result = true;
			} catch (final WrongValueException ex) {
				Object msg;
				Object msgid = ctx.getValidatorArg("msgid");
				if (ex.getCode() == MZul.EMPTY_NOT_ALLOWED) {
					if (msgid == null) {
						msg = "Không được để trống trường này.";
					} else {
						msg = "Vui lòng nhập đầy đủ thông tin!";
					}

				} else if (ex.getCode() == MZul.NO_TODAY) {
					msg = "no today";
				} else if (ex.getCode() == MZul.NO_NEGATIVE) {
					//System.out.println("NO_NEGATIVE");
					msg = "Vui lòng nhập số dương";
				} else if (ex.getCode() == MZul.NO_POSITIVE) {
					//System.out.println("NO_POSITIVE");
					msg = "no positive";
				} else if (ex.getCode() == MZul.NO_FUTURE) {
					//System.out.println("NO_FUTURE");
					msg = "Không được lớn hơn ngày hiện tại";
				} else if (ex.getCode() == MZul.NO_ZERO) {
					msg = "Vui lòng nhập lớn hơn 0";
				} else if (ex.getCode() == MZul.NO_PAST) {
					msg = "Không được nhỏ hơn ngày hiện tại";
				} else if (ex.getCode() == MZul.NO_POSITIVE_ZERO) {
					msg = "NO_POSITIVE_ZERO";
				} else if (ex.getCode() == MZul.NO_POSITIVE_NEGATIVE_ZERO) {
					msg = "NO_POSITIVE_NEGATIVE_ZERO";
				} else if (ex.getCode() == MZul.NO_POSITIVE_NEGATIVE) {
					msg = "NO_POSITIVE_NEGATIVE";
				} else if (ex.getCode() == MZul.NO_FUTURE_TODAY) {
					msg = "NO_FUTURE_TODAY";
				} else if (ex.getCode() == MZul.NO_PAST_TODAY) {
					msg = "NO_PAST_TODAY";
				} else if (ex.getCode() == MZul.NO_FUTURE_PAST_TODAY) {
					msg = "NO_FUTURE_PAST_TODAY";
				} else if (ex.getCode() == MZul.NO_FUTURE_PAST) {
					msg = "NO_FUTURE_PAST";
				} else if (ex.getCode() == MZul.VALUE_NOT_MATCHED) {
					msg = "VALUE_NOT_MATCHED";
				} else if (ex.getCode() == 655433733) {
					msg = "Chỉ được nhập số dương";
				} else if (ctx.getValidatorArg("cmsg") == null) {
					msg = ex.getMessage();
				} else {
					msg = ctx.getValidatorArg("cmsg");
				}
				//System.out.println("ex.getCode(): " + ex.getCode());
				java.util.logging.Logger.getAnonymousLogger().info(ctx.getBindContext().getComponent() + "");
				java.util.logging.Logger.getAnonymousLogger().info(ctx.getBindContext().getComponent().getId() + "");
				//System.out.println("msdid: " + msgid);
				if (msgid == null) {
					addInvalidMessage(ctx, msg.toString());
				} else {
					addInvalidMessage(ctx, msgid.toString(), msg.toString());
				}
				result = false;
			}
		}
		return result;
	}

	private boolean validatePasswords(final ValidationContext ctx) {
		final Object retype = ctx.getValidatorArg("password");
		//System.out.println("retype: " + retype);
		boolean result;
		if (retype == null) {
			result = true;
		} else {
			Object pass = ctx.getProperty().getValue();
			if (pass == null) {
				pass = "";
			}
			if (retype.equals(pass)) {
				result = true;
			} else {
				addInvalidMessage(ctx, "Xác nhận mật khẩu không trùng khớp!");
				result = false;
			}
		}
		return result;
	}

	private boolean validateEmail(final ValidationContext ctx) {
		String email = (String) ctx.getValidatorArg("email");
		boolean result;
		if (email == null || email.trim().matches(".+@.+\\.[a-z]+")) {
			result = true;
		} else {
			addInvalidMessage(ctx, "Địa chỉ email không đúng định dạng.");
			result = false;
		}
		return result;
	}

	// private boolean validateCaptcha(final ValidationContext ctx) {
	// String captcha = (String) ctx.getValidatorArg("captcha");
	// System.out.println("captcha: " + captcha);
	// final String recaptcha = (String) ctx.getValidatorArg("recaptcha");
	// System.out.println("recaptcha: " + recaptcha);
	//
	// boolean result;
	// if (captcha != null && captcha.equals(recaptcha)) {
	// result = true;
	// } else {
	// addInvalidMessage(ctx, "captchaerr", "Mã kiểm tra không đúng.");
	// result = false;
	// }
	// System.out.println("ket qua: " + result);
	// return result;
	// }

	private boolean validateUnique(final ValidationContext ctx) {
		boolean result;
		final Object field = ctx.getValidatorArg("field");
		if (field == null) {
			result = true;
		} else if (ctx.getProperty().getValue() != null) {
			final Model<?> object = (Model<?>) ctx.getValidatorArg("id");
			BasicService<Object> basicService = new BasicService<>();
			JPAQuery<?> q = basicService.find(object.getClass());
			Path<?> path = (Path<?>) q.getMetadata().getJoins().get(0).getTarget();
			PathBuilder<?> pb = new PathBuilder<>(object.getClass(), path.getMetadata().getName());
			// Model<?> a = alias(object.getClass(),
			// path.getMetadata().getName());
			// q =
			// q.where($(a.getTrangThai()).ne(basicService.core().TT_DA_XOA))
			// .where($(a.getId()).ne(object.getId()))
			q = q.where(pb.get(QModel.model.trangThai).ne(basicService.core().TT_DA_XOA))
					.where(pb.get(QModel.model.id).ne(object.getId()))
					.where(Expressions.path(Object.class, path, field.toString()).eq(ctx.getProperty().getValue()));
			if (q.fetchCount() > 0) {
				String replaceMsgs = (String) ctx.getValidatorArg("cmsg");
				if (replaceMsgs != null || !"".equals(replaceMsgs)) {
					addInvalidMessage(ctx, replaceMsgs);
				} else {
					addInvalidMessage(ctx, "Giá trị này đã tồn tại trong hệ thống.");
				}

				result = false;
			} else {
				result = true;
			}
		} else {
			result = true;
		}

		return result;
	}
	
	private boolean validateNamSinh(final ValidationContext ctx) {
		boolean result = true;
		String messg = "Năm sinh không được lớn hơn năm hiện tại";
		final Boolean flag = (Boolean) ctx.getValidatorArg("flagNamSinh");
		String namSinhStr = (String) ctx.getValidatorArg("namSinh");
		Date now = new Date();
		//System.out.println("namSinhStr: " + namSinhStr);
		if (flag == null || flag==false) {
			return true;
		}
		if (namSinhStr != null && !namSinhStr.isEmpty()) {
			int namSinh = Integer.parseInt(namSinhStr);
			if (namSinh > DateUtils.toCalendar(now).get(Calendar.YEAR)) {
				addInvalidMessage(ctx, messg);
				result = false;
			} else if (DateUtils.toCalendar(now).get(Calendar.YEAR) - namSinh > 150) {
				addInvalidMessage(ctx, "Tuổi đã vượt quá 150");
				result = false;
			}
		}
		return result;
	}
	
	private boolean validateNgaySinh(final ValidationContext ctx) {
		boolean result = true;
		String messg = "Không được để trống trường này.";
		String messg1 = "Ngày sinh không được lớn hơn ngày hiện tại.";
		String messg2 = "Tuổi đã vượt quá 150.";
		Date now = new Date();
		Date ngaySinh = (Date) ctx.getValidatorArg("ngaySinhConstraint");
		int namSinh = 0;
		//System.out.println("namSinh valid: " + namSinh);
		final Boolean flag = (Boolean) ctx.getValidatorArg("flagBirth");
		if (flag == null || flag==false) {
			return true;
		} else {
			try {
				namSinh = (int) ctx.getValidatorArg("namSinhConstraint");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (ngaySinh == null && namSinh == 0) {
		//	System.out.println("x1");
			addInvalidMessage(ctx, messg);
			result = false;
		} else {
			//System.out.println("x2");
			if (ngaySinh != null) {
				//System.out.println("x3");
				if (ngaySinh.after(now)) {
					addInvalidMessage(ctx, messg1);
					result = false;
				} else if (DateUtils.toCalendar(now).get(Calendar.YEAR) - DateUtils.toCalendar(ngaySinh).get(Calendar.YEAR) > 150){
					addInvalidMessage(ctx, messg2);
					result = false;
				}
			} else if (namSinh > 0) {
				//System.out.println("x4");
				int nam = 0;
				try {
					nam = namSinh;
					if (DateUtils.toCalendar(now).get(Calendar.YEAR) < nam) {
						addInvalidMessage(ctx, "Năm sinh không được lớn hơn năm hiện tại");
						result = false;
					} else if (DateUtils.toCalendar(now).get(Calendar.YEAR) - nam > 150) {
						addInvalidMessage(ctx, messg2);
						result = false;
					}
				} catch (Exception e) {
					//System.out.println(e.getMessage());
					addInvalidMessage(ctx, "errNgaySinh", "Năm sinh không đúng");
					result = false;
				}
			}
		}
		return result;
	}

}
