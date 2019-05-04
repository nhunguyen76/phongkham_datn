package vn.toancauxanh.gg.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import vn.toancauxanh.gg.model.enums.TrangThaiXuLyEnum;
import vn.toancauxanh.model.Model;

public class LichHenKham extends Model<LichHenKham> {

    private HoSoThongTin benhNhan;
    private Date thoiGianKham;
    private TrangThaiXuLyEnum trangThaiXuLy;
    private HoSoThongTin nguoiDuyet; // Nguoi duyet lich kham
    private String noiDung;

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

}
