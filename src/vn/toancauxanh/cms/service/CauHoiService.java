package vn.toancauxanh.cms.service;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CauHoi;
import vn.toancauxanh.gg.model.QCauHoi;
import vn.toancauxanh.service.BasicService;

public class CauHoiService extends BasicService<CauHoi> {

	public JPAQuery<CauHoi> getTargetQuery() {
		JPAQuery<CauHoi> q = find(CauHoi.class);
		q.orderBy(QCauHoi.cauHoi.ngayTao.desc());
		return q;
	}
}
