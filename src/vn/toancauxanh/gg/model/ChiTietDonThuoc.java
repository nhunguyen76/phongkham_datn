package vn.toancauxanh.gg.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "chitietdonthuoc")
public class ChiTietDonThuoc extends Model<ChiTietDonThuoc>{

    private Thuoc thuoc;
    private int soLuong;
    private int donVi;
    private String cachDung;
    private ChiTietBenhAn thongTinDieuTri;
    
    @ManyToOne
    public Thuoc getThuoc() {
        return thuoc;
    }
    
    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }
    
    public int getSoLuong() {
        return soLuong;
    }
    
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    
    public int getDonVi() {
        return donVi;
    }
    
    public void setDonVi(int donVi) {
        this.donVi = donVi;
    }
    
    public String getCachDung() {
        return cachDung;
    }
    
    public void setCachDung(String cachDung) {
        this.cachDung = cachDung;
    }
    
    @ManyToOne
    public ChiTietBenhAn getThongTinDieuTri() {
        return thongTinDieuTri;
    }
    
    public void setThongTinDieuTri(ChiTietBenhAn thongTinDieuTri) {
        this.thongTinDieuTri = thongTinDieuTri;
    }
    
}
