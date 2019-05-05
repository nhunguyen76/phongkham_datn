package vn.toancauxanh.cms.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.HoSoThongTin;
import vn.toancauxanh.gg.model.LichHenKham;
import vn.toancauxanh.gg.model.enums.BuoiKhamEnum;
import vn.toancauxanh.service.BasicService;

public class LichHenKhamService extends BasicService<LichHenKham> {

	public List<BuoiKhamEnum> getBuoiKhams(){
		List<BuoiKhamEnum> list = Arrays.asList(BuoiKhamEnum.values());
		return list;
	}
	
	public JPAQuery<LichHenKham> getTargetQuery() {
		String keyword = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		JPAQuery<HoSoThongTin> q = find(HoSoThongTin.class);

		if (keyword != null && !keyword.isEmpty()) {
			String tukhoa = "%" + keyword + "%";
			q.where(QLichHenKham.lichHenKham.benhNhan..like(tukhoa));
		}

		q.orderBy(QLichHenKham.lichHenKham.ngayTao.desc());
		return q;
	}
}
