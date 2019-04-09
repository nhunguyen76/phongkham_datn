package vn.toancauxanh.service;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.HoSoThongTin;
import vn.toancauxanh.gg.model.QHoSoThongTin;

public class HoSoThongTinService extends BasicService<HoSoThongTin> {

	public JPAQuery<HoSoThongTin> getTargetQuery() {
		String keyword = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		JPAQuery<HoSoThongTin> q = find(HoSoThongTin.class);

		if (keyword != null && !keyword.isEmpty()) {
			String tukhoa = "%" + keyword + "%";
			q.where(QHoSoThongTin.hoSoThongTin.hoVaTen.like(tukhoa));
		}

		q.orderBy(QHoSoThongTin.hoSoThongTin.ngayTao.desc());
		return q;
	}
}
