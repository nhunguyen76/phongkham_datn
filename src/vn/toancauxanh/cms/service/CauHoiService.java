package vn.toancauxanh.cms.service;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CauHoi;
import vn.toancauxanh.gg.model.QCauHoi;
import vn.toancauxanh.gg.model.QDichVu;
import vn.toancauxanh.service.BasicService;

public class CauHoiService extends BasicService<CauHoi> {

	public JPAQuery<CauHoi> getTargetQuery() {
		String keyword = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		JPAQuery<CauHoi> q = find(CauHoi.class);
		if (keyword != null && !keyword.isEmpty()) {
			String tukhoa = "%" + keyword + "%";
			q.where(QCauHoi.cauHoi.noiDung.like(tukhoa));
		}
		q.orderBy(QCauHoi.cauHoi.ngayTao.desc());
		return q;
	}
}
