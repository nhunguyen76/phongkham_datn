package vn.toancauxanh.cms.service;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.DichVu;
import vn.toancauxanh.gg.model.QDichVu;
import vn.toancauxanh.service.BasicService;

public class DichVuService extends BasicService<DichVu> {

	public JPAQuery<DichVu> getTargetQuery() {
		String keyword = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		JPAQuery<DichVu> q = find(DichVu.class);

		if (keyword != null && !keyword.isEmpty()) {
			String tukhoa = "%" + keyword + "%";
			q.where(QDichVu.dichVu.tenDichVu.like(tukhoa));
		}

		q.orderBy(QDichVu.dichVu.ngayTao.desc());
		return q;
	}
} 
