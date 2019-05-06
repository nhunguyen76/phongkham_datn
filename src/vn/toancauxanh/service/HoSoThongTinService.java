package vn.toancauxanh.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.HoSoThongTin;
import vn.toancauxanh.gg.model.QHoSoThongTin;
import vn.toancauxanh.gg.model.enums.GioiTinhEnum;
import vn.toancauxanh.model.QVaiTro;
import vn.toancauxanh.model.VaiTro;

public class HoSoThongTinService extends BasicService<HoSoThongTin> {

	
	public JPAQuery<HoSoThongTin> getTargetQuery() {
		String keyword = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		JPAQuery<HoSoThongTin> q = find(HoSoThongTin.class);
		
		if (paramHoVaTenBenhNhan != null && !paramHoVaTenBenhNhan.isEmpty()) {
			String tukhoa = "%" + paramHoVaTenBenhNhan + "%";
			q.where(QHoSoThongTin.hoSoThongTin.hoVaTen.like(tukhoa));
		}
		
		if (paramCMND != null && !paramCMND.isEmpty()) {
			String tukhoa = "%" + paramCMND + "%";
			q.where(QHoSoThongTin.hoSoThongTin.cmnd.like(tukhoa));
		}
		
		if (paramNgaySinh != null) {
			q.where(QHoSoThongTin.hoSoThongTin.ngaySinh.eq(paramNgaySinh));
		}

		q.orderBy(QHoSoThongTin.hoSoThongTin.ngayTao.desc());
		return q;
	}
	
	public List<GioiTinhEnum> getGioiTinhs(){
		List<GioiTinhEnum> list = Arrays.asList(GioiTinhEnum.values());
		return list;
	}
	
	//Lấy danh sách bác sĩ
	public List<HoSoThongTin> getListBacSi() {
		VaiTro vaiTro = find(VaiTro.class).where(QVaiTro.vaiTro.alias.eq("bacsi")).fetchFirst();
		JPAQuery<HoSoThongTin> q = find(HoSoThongTin.class).where(QHoSoThongTin.hoSoThongTin.taiKhoan.vaiTros.any().eq(vaiTro));
		return q.fetch();
	}
	
	// lấy toàn bộ danh sách nhân viên
	public JPAQuery<HoSoThongTin> getListThongTinNhanVien() {
		String keyword = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		VaiTro vaiTroBenhNhan = find(VaiTro.class).where(QVaiTro.vaiTro.alias.eq("benhnhan")).fetchFirst();
		JPAQuery<HoSoThongTin> q = find(HoSoThongTin.class).where(QHoSoThongTin.hoSoThongTin.taiKhoan.vaiTros.any().ne(vaiTroBenhNhan));

		if (keyword != null && !keyword.isEmpty()) {
			String tukhoa = "%" + keyword + "%";
			q.where(QHoSoThongTin.hoSoThongTin.hoVaTen.like(tukhoa));
		}

		q.orderBy(QHoSoThongTin.hoSoThongTin.ngayTao.desc());
		return q;
	}
	
	// param tìm kiếm 
	private String paramHoVaTenBenhNhan;
	private Date paramNgaySinh;
	private String paramCMND;
	
	public String getParamHoVaTenBenhNhan() {
		return paramHoVaTenBenhNhan;
	}

	public void setParamHoVaTenBenhNhan(String paramHoVaTenBenhNhan) {
		this.paramHoVaTenBenhNhan = paramHoVaTenBenhNhan;
	}

	public Date getParamNgaySinh() {
		return paramNgaySinh;
	}

	public void setParamNgaySinh(Date paramNgaySinh) {
		this.paramNgaySinh = paramNgaySinh;
	}

	public String getParamCMND() {
		return paramCMND;
	}

	public void setParamCMND(String paramCMND) {
		this.paramCMND = paramCMND;
	}
	
	
} 
