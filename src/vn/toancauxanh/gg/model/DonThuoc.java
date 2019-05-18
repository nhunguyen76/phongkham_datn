package vn.toancauxanh.gg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "donthuoc")
public class DonThuoc extends Model<DonThuoc> {

	private HoSoThongTin benhNhan;
	private Date ngayXuat;
	
	@ManyToOne
	public HoSoThongTin getBenhNhan() {
		return benhNhan;
	}
	public void setBenhNhan(HoSoThongTin benhNhan) {
		this.benhNhan = benhNhan;
	}
	public Date getNgayXuat() {
		return ngayXuat;
	}
	public void setNgayXuat(Date ngayXuat) {
		this.ngayXuat = ngayXuat;
	}
	
	////////////////////////////////////////
	public List<ChiTietDonThuoc> chiTietDonThuocs = new ArrayList<>() ;
	public ChiTietDonThuoc chiTietDonThuoc = new ChiTietDonThuoc();
	
	@Transient
	public List<ChiTietDonThuoc> getChiTietDonThuocs() {
		return chiTietDonThuocs;
	}
	
	public void setChiTietDonThuocs(List<ChiTietDonThuoc> chiTietDonThuocs) {
		this.chiTietDonThuocs = chiTietDonThuocs;
	}
	
	@Transient
	public ChiTietDonThuoc getChiTietDonThuoc() {
		return chiTietDonThuoc;
	}
	public void setChiTietDonThuoc(ChiTietDonThuoc chiTietDonThuoc) {
		this.chiTietDonThuoc = chiTietDonThuoc;
	}
	
	@Transient
	public void getChiTietDonThuocInDB() {
		JPAQuery<ChiTietDonThuoc> q = find(ChiTietDonThuoc.class).where(QChiTietDonThuoc.chiTietDonThuoc.donThuoc.id.eq(this.getId()));
		q.orderBy(QChiTietDonThuoc.chiTietDonThuoc.ngayTao.desc());
		chiTietDonThuocs.addAll(q.fetch());
		System.out.println("=========" + chiTietDonThuocs.size());
	}
	
	@Command
	public void themThuoc() {
		chiTietDonThuocs.add(chiTietDonThuoc);
		chiTietDonThuoc = new ChiTietDonThuoc();
		BindUtils.postNotifyChange(null, null, this, "chiTietDonThuoc");
		BindUtils.postNotifyChange(null, null, this, "chiTietDonThuocs");
	}
	
	@Command
	public void saveDonThuoc(@BindingParam("wdn") final Window wdn,
			@BindingParam("vmArgs") Object vmArgs,
			@BindingParam("attr") final String attr) {
		if (!chiTietDonThuocs.isEmpty()) {
			save();
			for (ChiTietDonThuoc dto : chiTietDonThuocs) {
				dto.setDonThuoc(this);
				dto.saveNotShowNotification();
			}
		}
		wdn.detach();
		BindUtils.postNotifyChange(null, null, vmArgs, attr);
	}
}
