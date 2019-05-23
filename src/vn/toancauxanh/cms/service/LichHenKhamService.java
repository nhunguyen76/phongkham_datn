package vn.toancauxanh.cms.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.HoSoThongTin;
import vn.toancauxanh.gg.model.LichHenKham;
import vn.toancauxanh.gg.model.QHoSoThongTin;
import vn.toancauxanh.gg.model.QLichHenKham;
import vn.toancauxanh.gg.model.enums.BuoiKhamEnum;
import vn.toancauxanh.gg.model.enums.TrangThaiXuLyEnum;
import vn.toancauxanh.service.BasicService;

public class LichHenKhamService extends BasicService<LichHenKham> {
	
	private String paramHoVaTenBenhNhan;
	private Date paramNgayKham;

	public String getParamHoVaTenBenhNhan() {
		return paramHoVaTenBenhNhan;
	}

	public void setParamHoVaTenBenhNhan(String paramHoVaTenBenhNhan) {
		this.paramHoVaTenBenhNhan = paramHoVaTenBenhNhan;
	}

	public Date getParamNgayKham() {
		return paramNgayKham;
	}

	public void setParamNgayKham(Date paramNgayKham) {
		this.paramNgayKham = paramNgayKham;
	}

	public List<BuoiKhamEnum> getBuoiKhams(){
		List<BuoiKhamEnum> list = Arrays.asList(BuoiKhamEnum.values());
		return list;
	}
	
	public List<TrangThaiXuLyEnum> getTrangThaiXuLys(){
		List<TrangThaiXuLyEnum> list = Arrays.asList(TrangThaiXuLyEnum.values());
		return list;
	}
	
	public JPAQuery<LichHenKham> getTargetQuery() {
		JPAQuery<LichHenKham> q = find(LichHenKham.class);

		if (paramHoVaTenBenhNhan != null && !paramHoVaTenBenhNhan.isEmpty()) {
			String tukhoa = "%" + paramHoVaTenBenhNhan + "%";
			q.where(QLichHenKham.lichHenKham.benhNhan.hoVaTen.like(tukhoa));
		}
		
		if (paramNgayKham != null) {
			q.where(QLichHenKham.lichHenKham.thoiGianKham.eq(paramNgayKham));
		}

		q.orderBy(QLichHenKham.lichHenKham.ngayTao.desc());
		return q;
	}
	
	public JPAQuery<LichHenKham> getLichKhamCaNhan(Long idBenhNhan) {
        JPAQuery<LichHenKham> q = find(LichHenKham.class);

        q.where(QLichHenKham.lichHenKham.benhNhan.id.eq(idBenhNhan));
        q.orderBy(QLichHenKham.lichHenKham.ngayTao.desc());
        return q;
    }
	
//	public Long getTongLichHenKhamTrongNgay() {
//	    Calendar calendar = Calendar.getInstance(); // ngay hien tai
//	    calendar.add(Calendar.DATE, -1); // tru 1 ngay
//        JPAQuery<LichHenKham> q = find(LichHenKham.class);
//	    try {
//	    q.where(QLichHenKham.lichHenKham.thoiGianKham.after(calendar.getTime()));
//	    calendar.add(Calendar.DATE, 2); // cong 2 ngay
//	    q.where(QLichHenKham.lichHenKham.thoiGianKham.before(calendar.getTime()));
//	    } catch (Exception e) {}
//	    return q.fetchCount();
//	}
	
	
	public JPAQuery<LichHenKham> getLichHenKhamTrongNgay() {
        Calendar calendar = Calendar.getInstance(); // ngay hien tai
        calendar.add(Calendar.DATE, -1); // tru 1 ngay
        JPAQuery<LichHenKham> q = find(LichHenKham.class);
        try {
            q.where(QLichHenKham.lichHenKham.thoiGianKham.after(calendar.getTime()));
            calendar.add(Calendar.DATE, 2); // cong 2 ngay
            q.where(QLichHenKham.lichHenKham.thoiGianKham.before(calendar.getTime()));
        } catch (Exception e) {}
        return q;
    }
}
