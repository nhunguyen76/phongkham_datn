package vn.toancauxanh.gg.model;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "dichvu")
public class DichVu extends Model<DichVu> {

	private String tenDichVu;
	private String maDichVu;
	
	public String getTenDichVu() {
		return tenDichVu;
	}

	public void setTenDichVu(String tenDichVu) {
		this.tenDichVu = tenDichVu;
	}

	public String getMaDichVu() {
		return maDichVu;
	}

	public void setMaDichVu(String maDichVu) {
		this.maDichVu = maDichVu;
	}

	@Command
	public void saveDichVu(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {
		
			save();
			wdn.detach();
			BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	
	@Transient
	public AbstractValidator getValidatorTenDichVu() {
		return new AbstractValidator() {
			@Override
			public void validate(ValidationContext ctx) {

				String value = (String) ctx.getProperty().getValue();
				if (value.isEmpty()) {
					addInvalidMessage(ctx, "Không được để trống trường này.");
				} else if (StringUtils.isBlank(value)) {
					addInvalidMessage(ctx, "Không được để khoảng trắng.");
				} else {
					JPAQuery<DichVu> q = find(DichVu.class)
							.where(QDichVu.dichVu.tenDichVu.eq(value));
					if (!DichVu.this.noId()) {
						q.where(QDichVu.dichVu.id.ne(getId()));
					}
					if (q.fetchCount() > 0) {
						addInvalidMessage(ctx, "Đã tồn tại tên dịch vụ này.");
					}
				}
			}
		};
	}
	
	@Transient
	public AbstractValidator getValidatorMaDichVu() {
		return new AbstractValidator() {
			@Override
			public void validate(ValidationContext ctx) {

				String value = (String) ctx.getProperty().getValue();
				if (value.isEmpty()) {
					addInvalidMessage(ctx, "Không được để trống trường này.");
				} else if (StringUtils.isBlank(value)) {
					addInvalidMessage(ctx, "Không được để khoảng trắng.");
				} else {
					JPAQuery<DichVu> q = find(DichVu.class)
							.where(QDichVu.dichVu.maDichVu.eq(value));
					if (!DichVu.this.noId()) {
						q.where(QDichVu.dichVu.id.ne(getId()));
					}
					if (q.fetchCount() > 0) {
						addInvalidMessage(ctx, "Đã tồn tại tên dịch vụ này.");
					}
				}
			}
		};
	}
}
