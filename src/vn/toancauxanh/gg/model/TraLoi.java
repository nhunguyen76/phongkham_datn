package vn.toancauxanh.gg.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "traloi")
public class TraLoi extends Model<TraLoi>{

	private CauHoi cauHoi;
	private String noiDung;
	
	@ManyToOne
	public CauHoi getCauHoi() {
		return cauHoi;
	}
	public void setCauHoi(CauHoi cauHoi) {
		this.cauHoi = cauHoi;
	}
	public String getNoiDung() {
		return noiDung;
	}
	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}
	
	
}
