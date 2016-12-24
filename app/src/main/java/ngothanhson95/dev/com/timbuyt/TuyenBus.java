package ngothanhson95.dev.com.timbuyt;

/**
 * Created by Asus on 11/14/2016.
 */

public class TuyenBus {
    private int id;
    private String TenTuyen;
    private String XiNghiep;
    private String TanSuat;
    private String ThoiGianHoatDong;
    private String GiaVe;
    private String LoTrinhChieuDi;
    private String LoTrinhChieuVe;

    public TuyenBus(){

    }
    public TuyenBus(int id, String TenTuyen, String XiNghiep, String TanSuat, String ThoiGianHoatDong, String GiaVe, String LoTrinhChieuDi, String LoTrinhChieuVe){
        this.id = id;
        this.TenTuyen= TenTuyen;
        this.XiNghiep= XiNghiep;
        this.TanSuat= TanSuat;
        this.ThoiGianHoatDong= ThoiGianHoatDong;
        this.GiaVe= GiaVe;
        this.LoTrinhChieuDi= LoTrinhChieuDi;
        this.LoTrinhChieuVe= LoTrinhChieuVe;
    }
    public TuyenBus(String TenTuyen, String XiNghiep, String TanSuat, String ThoiGianHoatDong, String GiaVe, String LoTrinhChieuDi, String LoTrinhChieuVe ){
        this.TenTuyen= TenTuyen;
        this.XiNghiep= XiNghiep;
        this.TanSuat= TanSuat;
        this.ThoiGianHoatDong= ThoiGianHoatDong;
        this.GiaVe= GiaVe;
        this.LoTrinhChieuDi= LoTrinhChieuDi;
        this.LoTrinhChieuVe= LoTrinhChieuVe;
    }

    public int getID(){
        return id;
    }
    public void setID(int id){
        this.id = id;
    }
    public String getTenTuyen(){
        return TenTuyen;
    }
    public void setTenTuyen(String TenTuyen){
        this.TenTuyen= TenTuyen;
    }
    public String getXiNghiep(){
        return XiNghiep;
    }
    public void setXiNghiep(String XiNghiep){
        this.XiNghiep=XiNghiep;
    }
    public String getTanSuat(){
        return TanSuat;
    }
    public void setTanSuat(String TanSuat){
        this.TanSuat= TanSuat;
    }
    public String getThoiGianHoatDong(){
        return ThoiGianHoatDong;
    }
    public void setThoiGianHoatDong(String ThoiGianHoatDong){
        this.ThoiGianHoatDong= ThoiGianHoatDong;
    }
    public String getGiaVe(){
        return GiaVe;
    }
    public void setGiaVe(String GiaVe){
        this.GiaVe=GiaVe;
    }
    public String getLoTrinhChieuDi(){
        return LoTrinhChieuDi;
    }
    public void setLoTrinhChieuDi(String LoTrinhChieuDi){
        this.LoTrinhChieuDi=LoTrinhChieuDi;
    }
    public String getLoTrinhChieuVe(){
        return LoTrinhChieuVe;
    }
    public void setLoTrinhChieuVe(String LoTrinhChieuVe){
        this.LoTrinhChieuVe= LoTrinhChieuVe;
    }
}
