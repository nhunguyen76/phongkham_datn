package vn.toancauxanh.gg.model;

import java.io.IOException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "cauhoi")
public class CauHoi extends Model<CauHoi> {

	private String noiDung;

	public String getNoiDung() {
		return noiDung;
	}

	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}
	
	@Command
	public void saveCauHoi(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {
		
			save();
			wdn.detach();
			BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	
	@Transient
	public List<TraLoi> getListCauTraLoi() {
		JPAQuery<TraLoi> q = find(TraLoi.class);
		q.where(QTraLoi.traLoi.cauHoi.id.eq(this.getId()));
		q.orderBy(QTraLoi.traLoi.ngayTao.desc());
		return q.fetch();
	}
	
	private TraLoi selectedTraLoi = new TraLoi();

	@Transient
	public TraLoi getSelectedTraLoi() {
		return selectedTraLoi;
	}

	public void setSelectedTraLoi(TraLoi selectedTraLoi) {
		this.selectedTraLoi = selectedTraLoi;
	}

	@Command
	public void saveCauTraLoi(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {
			selectedTraLoi.setCauHoi(this);
			selectedTraLoi.save();
			selectedTraLoi = new TraLoi();
			BindUtils.postNotifyChange(null, null, this, "selectedTraLoi");
			BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	
	@Command
	public void onEditCauTraLoi(@BindingParam("item") TraLoi item) {
		this.selectedTraLoi = item;
		BindUtils.postNotifyChange(null, null, this, "selectedTraLoi");
	}
	
	@Command
	public void huyCauTraLoi() {
		this.selectedTraLoi = new TraLoi();
		BindUtils.postNotifyChange(null, null, this, "selectedTraLoi");
	}
}
