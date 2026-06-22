package com.example.watchstoreapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.watchstoreapp.database.DatabaseHelper;
import com.example.watchstoreapp.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {
    private DatabaseHelper dbHelper;

    public NguoiDungDAO(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public long insert(NguoiDung nd) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.ND_HO_TEN, nd.getHoTen());
        cv.put(DatabaseHelper.ND_SDT, nd.getSdt());
        cv.put(DatabaseHelper.ND_EMAIL, nd.getEmail());
        cv.put(DatabaseHelper.ND_MAT_KHAU, nd.getMatKhau());
        cv.put(DatabaseHelper.ND_VAI_TRO, nd.getVaiTro() != null ? nd.getVaiTro() : "user");
        cv.put(DatabaseHelper.ND_DIA_CHI, nd.getDiaChi());
        return db.insert(DatabaseHelper.TABLE_NGUOI_DUNG, null, cv);
    }

    public int update(NguoiDung nd) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.ND_HO_TEN, nd.getHoTen());
        cv.put(DatabaseHelper.ND_SDT, nd.getSdt());
        cv.put(DatabaseHelper.ND_EMAIL, nd.getEmail());
        cv.put(DatabaseHelper.ND_DIA_CHI, nd.getDiaChi());
        if (nd.getMatKhau() != null && !nd.getMatKhau().isEmpty()) {
            cv.put(DatabaseHelper.ND_MAT_KHAU, nd.getMatKhau());
        }
        return db.update(DatabaseHelper.TABLE_NGUOI_DUNG, cv,
                DatabaseHelper.ND_ID + "=?", new String[]{String.valueOf(nd.getId())});
    }

    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(DatabaseHelper.TABLE_NGUOI_DUNG,
                DatabaseHelper.ND_ID + "=?", new String[]{String.valueOf(id)});
    }

    public NguoiDung getById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NGUOI_DUNG, null,
                DatabaseHelper.ND_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            NguoiDung nd = cursorToNguoiDung(cursor);
            cursor.close();
            return nd;
        }
        return null;
    }

    public NguoiDung login(String email, String matKhau) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NGUOI_DUNG, null,
                DatabaseHelper.ND_EMAIL + "=? AND " + DatabaseHelper.ND_MAT_KHAU + "=?",
                new String[]{email, matKhau}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            NguoiDung nd = cursorToNguoiDung(cursor);
            cursor.close();
            return nd;
        }
        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NGUOI_DUNG, null,
                DatabaseHelper.ND_EMAIL + "=?", new String[]{email}, null, null, null);
        boolean exists = cursor != null && cursor.getCount() > 0;
        if (cursor != null) cursor.close();
        return exists;
    }

    public boolean isSdtExists(String sdt) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NGUOI_DUNG, null,
                DatabaseHelper.ND_SDT + "=?", new String[]{sdt}, null, null, null);
        boolean exists = cursor != null && cursor.getCount() > 0;
        if (cursor != null) cursor.close();
        return exists;
    }

    public List<NguoiDung> getAll() {
        List<NguoiDung> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NGUOI_DUNG, null, null, null,
                null, null, DatabaseHelper.ND_HO_TEN);
        if (cursor != null) {
            while (cursor.moveToNext()) list.add(cursorToNguoiDung(cursor));
            cursor.close();
        }
        return list;
    }

    private NguoiDung cursorToNguoiDung(Cursor cursor) {
        NguoiDung nd = new NguoiDung();
        nd.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ND_ID)));
        nd.setHoTen(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ND_HO_TEN)));
        nd.setSdt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ND_SDT)));
        nd.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ND_EMAIL)));
        nd.setMatKhau(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ND_MAT_KHAU)));
        nd.setVaiTro(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ND_VAI_TRO)));
        nd.setDiaChi(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ND_DIA_CHI)));
        nd.setNgayTao(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ND_NGAY_TAO)));
        return nd;
    }
}
