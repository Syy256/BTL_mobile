package com.example.watchstoreapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.watchstoreapp.database.DatabaseHelper;
import com.example.watchstoreapp.model.Hang;

import java.util.ArrayList;
import java.util.List;

public class HangDAO {
    private DatabaseHelper dbHelper;

    public HangDAO(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public long insert(Hang hang) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.HANG_TEN, hang.getTen());
        cv.put(DatabaseHelper.HANG_XUAT_XU, hang.getXuatXu());
        cv.put(DatabaseHelper.HANG_MO_TA, hang.getMoTa());
        cv.put(DatabaseHelper.HANG_LOGO, hang.getLogo());
        return db.insert(DatabaseHelper.TABLE_HANG, null, cv);
    }

    public int update(Hang hang) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.HANG_TEN, hang.getTen());
        cv.put(DatabaseHelper.HANG_XUAT_XU, hang.getXuatXu());
        cv.put(DatabaseHelper.HANG_MO_TA, hang.getMoTa());
        cv.put(DatabaseHelper.HANG_LOGO, hang.getLogo());
        return db.update(DatabaseHelper.TABLE_HANG, cv,
                DatabaseHelper.HANG_ID + "=?", new String[]{String.valueOf(hang.getId())});
    }

    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(DatabaseHelper.TABLE_HANG,
                DatabaseHelper.HANG_ID + "=?", new String[]{String.valueOf(id)});
    }

    public Hang getById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_HANG, null,
                DatabaseHelper.HANG_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Hang hang = cursorToHang(cursor);
            cursor.close();
            return hang;
        }
        return null;
    }

    public List<Hang> getAll() {
        List<Hang> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_HANG, null, null, null,
                null, null, DatabaseHelper.HANG_TEN + " ASC");
        if (cursor != null) {
            while (cursor.moveToNext()) list.add(cursorToHang(cursor));
            cursor.close();
        }
        return list;
    }

    public List<Hang> search(String keyword) {
        List<Hang> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_HANG, null,
                DatabaseHelper.HANG_TEN + " LIKE ?",
                new String[]{"%" + keyword + "%"}, null, null, DatabaseHelper.HANG_TEN);
        if (cursor != null) {
            while (cursor.moveToNext()) list.add(cursorToHang(cursor));
            cursor.close();
        }
        return list;
    }

    private Hang cursorToHang(Cursor cursor) {
        Hang hang = new Hang();
        hang.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.HANG_ID)));
        hang.setTen(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.HANG_TEN)));
        hang.setXuatXu(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.HANG_XUAT_XU)));
        hang.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.HANG_MO_TA)));
        hang.setLogo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.HANG_LOGO)));
        return hang;
    }
}
