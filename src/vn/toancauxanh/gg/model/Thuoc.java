package vn.toancauxanh.gg.model;

import java.io.IOException;

import javax.persistence.Column;
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
@Table(name = "thuoc")
public class Thuoc extends Model<Thuoc> {

    private String tenThuoc;
    private String maThuoc;
    private String sanXuat;
    private String tacDung;
    
    public String getTenThuoc() {
        return tenThuoc;
    }
    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }
    
    public String getMaThuoc() {
        return maThuoc;
    }
    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }
    
    public String getSanXuat() {
        return sanXuat;
    }
    public void setSanXuat(String sanXuat) {
        this.sanXuat = sanXuat;
    }
    
    @Column(columnDefinition="TEXT")
    public String getTacDung() {
        return tacDung;
    }
    public void setTacDung(String tacDung) {
        this.tacDung = tacDung;
    }
    
    @Command
    public void saveThuoc(@BindingParam("list") final Object listObject,
            @BindingParam("attr") final String attr,
            @BindingParam("wdn") final Window wdn) throws IOException {
        
            save();
            wdn.detach();
            BindUtils.postNotifyChange(null, null, listObject, attr);
    }
    
    @Transient
    public AbstractValidator getValidatorTenThuoc() {
        return new AbstractValidator() {
            @Override
            public void validate(ValidationContext ctx) {
                String value = (String) ctx.getProperty().getValue();
                if (value.isEmpty()) {
                    addInvalidMessage(ctx, "Không được để trống trường này.");
                } else if (StringUtils.isBlank(value)) {
                    addInvalidMessage(ctx, "Không được để khoảng trắng.");
                } else {
                    JPAQuery<Thuoc> q = find(Thuoc.class)
                            .where(QThuoc.thuoc.tenThuoc.eq(value));
                    if (!Thuoc.this.noId()) {
                        q.where(QThuoc.thuoc.id.ne(getId()));
                    }
                    if (q.fetchCount() > 0) {
                        addInvalidMessage(ctx, "Đã tồn tại tên loại thuốc này.");
                    }
                }
            }
        };
    }
    
    @Transient
    public AbstractValidator getValidatorMaThuoc() {
        return new AbstractValidator() {
            @Override
            public void validate(ValidationContext ctx) {
                String value = (String) ctx.getProperty().getValue();
                if (value.isEmpty()) {
                    addInvalidMessage(ctx, "Không được để trống trường này.");
                } else if (StringUtils.isBlank(value)) {
                    addInvalidMessage(ctx, "Không được để khoảng trắng.");
                } else {
                    JPAQuery<Thuoc> q = find(Thuoc.class)
                            .where(QThuoc.thuoc.maThuoc.eq(value));
                    if (!Thuoc.this.noId()) {
                        q.where(QThuoc.thuoc.id.ne(getId()));
                    }
                    if (q.fetchCount() > 0) {
                        addInvalidMessage(ctx, "Đã tồn tại mã thuốc này.");
                    }
                }
            }
        };
    }
}
