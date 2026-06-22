package com.example.watchstoreapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.watchstoreapp.database.DatabaseHelper;
import com.example.watchstoreapp.model.ChiTietHoaDon;
import com.example.watchstoreapp.model.HoaDon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HoaDonDAO {
    private DatabaseHelper dbHelper;

    public HoaDonDAO(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public long insert(HoaDon hd, List<ChiTietHoaDon> chiTietList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.HD_MA, hd.getMaHd());
            cv.put(DatabaseHelper.HD_NGUOI_DUNG_ID, hd.getNguoiDungId());
            cv.put(DatabaseHelper.HD_TONG_TIEN, hd.getTongTien());
            cv.put(DatabaseHelper.HD_GIAM_GIA, hd.getGiamGia());
            cv.put(DatabaseHelper.HD_THANH_TIEN, hd.getThanhTien());
            cv.put(DatabaseHelper.HD_HINH_THUC_TT, hd.getHinhThucTt());
            cv.put(DatabaseHelper.HD_TRANG_THAI, hd.getTrangThai());
            cv.put(DatabaseHelper.HD_DIA_CHI_GIAO, hd.getDiaChiGiao());
            cv.put(DatabaseHelper.HD_GHI_CHU, hd.getGhiChu());
            long hdId = db.insert(DatabaseHelper.TABLE_HOA_DON, null, cv);

            for (ChiTietHoaDon ct : chiTietList) {
                ContentValues ctCv = new ContentValues();
                ctCv.put(DatabaseHelper.CTHD_HOA_DON_ID, hdId);
                ctCv.put(DatabaseHelper.CTHD_SAN_PHAM_ID, ct.getSanPhamId());
                ctCv.put(DatabaseHelper.CTHD_SO_LUONG, ct.getSoLuong());
                ctCv.put(DatabaseHelper.CTHD_GIA_BAN, ct.getGiaBan());
                db.insert(DatabaseHelper.TABLE_CHI_TIET_HOA_DON, null, ctCv);

                // Update stock
                db.execSQL("UPDATE " + DatabaseHelper.TABLE_SAN_PHAM +
                        " SET " + DatabaseHelper.SP_SO_LUONG + " = " + DatabaseHelper.SP_SO_LUONG + " - " + ct.getSoLuong() +
                        " WHERE " + DatabaseHelper.SP_ID + " = " + ct.getSanPhamId());
            }

            db.setTransactionSuccessful();
            return hdId;
        } finally {
            db.endTransaction();
        }
    }

    public int updateTrangThai(int id, String trangThai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.HD_TRANG_THAI, trangThai);
        return db.update(DatabaseHelper.TABLE_HOA_DON, cv,
                DatabaseHelper.HD_ID + "=?", new String[]{String.valueOf(id)});
    }

    public HoaDon getById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT hd.*, nd." + DatabaseHelper.ND_HO_TEN + " as ten_nguoi_dung FROM " +
                DatabaseHelper.TABLE_HOA_DON + " hd " +
                "JOIN " + DatabaseHelper.TABLE_NGUOI_DUNG + " nd ON hd." + DatabaseHelper.HD_NGUOI_DUNG_ID + " = nd." + DatabaseHelper.ND_ID +
                " WHERE hd." + DatabaseHelper.HD_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            HoaDon hd = cursorToHoaDon(cursor);
            cursor.close();
            return hd;
        }
        return null;
    }

    public List<HoaDon> getByNguoiDung(int nguoiDungId) {
        List<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT hd.*, nd." + DatabaseHelper.ND_HO_TEN + " as ten_nguoi_dung FROM " +
                DatabaseHelper.TABLE_HOA_DON + " hd " +
                "JOIN " + DatabaseHelper.TABLE_NGUOI_DUNG + " nd ON hd." + DatabaseHelper.HD_NGUOI_DUNG_ID + " = nd." + DatabaseHelper.ND_ID +
                " WHERE hd." + DatabaseHelper.HD_NGUOI_DUNG_ID + "=? ORDER BY hd." + DatabaseHelper.HD_NGAY_DAT + " DESC";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(nguoiDungId)});
        if (cursor != null) {
            while (cursor.moveToNext()) list.add(cursorToHoaDon(cursor));
            cursor.close();
        }
        return list;
    }

    public List<HoaDon> getAll() {
        List<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT hd.*, nd." + DatabaseHelper.ND_HO_TEN + " as ten_nguoi_dung FROM " +
                DatabaseHelper.TABLE_HOA_DON + " hd " +
                "JOIN " + DatabaseHelper.TABLE_NGUOI_DUNG + " nd ON hd." + DatabaseHelper.HD_NGUOI_DUNG_ID + " = nd." + DatabaseHelper.ND_ID +
                " ORDER BY hd." + DatabaseHelper.HD_NGAY_DAT + " DESC";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            while (cursor.moveToNext()) list.add(cursorToHoaDon(cursor));
            cursor.close();
        }
        return list;
    }

    public List<ChiTietHoaDon> getChiTiet(int hoaDonId) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT ct.*, sp." + DatabaseHelper.SP_TEN + " as ten_san_pham, " +
                "sp." + DatabaseHelper.SP_ANH + " as anh_san_pham, " +
                "h.ten as ten_hang FROM " + DatabaseHelper.TABLE_CHI_TIET_HOA_DON + " ct " +
                "JOIN " + DatabaseHelper.TABLE_SAN_PHAM + " sp ON ct." + DatabaseHelper.CTHD_SAN_PHAM_ID + " = sp." + DatabaseHelper.SP_ID +
                " LEFT JOIN " + DatabaseHelper.TABLE_HANG + " h ON sp." + DatabaseHelper.SP_HANG_ID + " = h." + DatabaseHelper.HANG_ID +
                " WHERE ct." + DatabaseHelper.CTHD_HOA_DON_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(hoaDonId)});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ChiTietHoaDon ct = new ChiTietHoaDon();
                ct.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.CTHD_ID)));
                ct.setHoaDonId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.CTHD_HOA_DON_ID)));
                ct.setSanPhamId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.CTHD_SAN_PHAM_ID)));
                ct.setSoLuong(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.CTHD_SO_LUONG)));
                ct.setGiaBan(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.CTHD_GIA_BAN)));
                int idx;
                idx = cursor.getColumnIndex("ten_san_pham");
                if (idx >= 0) ct.setTenSanPham(cursor.getString(idx));
                idx = cursor.getColumnIndex("anh_san_pham");
                if (idx >= 0) ct.setAnhSanPham(cursor.getString(idx));
                idx = cursor.getColumnIndex("ten_hang");
                if (idx >= 0) ct.setTenHang(cursor.getString(idx));
                list.add(ct);
            }
            cursor.close();
        }
        return list;
    }

    public double getDoanhThuThang(int month, int year) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + DatabaseHelper.HD_THANH_TIEN + ") FROM " +
                DatabaseHelper.TABLE_HOA_DON + " WHERE " + DatabaseHelper.HD_TRANG_THAI + "=? " +
                "AND strftime('%m', " + DatabaseHelper.HD_NGAY_DAT + ")=? " +
                "AND strftime('%Y', " + DatabaseHelper.HD_NGAY_DAT + ")=?",
                new String[]{HoaDon.TRANG_THAI_HOAN_THANH,
                        String.format("%02d", month), String.valueOf(year)});
        if (cursor != null && cursor.moveToFirst()) {
            double dt = cursor.getDouble(0);
            cursor.close();
            return dt;
        }
        return 0;
    }

    public String generateMaHd() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_HOA_DON, null);
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
        return "HD" + date + String.format("%03d", count + 1);
    }

    private HoaDon cursorToHoaDon(Cursor cursor) {
        HoaDon hd = new HoaDon();
        hd.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.HD_ID)));
        hd.setMaHd(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.HD_MA)));
        hd.setNguoiDungId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.HD_NGUOI_DUNG_ID)));
        hd.setNgayDat(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.HD_NGAY_DAT)));
        hd.setTongTien(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.HD_TONG_TIEN)));
        hd.setGiamGia(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.HD_GIAM_GIA)));
        hd.setThanhTien(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.HD_THANH_TIEN)));
        hd.setHinhThucTt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.HD_HINH_THUC_TT)));
        hd.setTrangThai(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.HD_TRANG_THAI)));
        hd.setDiaChiGiao(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.HD_DIA_CHI_GIAO)));
        hd.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.HD_GHI_CHU)));
        int idx = cursor.getColumnIndex("ten_nguoi_dung");
        if (idx >= 0) hd.setTenNguoiDung(cursor.getString(idx));
        return hd;
    }
}
