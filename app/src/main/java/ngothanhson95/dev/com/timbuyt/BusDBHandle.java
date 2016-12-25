package ngothanhson95.dev.com.timbuyt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import ngothanhson95.dev.com.timbuyt.model.TuyenBus;

/**
 * Created by sonnt on 12/24/16.
 */

public class BusDBHandle extends SQLiteOpenHelper {
    private static final String TAG ="SQLite";
    public static final String DB_PATH ="/data/data/YOUR_PACKAGE/databases";
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="bus.db";
    public static final String TABLE_BUS ="bus_inf";
    public static final String COLUMN_ID ="_id";
    public static final String COLUMN_SOBUS = "sobus";
    public static final String COLUMN_TENBUS="tenbus";
    public static final String COLUMN_XINGHIEP ="xinghiep";
    public static final String COLUMN_TANSUAT="tansuat";
    public static final String COLUMN_THOIGIAN ="thoigianhoatdong";
    public static final String COLUMN_GIAVE ="giave";
    public static final String COLUMN_LOTRINHDI ="lotrinhchieudi";
    public static final String COLUMN_LOTRINHVE ="lotrinhchieuve";
    private final Context myContext;
    private SQLiteDatabase myDB;
    public BusDBHandle(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }


    public void updateTable(){
        Log.i(TAG, "MyDatabaseHelper.onUpdate ... ");
        String truncate ="DELETE FROM " + TABLE_BUS;
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL(truncate);
        db.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        String CREATE_BUS_TABLE="CREATE TABLE "+ TABLE_BUS +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY ,"
                + COLUMN_SOBUS + " TEXT,"
                + COLUMN_TENBUS + " TEXT,"
                + COLUMN_XINGHIEP + " TEXT,"
                + COLUMN_TANSUAT + " TEXT,"
                + COLUMN_THOIGIAN + " TEXT,"
                + COLUMN_GIAVE + " TEXT,"
                + COLUMN_LOTRINHDI + " TEXT,"
                + COLUMN_LOTRINHVE + " TEXT )";
        db.execSQL(CREATE_BUS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_BUS);
        onCreate(db);
    }
    public void addBus(TuyenBus bus){
        ContentValues values = new ContentValues();
        values.put(COLUMN_SOBUS, bus.getSoTuyen());
        values.put(COLUMN_TENBUS, bus.getTenTuyen());
        values.put(COLUMN_XINGHIEP, bus.getXiNghiep());
        values.put(COLUMN_TANSUAT, bus.getTanSuat());
        values.put(COLUMN_THOIGIAN, bus.getThoiGianHoatDong());
        values.put(COLUMN_GIAVE, bus.getGiaVe());
        values.put(COLUMN_LOTRINHDI, bus.getLoTrinhChieuDi());
        values.put(COLUMN_LOTRINHVE, bus.getLoTrinhChieuVe());

        SQLiteDatabase db= this.getWritableDatabase();
        db.insert(TABLE_BUS,null,values);
        db.close();
    }
    public TuyenBus findBus(String tenBus){
        String query = "SELECT * FROM "+ TABLE_BUS
                + " WHERE " + COLUMN_TENBUS + " LIKE \""+tenBus+"\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        TuyenBus bus = new TuyenBus();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            bus.setID(Integer.parseInt(cursor.getString(0)));
            bus.setSoTuyen(cursor.getString(1));
            bus.setTenTuyen(cursor.getString(2));
            bus.setXiNghiep(cursor.getString(3));
            bus.setTanSuat(cursor.getString(4));
            bus.setThoiGianHoatDong(cursor.getString(5));
            bus.setGiaVe(cursor.getString(6));
            bus.setLoTrinhChieuDi(cursor.getString(7));
            bus.setLoTrinhChieuVe(cursor.getString(8));
            cursor.close();
        }
        else {
            bus = null;
        }
        db.close();
        return bus;
    }
    public TuyenBus findBus_byDiemDung(String diemdung){
        String query = "SELECT * FROM "+ TABLE_BUS
                +" WHERE "+ COLUMN_LOTRINHVE+ " LIKE \""+diemdung+"\""
                +" OR "+COLUMN_LOTRINHDI+ " LIKE \""+diemdung+"\"";
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        TuyenBus bus = new TuyenBus();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            bus.setID(Integer.parseInt(cursor.getString(0)));
            bus.setTenTuyen(cursor.getString(1));
            bus.setXiNghiep(cursor.getString(2));
            bus.setTanSuat(cursor.getString(3));
            bus.setThoiGianHoatDong(cursor.getString(4));
            bus.setGiaVe(cursor.getString(5));
            bus.setLoTrinhChieuDi(cursor.getString(6));
            bus.setLoTrinhChieuVe(cursor.getString(7));
            cursor.close();
        }
        else {
            bus = null;
        }
        db.close();
        return bus;
    }
    public boolean deleteBus(String bus){
        boolean result = false;
        String query = "SELECT * FROM "+ TABLE_BUS + " WHERE "+COLUMN_TENBUS + "= \""+bus+"\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        TuyenBus tuyenBus = new TuyenBus();
        if (cursor.moveToFirst()){
            tuyenBus.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_BUS, COLUMN_ID+"=?",new String[]{
                    String.valueOf(tuyenBus.getID())

            });
            cursor.close();
            result= true;
        }
        db.close();
        return result;
    }

    public ArrayList<TuyenBus> getAllBus(){
        Log.i(TAG,"Mydatabase.getAllBus");
        ArrayList<TuyenBus> bus_list = new ArrayList<TuyenBus>();

        String selectQuery = "SELECT * FROM " + TABLE_BUS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                TuyenBus bus = new TuyenBus();
                bus.setSoTuyen(cursor.getString(1));
                bus.setTenTuyen(cursor.getString(2));
                bus.setXiNghiep(cursor.getString(3));
                bus.setTanSuat(cursor.getString(4));
                bus.setThoiGianHoatDong(cursor.getString(5));
                bus.setGiaVe(cursor.getString(6));
                bus.setLoTrinhChieuDi(cursor.getString(7));
                bus.setLoTrinhChieuVe(cursor.getString(8));
                bus_list.add(bus);
            } while (cursor.moveToNext());
        }
        return bus_list;

    }

}
