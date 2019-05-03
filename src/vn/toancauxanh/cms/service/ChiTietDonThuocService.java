package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.jpa.impl.JPAQuery;

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

	public List<ChiTietDonThuoc> getChiTietToaThuoc() {
        List<ChiTietDonThuoc> list = new ArrayList<>();
        JPAQuery<ChiTietDonThuoc> q = find(ChiTietDonThuoc.class).where(QChiTietDonThuoc.chiTietDonThuoc.thongTinDieuTri.id.eq(selectedIdThongTinDieuTri));
        q.orderBy(QChiTietDonThuoc.chiTietDonThuoc.ngayTao.asc());
        if(list.isEmpty()) {
        	list.add(new ChiTietDonThuoc());
        }
        return list;
    }
}
