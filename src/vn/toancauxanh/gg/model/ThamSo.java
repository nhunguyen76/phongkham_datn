package vn.toancauxanh.gg.model;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "thamso")
public class ThamSo extends Model<ThamSo> {

	private static final long serialVersionUID = -1973333094118013160L;

	private String ten = "";
	private String giaTri = "";
	private String moTa = "";

	
	
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getGiaTri() {
		return giaTri;
	}

	public void setGiaTri(String giaTri) {
		this.giaTri = giaTri;
	}
	
	@Column(columnDefinition="TEXT")
	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	@Command
	public void saveThamSo(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {	
		save();
		wdn.detach();
	}

}
