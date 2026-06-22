package com.example.watchstoreapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.watchstoreapp.database.DatabaseHelper;
import com.example.watchstoreapp.model.GioHang;

import java.util.ArrayList;
import java.util.List;

public class GioHangDAO {
    private DatabaseHelper dbHelper;

    public GioHangDAO(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public long addToCart(int nguoiDungId, int sanPhamId, int soLuong) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Check if item already exists
        Cursor cursor = db.query(DatabaseHelper.TABLE_GIO_HANG, null,
                DatabaseHelper.GH_NGUOI_DUNG_ID + "=? AND " + DatabaseHelper.GH_SAN_PHAM_ID + "=?",
                new String[]{String.valueOf(nguoiDungId), String.valueOf(sanPhamId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int currentQty = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.GH_SO_LUONG));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.GH_ID));
            cursor.close();
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.GH_SO_LUONG, currentQty + soLuong);
            return db.update(DatabaseHelper.TABLE_GIO_HANG, cv,
                    DatabaseHelper.GH_ID + "=?", new String[]{String.valueOf(id)});
        }

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.GH_NGUOI_DUNG_ID, nguoiDungId);
        cv.put(DatabaseHelper.GH_SAN_PHAM_ID, sanPhamId);
        cv.put(DatabaseHelper.GH_SO_LUONG, soLuong);
        return db.insert(DatabaseHelper.TABLE_GIO_HANG, null, cv);
    }

    public int updateSoLuong(int id, int soLuong) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.GH_SO_LUONG, soLuong);
        return db.update(DatabaseHelper.TABLE_GIO_HANG, cv,
                DatabaseHelper.GH_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(DatabaseHelper.TABLE_GIO_HANG,
                DatabaseHelper.GH_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void clearCart(int nguoiDungId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_GIO_HANG,
                DatabaseHelper.GH_NGUOI_DUNG_ID + "=?", new String[]{String.valueOf(nguoiDungId)});
    }

    public List<GioHang> getByNguoiDung(int nguoiDungId) {
        List<GioHang> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT gh.*, sp." + DatabaseHelper.SP_TEN + " as ten_san_pham, " +
                "sp." + DatabaseHelper.SP_GIA_BAN + " as gia_ban, " +
                "sp." + DatabaseHelper.SP_GIA_KM + " as gia_km, " +
                "sp." + DatabaseHelper.SP_ANH + " as anh_san_pham, " +
                "sp." + DatabaseHelper.SP_SO_LUONG + " as so_luong_ton " +
                "FROM " + DatabaseHelper.TABLE_GIO_HANG + " gh " +
                "JOIN " + DatabaseHelper.TABLE_SAN_PHAM + " sp ON gh." + DatabaseHelper.GH_SAN_PHAM_ID + " = sp." + DatabaseHelper.SP_ID +
                " WHERE gh." + DatabaseHelper.GH_NGUOI_DUNG_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(nguoiDungId)});
        if (cursor != null) {
            while (cursor.moveToNext()) list.add(cursorToGioHang(cursor));
            cursor.close();
        }
        return list;
    }

    public int getCartCount(int nguoiDungId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + DatabaseHelper.GH_SO_LUONG + ") FROM " +
                DatabaseHelper.TABLE_GIO_HANG + " WHERE " + DatabaseHelper.GH_NGUOI_DUNG_ID + "=?",
                new String[]{String.valueOf(nguoiDungId)});
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        }
        return 0;
    }

    private GioHang cursorToGioHang(Cursor cursor) {
        GioHang gh = new GioHang();
        gh.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.GH_ID)));
        gh.setNguoiDungId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.GH_NGUOI_DUNG_ID)));
        gh.setSanPhamId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.GH_SAN_PHAM_ID)));
        gh.setSoLuong(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.GH_SO_LUONG)));
        gh.setNgayThem(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.GH_NGAY_THEM)));
        int idx;
        idx = cursor.getColumnIndex("ten_san_pham");
        if (idx >= 0) gh.setTenSanPham(cursor.getString(idx));
        idx = cursor.getColumnIndex("gia_ban");
        if (idx >= 0) gh.setGiaBan(cursor.getDouble(idx));
        idx = cursor.getColumnIndex("gia_km");
        if (idx >= 0) gh.setGiaKm(cursor.getDouble(idx));
        idx = cursor.getColumnIndex("anh_san_pham");
        if (idx >= 0) gh.setAnhSanPham(cursor.getString(idx));
        idx = cursor.getColumnIndex("so_luong_ton");
        if (idx >= 0) gh.setSoLuongTon(cursor.getInt(idx));
        return gh;
    }
}
