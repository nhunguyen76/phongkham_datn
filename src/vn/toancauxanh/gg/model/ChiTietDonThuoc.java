package vn.toancauxanh.gg.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "chitietdonthuoc")
public class ChiTietDonThuoc extends Model<ChiTietDonThuoc>{

    private DonThuoc donThuoc;
    private String tenThuoc;
    private int soLuong;
    private String donVi;
    private String lieuDung;
    private String ghiChu;
    
    @ManyToOne
    public DonThuoc getDonThuoc() {
		return donThuoc;
	}

	public void setDonThuoc(DonThuoc donThuoc) {
		this.donThuoc = donThuoc;
	}

	public String getTenThuoc() {
		return tenThuoc;
	}

	public void setTenThuoc(String tenThuoc) {
		this.tenThuoc = tenThuoc;
	}

	public int getSoLuong() {
		return soLuong;
	}
	
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}



	public String getDonVi() {
		return donVi;
	}



	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}



	public String getLieuDung() {
		return lieuDung;
	}



	public void setLieuDung(String lieuDung) {
		this.lieuDung = lieuDung;
	}



	public String getGhiChu() {
		return ghiChu;
	}



	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
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
    
}
