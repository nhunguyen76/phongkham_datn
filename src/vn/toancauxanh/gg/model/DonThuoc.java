package vn.toancauxanh.gg.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.enums.TrangThaiXuLyEnum;
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
		q.orderBy(QChiTietDonThuoc.chiTietDonThuoc.ngayTao.asc());
		chiTietDonThuocs.addAll(q.fetch());
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
		List<ChiTietDonThuoc> listInDB = find(ChiTietDonThuoc.class).where(QChiTietDonThuoc.chiTietDonThuoc.donThuoc.id.eq(this.getId())).fetch();
		if (!chiTietDonThuocs.isEmpty()) {
			save();
			// list thuoc DB bi xoa
			List<ChiTietDonThuoc> removed = listInDB.stream()
					.filter(db -> chiTietDonThuocs.stream().noneMatch(obj -> obj.getId().equals(db.getId())))
					.collect(Collectors.toList());
			removed.forEach(System.out::println);
			for (ChiTietDonThuoc obj : removed) {
				obj.doDelete(true);
			}
			// Save don thuoc
			for (ChiTietDonThuoc dto : chiTietDonThuocs) {
				dto.setDonThuoc(this);
				dto.saveNotShowNotification();
			}
		}
		wdn.detach();
		BindUtils.postNotifyChange(null, null, vmArgs, attr);
	}
	
	@Command
	public void onEditThuoc(@BindingParam("vm") Object vm,  @BindingParam("item") ChiTietDonThuoc item) {
		this.chiTietDonThuoc = item;
		BindUtils.postNotifyChange(null,null,vm,"chiTietDonThuoc");
	}
	
	@Command
	public void onDeleteThuoc(
			@BindingParam("vm") Object vm,
			@BindingParam("item") ChiTietDonThuoc item)throws IOException{
		Messagebox.show("Bạn muốn xóa thuốc này?", "Xác nhận", Messagebox.CANCEL | Messagebox.OK,
				Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							chiTietDonThuocs.remove(item);
							BindUtils.postNotifyChange(null,null,vm,"chiTietDonThuocs");
						}
					}
				});
	}
	
	@Command
	public void suaThuoc() {
		BindUtils.postNotifyChange(null,null,this,"chiTietDonThuocs");
		this.chiTietDonThuoc = new ChiTietDonThuoc();
		BindUtils.postNotifyChange(null,null,this,"chiTietDonThuoc");
	}
	
	@Command
	public void huySuaThuoc() {
		this.chiTietDonThuoc = new ChiTietDonThuoc();
		BindUtils.postNotifyChange(null,null,this,"chiTietDonThuoc");
	}
}
