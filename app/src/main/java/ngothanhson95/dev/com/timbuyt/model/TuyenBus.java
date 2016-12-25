package ngothanhson95.dev.com.timbuyt.model;

import java.io.Serializable;

/**
 * Created by sonnt on 12/24/16.
 */

public class TuyenBus implements Serializable {
    private int id;
    private String soTuyen;
    private String tenTuyen;
    private String xiNghiep;
    private String tanSuat;
    private  String thoiGianHoatDong;
    private String giaVe;
    private String loTrinhChieuDi;
    private String loTrinhChieuVe;

    public TuyenBus(int id, String tenTuyen, String xiNghiep, String tanSuat, String thoiGianHoatDong, String giaVe, String loTrinhChieuDi, String loTrinhChieuVe) {
        this.id = id;
        this.tenTuyen = tenTuyen;
        this.xiNghiep = xiNghiep;
        this.tanSuat = tanSuat;
        this.thoiGianHoatDong = thoiGianHoatDong;
        this.giaVe = giaVe;
        this.loTrinhChieuDi = loTrinhChieuDi;
        this.loTrinhChieuVe = loTrinhChieuVe;
    }

    public TuyenBus() {
    }

    public String getSoTuyen() {
        return soTuyen;
    }

    public void setSoTuyen(String soTuyen) {
        this.soTuyen = soTuyen;
    }

    public TuyenBus(String soTuyen, String tenTuyen, String xiNghiep, String tanSuat, String thoiGianHoatDong, String giaVe, String loTrinhChieuDi, String loTrinhChieuVe) {
        this.soTuyen = soTuyen;
        this.tenTuyen = tenTuyen;
        this.xiNghiep = xiNghiep;
        this.tanSuat = tanSuat;
        this.thoiGianHoatDong = thoiGianHoatDong;
        this.giaVe = giaVe;
        this.loTrinhChieuDi = loTrinhChieuDi;
        this.loTrinhChieuVe = loTrinhChieuVe;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getTenTuyen() {
        return tenTuyen;
    }

    public void setTenTuyen(String tenTuyen) {
        this.tenTuyen = tenTuyen;
    }

    public String getXiNghiep() {
        return xiNghiep;
    }

    public void setXiNghiep(String xiNghiep) {
        this.xiNghiep = xiNghiep;
    }

    public String getTanSuat() {
        return tanSuat;
    }

    public void setTanSuat(String tanSuat) {
        this.tanSuat = tanSuat;
    }

    public String getThoiGianHoatDong() {
        return thoiGianHoatDong;
    }

    public void setThoiGianHoatDong(String thoiGianHoatDong) {
        this.thoiGianHoatDong = thoiGianHoatDong;
    }

    public String getGiaVe() {
        return giaVe;
    }

    public void setGiaVe(String giaVe) {
        this.giaVe = giaVe;
    }

    public String getLoTrinhChieuDi() {
        return loTrinhChieuDi;
    }

    public void setLoTrinhChieuDi(String loTrinhChieuDi) {
        this.loTrinhChieuDi = loTrinhChieuDi;
    }

    public String getLoTrinhChieuVe() {
        return loTrinhChieuVe;
    }

    public void setLoTrinhChieuVe(String loTrinhChieuVe) {
        this.loTrinhChieuVe = loTrinhChieuVe;
    }
}
