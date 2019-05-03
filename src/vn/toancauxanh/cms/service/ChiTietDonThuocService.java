package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.ChiTietDonThuoc;
import vn.toancauxanh.gg.model.QChiTietBenhAn;
import vn.toancauxanh.gg.model.QChiTietDonThuoc;
import vn.toancauxanh.service.BasicService;

public class ChiTietDonThuocService extends BasicService<ChiTietDonThuocService> {

    private Long selectedIdThongTinDieu;
    
    List<ChiTietDonThuoc> getChiTietToaThuoc() {
        List<ChiTietDonThuoc> list = new ArrayList<>();
        JPAQuery<ChiTietDonThuoc> q = find(ChiTietDonThuoc.class).where(QChiTietDonThuoc.chiTietDonThuoc.thongTinDieuTri.id.eq(selectedIdThongTinDieu));
        q.orderBy(QChiTietBenhAn.chiTietBenhAn.ngayTao.asc());
        return list;
    }
}
