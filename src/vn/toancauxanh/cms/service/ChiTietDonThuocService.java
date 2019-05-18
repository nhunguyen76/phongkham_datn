package vn.toancauxanh.cms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.ChiTietBenhAn;
import vn.toancauxanh.gg.model.ChiTietDonThuoc;
import vn.toancauxanh.gg.model.QChiTietBenhAn;
import vn.toancauxanh.gg.model.QChiTietDonThuoc;
import vn.toancauxanh.service.BasicService;

public class ChiTietDonThuocService extends BasicService<ChiTietDonThuocService> {

    private Long selectedIdThongTinDieuTri;
    
    public Long getSelectedIdThongTinDieuTri() {
		return selectedIdThongTinDieuTri;
	}

	public void setSelectedIdThongTinDieuTri(Long selectedIdThongTinDieuTri) {
		this.selectedIdThongTinDieuTri = selectedIdThongTinDieuTri;
	}
	
	private List<ChiTietDonThuoc> donThuoc = new ArrayList<>();
	
	public List<ChiTietDonThuoc> getDonThuoc() {
        return donThuoc;
    }

    public void setDonThuoc(List<ChiTietDonThuoc> donThuoc) {
        this.donThuoc = donThuoc;
    }

//	public List<ChiTietDonThuoc> getChiTietToaThuoc() {
//        JPAQuery<ChiTietDonThuoc> q = find(ChiTietDonThuoc.class).where(QChiTietDonThuoc.chiTietDonThuoc.thongTinDieuTri.id.eq(selectedIdThongTinDieuTri));
//        q.orderBy(QChiTietDonThuoc.chiTietDonThuoc.ngayTao.asc());
//        donThuoc.addAll(q.fetch());
//        if(donThuoc.isEmpty()) {
//            donThuoc.add(new ChiTietDonThuoc());
//        }
//        return donThuoc;
//    }
	
	//Thêm row thuốc vào đơn thuốc
	@Command
	public void addRowThuoc() {
	    donThuoc.add(new ChiTietDonThuoc());
	    BindUtils.postNotifyChange(null, null, this, "donThuoc");
	}
	
//	@Command
//    public void saveDonThuoc(@BindingParam("wdn") final Window wdn) {
//	    JPAQuery<ChiTietBenhAn> q = find(ChiTietBenhAn.class).where(QChiTietBenhAn.chiTietBenhAn.id.eq(selectedIdThongTinDieuTri));
//	    ChiTietBenhAn chiTietBenhAn = q.fetchFirst();
//	    
//	    //Đơn thuốc trong DB
//	    JPAQuery<ChiTietDonThuoc> query = find(ChiTietDonThuoc.class).where(QChiTietDonThuoc.chiTietDonThuoc.thongTinDieuTri.id.eq(selectedIdThongTinDieuTri));
//        List<ChiTietDonThuoc> donThuocInDB = new ArrayList<>();
//        donThuocInDB.addAll(query.fetch());
//        
//	    //List thuốc bị xóa
//	    List<ChiTietDonThuoc> removed = donThuocInDB.stream()
//	            .filter(thuocDB -> this.donThuoc.stream().noneMatch(thuoc -> thuoc.getId().equals(thuocDB.getId())))
//	            .collect(Collectors.toList());
//	    for(ChiTietDonThuoc obj : removed) {
//	        obj.doDelete(true);
//	    }
//	    
//	    //Cập nhật và thêm toa thuốc
//	    for (ChiTietDonThuoc thuoc : this.donThuoc) {
//            thuoc.setThongTinDieuTri(chiTietBenhAn);
//            thuoc.saveNotShowNotification();
//        }
//	    showNotification("Đã lưu thành công!", "", "success");
//	    wdn.detach();
//    }

	@Command
    public void onDeleteThuoc(@ContextParam(ContextType.TRIGGER_EVENT) Event e,
            @BindingParam("vm") Object vm,
            @BindingParam("item") ChiTietDonThuoc item)throws IOException{
        e.stopPropagation();
        Messagebox.show("Bạn muốn xóa loại thuốc này?", "Hủy",
                new Messagebox.Button[] { Messagebox.Button.YES, Messagebox.Button.NO }, Messagebox.QUESTION,
                new EventListener<Messagebox.ClickEvent>() {
                    public void onEvent(ClickEvent event) throws Exception {
                        if (Messagebox.Button.YES.equals(event.getButton())) {
                            donThuoc.remove(item);
                            BindUtils.postNotifyChange(null,null,vm,"donThuoc");
                        }
                    }
                });
    }
    
}
