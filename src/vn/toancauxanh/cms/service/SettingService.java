package vn.toancauxanh.cms.service;

import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.media.Media;
import org.zkoss.zul.Messagebox;

import vn.toancauxanh.model.QSetting;
import vn.toancauxanh.model.Setting;
import vn.toancauxanh.service.BasicService;

public class SettingService extends BasicService<Setting> {
	Setting setting = super.getSetting();

	private String filepath = "";

	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	@Command
	public void saveSetting() {
		Setting setting1 = getSetting();
		setting1.save();
	}

	@Command
	public void uploadFile(@BindingParam("media") final Media media) {
		try {
			if (media.getByteData().length > 50 * 1024 * 1024) {
				Messagebox.show("Dung lượng file upload không được lớn hơn 50MB!");
				return;
			}
			if (media.getName().toLowerCase().endsWith(".sql")) {
				String filename = media.getName();
				setFilepath("D:/stnmt/backup/" + filename);
				BindUtils.postNotifyChange(null, null, this, "filepath");
			} else {
				Messagebox.show("Chọn tập tin theo đúng định dạng (*.sql)");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	@Override
	public Setting getSetting() {
		return setting;
	}

	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				if (getSetting().getWidthMedium() == 0) {
					addInvalidMessage(ctx, "errLabelMedium", "Chiều rộng phải lớn hơn 0.");
				}

				if (getSetting().getWidthMedium() == 0) {
					addInvalidMessage(ctx, "errLabelSmal", "Chiều rộng phải lớn hơn 0.");
				}
			}
		};
	}
	
	@Transient
	public AbstractValidator getValidateSoDienThoai() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				String value = (String)ctx.getProperty().getValue();
				if(value == null || "".equals(value)) {
					addInvalidMessage(ctx, "error","Không được để trống trường này");
				}
				if (!value.isEmpty() && !value.trim().matches("^\\+?\\d{1,3}?[- .]?\\(?(?:\\d{2,3})\\)?[- .]?\\d\\d\\d[- .]?\\d\\d\\d\\d$")) {
					addInvalidMessage(ctx, "error", "Số điện thoại không đúng định dạng.");
				}
			}
		};
	}
	
	@Transient
	public AbstractValidator getValidatorEmail() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				String value = (String)ctx.getProperty().getValue();
				if(value == null || "".equals(value)) {
					addInvalidMessage(ctx, "error","Không được để trống trường này");
				}
				else if(!value.trim().matches(".+@.+\\.[a-z]+"))
				{
					addInvalidMessage(ctx, "error","Email không đúng định dạng");
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
				}
				else if (StringUtils.isBlank(value)) {
					addInvalidMessage(ctx, "Không được để khoảng trắng.");
				} 
			}
		};
	}
	
	@Transient
	public String getDiaChiSoXayDungDaNang() {
		return find(Setting.class).select(QSetting.setting.diaChi).fetchFirst();
	}
}
