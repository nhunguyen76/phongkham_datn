package vn.toancauxanh.gg.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import vn.toancauxanh.model.Model;
import vn.toancauxanh.model.NguoiDung;

@Entity
@Table(name = "danhgia")
public class DanhGia extends Model<DanhGia>{
	
	private float rate;
	private Sach sach;
	private NguoiDung user;
	
	public float getRate() {
		return rate;
	}
	
	public void setRate(float rate) {
		this.rate = rate;
	}
	
	@ManyToOne
	public Sach getSach() {
		return sach;
	}
	
	public void setSach(Sach book) {
		this.sach = book;
	}
	
	@ManyToOne
	public NguoiDung getUser() {
		return user;
	}
	
	public void setUser(NguoiDung user) {
		this.user = user;
	}
	
	@Command
	public void saveDanhGia(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		setUser(core().fetchNhanVien(true));
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	
}
