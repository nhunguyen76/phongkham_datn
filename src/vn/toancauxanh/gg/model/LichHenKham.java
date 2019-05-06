package vn.toancauxanh.gg.model;

import java.io.IOException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import vn.toancauxanh.gg.model.enums.BuoiKhamEnum;
import vn.toancauxanh.gg.model.enums.TrangThaiXuLyEnum;
import vn.toancauxanh.model.Model;

@Entity
@Table(name = "lichhenkham")
public class LichHenKham extends Model<LichHenKham> {

    private HoSoThongTin benhNhan;
    private Date thoiGianDatHen; //thoi gian nguoi dat lich hen
    private Date thoiGianKham;
    private BuoiKhamEnum buoiKham;
    private int thoiGianKhamUocTinh;
    private TrangThaiXuLyEnum trangThaiXuLy;
    private HoSoThongTin nguoiDuyet; // Nguoi duyet lich kham
    private String noiDung;

    @ManyToOne
    public HoSoThongTin getBenhNhan() {
        return benhNhan;
    }

    public void setBenhNhan(HoSoThongTin benhNhan) {
        this.benhNhan = benhNhan;
    }

    public Date getThoiGianKham() {
        return thoiGianKham;
    }

    public void setThoiGianKham(Date thoiGianKham) {
        this.thoiGianKham = thoiGianKham;
    }

    @Enumerated(EnumType.STRING)
    public TrangThaiXuLyEnum getTrangThaiXuLy() {
        return trangThaiXuLy;
    }

    public void setTrangThaiXuLy(TrangThaiXuLyEnum trangThaiXuLy) {
        this.trangThaiXuLy = trangThaiXuLy;
    }

    @ManyToOne
    public HoSoThongTin getNguoiDuyet() {
        return nguoiDuyet;
    }

    public void setNguoiDuyet(HoSoThongTin nguoiDuyet) {
        this.nguoiDuyet = nguoiDuyet;
    }

    @Column(columnDefinition = "TEXT")
    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

	public int getThoiGianKhamUocTinh() {
		return thoiGianKhamUocTinh;
	}

	public void setThoiGianKhamUocTinh(int thoiGianKhamUocTinh) {
		this.thoiGianKhamUocTinh = thoiGianKhamUocTinh;
	}

	@Enumerated(EnumType.STRING)
	public BuoiKhamEnum getBuoiKham() {
		return buoiKham;
	}

	public void setBuoiKham(BuoiKhamEnum buoiKham) {
		this.buoiKham = buoiKham;
	}

	@Command
	public void saveLichHenKham(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {
			this.setTrangThaiXuLy(TrangThaiXuLyEnum.CHO_DUYET);
			save();
			wdn.detach();
	}
	
	@Command
	public void saveLichHenKhamConfirm(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {
			save();
			wdn.detach();
	}

	public Date getThoiGianDatHen() {
		return thoiGianDatHen;
	}

	public void setThoiGianDatHen(Date thoiGianDatHen) {
		this.thoiGianDatHen = thoiGianDatHen;
	}
}
