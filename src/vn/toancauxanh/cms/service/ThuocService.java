package vn.toancauxanh.cms.service;

import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.QThuoc;
import vn.toancauxanh.gg.model.Thuoc;
import vn.toancauxanh.service.BasicService;

public class ThuocService extends BasicService<Thuoc> {

    public JPAQuery<Thuoc> getTargetQuery() {
        String keyword = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
        JPAQuery<Thuoc> q = find(Thuoc.class);

        if (keyword != null && !keyword.isEmpty()) {
            String tukhoa = "%" + keyword + "%";
            q.where(QThuoc.thuoc.tenThuoc.like(tukhoa));
        }

        q.orderBy(QThuoc.thuoc.ngayTao.desc());
        return q;
    }
    
    public List<String> getListTenThuoc() {
        JPAQuery<Thuoc> q = find(Thuoc.class);
        q.orderBy(QThuoc.thuoc.ngayTao.desc());
        List<String> result = q.select(QThuoc.thuoc.tenThuoc).fetch();
        return result;
    }
}
