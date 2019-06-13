package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.LichHenKham;
import vn.toancauxanh.gg.model.QLichHenKham;
import vn.toancauxanh.gg.model.enums.BuoiKhamEnum;
import vn.toancauxanh.gg.model.enums.SapXepLichKhamEnum;
import vn.toancauxanh.gg.model.enums.TrangThaiXuLyEnum;
import vn.toancauxanh.service.BasicService;

public class LichHenKhamService extends BasicService<LichHenKham> {
	
	private String paramHoVaTenBenhNhan;
	private Date paramNgayKham;
	private Date paramNgayDatHen;
	private TrangThaiXuLyEnum paramTrangThaiXuLy;
	private SapXepLichKhamEnum paramSapXepLichKham;

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


	public TrangThaiXuLyEnum getParamTrangThaiXuLy() {
		return paramTrangThaiXuLy;
	}

	public void setParamTrangThaiXuLy(TrangThaiXuLyEnum paramTrangThaiXuLy) {
		this.paramTrangThaiXuLy = paramTrangThaiXuLy;
	}
	 
	public List<SapXepLichKhamEnum> getSapXepLichKhamEnum(){
		List<SapXepLichKhamEnum> list = Arrays.asList(SapXepLichKhamEnum.values());
		return list;
	}
	
	public List<BuoiKhamEnum> getBuoiKhams(){
		List<BuoiKhamEnum> list = Arrays.asList(BuoiKhamEnum.values());
		return list;
	}
	
	public List<TrangThaiXuLyEnum> getTrangThaiXuLys(){
		List<TrangThaiXuLyEnum> list = Arrays.asList(TrangThaiXuLyEnum.values());
		return list;
	}
	
	public List<TrangThaiXuLyEnum> getTrangThaiXuLysAndNull(){
		List<TrangThaiXuLyEnum> list = new ArrayList<>();
		list.add(null);
		list.addAll(Arrays.asList(TrangThaiXuLyEnum.values()));
		return list;
	}
	
	public JPAQuery<LichHenKham> getTargetQuery() {
		JPAQuery<LichHenKham> q = find(LichHenKham.class);

		if (paramHoVaTenBenhNhan != null && !paramHoVaTenBenhNhan.isEmpty()) {
			String tukhoa = "%" + paramHoVaTenBenhNhan + "%";
			q.where(QLichHenKham.lichHenKham.benhNhan.hoVaTen.like(tukhoa));
		}
		
		if (paramNgayKham != null) {
	        //
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(paramNgayKham);
			calendar.add(Calendar.DATE, -1); // tru 1 ngay
	        Date dayBefore = calendar.getTime();
	        //
	        calendar.setTime(paramNgayKham);
	        calendar.add(Calendar.DATE, 2); // cong 2 ngay
	        Date dayAfter = calendar.getTime();
	        try {
	            q.where(QLichHenKham.lichHenKham.thoiGianKham.after(dayBefore));
	            calendar.add(Calendar.DATE, 2); // cong 2 ngay
	            q.where(QLichHenKham.lichHenKham.thoiGianKham.before(dayAfter));
	        } catch (Exception e) {}
		}
		
		if (paramNgayDatHen != null) {
			q.where(QLichHenKham.lichHenKham.thoiGianDatHen.eq(paramNgayDatHen));
		}
		
		if (paramTrangThaiXuLy != null) {
			q.where(QLichHenKham.lichHenKham.trangThaiXuLy.eq(paramTrangThaiXuLy));
		}
		
		
		if (paramSapXepLichKham != null) {
			if( SapXepLichKhamEnum.NGAY_DAT_HEN_GAN_NHAT.equals(paramSapXepLichKham)) {
				q.orderBy(QLichHenKham.lichHenKham.thoiGianDatHen.desc());
			} else if( SapXepLichKhamEnum.NGAY_DAT_HEN_XA_NHAT.equals(paramSapXepLichKham)) {
				q.orderBy(QLichHenKham.lichHenKham.thoiGianDatHen.asc());
			} else if( SapXepLichKhamEnum.NGAY_KHAM_GAN_NHAT.equals(paramSapXepLichKham)) {
				q.orderBy(QLichHenKham.lichHenKham.thoiGianKham.desc());
			} else if( SapXepLichKhamEnum.NGAY_KHAM_XA_NHAT.equals(paramSapXepLichKham)) {
				q.orderBy(QLichHenKham.lichHenKham.thoiGianKham.asc());
			}
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
	
	public Long getTongLichHenKhamTrongNgay() {
	    Calendar calendar = Calendar.getInstance(); // ngay hien tai
	    calendar.add(Calendar.DATE, -1); // tru 1 ngay
        JPAQuery<LichHenKham> q = find(LichHenKham.class);
	    try {
	    q.where(QLichHenKham.lichHenKham.thoiGianKham.after(calendar.getTime()));
	    calendar.add(Calendar.DATE, 2); // cong 2 ngay
	    q.where(QLichHenKham.lichHenKham.thoiGianKham.before(calendar.getTime()));
	    } catch (Exception e) {}
	    return q.fetchCount();
	}
	
	
	public List<LichHenKham> getLichHenKhamTrongNgay() {
        Calendar calendar = Calendar.getInstance(); // ngay hien tai
        calendar.add(Calendar.DATE, -1); // tru 1 ngay
        JPAQuery<LichHenKham> q = find(LichHenKham.class);
        try {
            q.where(QLichHenKham.lichHenKham.thoiGianKham.after(calendar.getTime()));
            calendar.add(Calendar.DATE, 2); // cong 2 ngay
            q.where(QLichHenKham.lichHenKham.thoiGianKham.before(calendar.getTime()));
        } catch (Exception e) {}
        return q.fetch();
    }

	public SapXepLichKhamEnum getParamSapXepLichKham() {
		return paramSapXepLichKham;
	}

	public void setParamSapXepLichKham(SapXepLichKhamEnum paramSapXepLichKham) {
		this.paramSapXepLichKham = paramSapXepLichKham;
	}
	
	public Date getParamNgayDatHen() {
		return paramNgayDatHen;
	}

	public void setParamNgayDatHen(Date paramNgayDatHen) {
		this.paramNgayDatHen = paramNgayDatHen;
	}
}
