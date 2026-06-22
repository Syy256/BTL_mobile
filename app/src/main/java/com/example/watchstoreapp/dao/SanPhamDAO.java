package com.example.watchstoreapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.watchstoreapp.database.DatabaseHelper;
import com.example.watchstoreapp.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {

    private DatabaseHelper dbHelper;

    public SanPhamDAO(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    // ================= INSERT =================
    public long insert(SanPham sp) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = toContentValues(sp);
        return db.insert(DatabaseHelper.TABLE_SAN_PHAM, null, cv);
    }

    // ================= UPDATE =================
    public int update(SanPham sp) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = toContentValues(sp);
        return db.update(DatabaseHelper.TABLE_SAN_PHAM, cv,
                DatabaseHelper.SP_ID + "=?",
                new String[]{String.valueOf(sp.getId())});
    }

    // ================= DELETE =================
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(DatabaseHelper.TABLE_SAN_PHAM,
                DatabaseHelper.SP_ID + "=?",
                new String[]{String.valueOf(id)});
    }

    // ================= GET BY ID =================
    public SanPham getById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query =
                "SELECT sp.*, h.ten as ten_hang FROM " + DatabaseHelper.TABLE_SAN_PHAM + " sp " +
                        "LEFT JOIN " + DatabaseHelper.TABLE_HANG + " h " +
                        "ON sp." + DatabaseHelper.SP_HANG_ID + " = h." + DatabaseHelper.HANG_ID +
                        " WHERE sp." + DatabaseHelper.SP_ID + "=?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            SanPham sp = cursorToSanPham(cursor);
            cursor.close();
            return sp;
        }
        return null;
    }

    // ================= GET ALL =================
    public List<SanPham> getAll() {
        List<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query =
                "SELECT sp.*, h.ten as ten_hang FROM " + DatabaseHelper.TABLE_SAN_PHAM + " sp " +
                        "LEFT JOIN " + DatabaseHelper.TABLE_HANG + " h " +
                        "ON sp." + DatabaseHelper.SP_HANG_ID + " = h." + DatabaseHelper.HANG_ID +
                        " ORDER BY sp." + DatabaseHelper.SP_TEN;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(cursorToSanPham(cursor));
            }
            cursor.close();
        }
        return list;
    }

    // ================= GET ACTIVE =================
    public List<SanPham> getDangBan() {
        List<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query =
                "SELECT sp.*, h.ten as ten_hang FROM " + DatabaseHelper.TABLE_SAN_PHAM + " sp " +
                        "LEFT JOIN " + DatabaseHelper.TABLE_HANG + " h " +
                        "ON sp." + DatabaseHelper.SP_HANG_ID + " = h." + DatabaseHelper.HANG_ID +
                        " WHERE sp." + DatabaseHelper.SP_TRANG_THAI + "=1 " +
                        "ORDER BY sp." + DatabaseHelper.SP_TEN;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(cursorToSanPham(cursor));
            }
            cursor.close();
        }
        return list;
    }

    // ================= SEARCH =================
    public List<SanPham> search(String keyword, String gioiTinh, int hangId, String phanKhuc) {
        List<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        StringBuilder where = new StringBuilder("sp." + DatabaseHelper.SP_TRANG_THAI + "=1");
        List<String> args = new ArrayList<>();

        if (keyword != null && !keyword.isEmpty()) {
            where.append(" AND (sp.")
                    .append(DatabaseHelper.SP_TEN)
                    .append(" LIKE ? OR h.ten LIKE ?)");
            args.add("%" + keyword + "%");
            args.add("%" + keyword + "%");
        }

        if (gioiTinh != null && !gioiTinh.isEmpty() && !gioiTinh.equals("Tất cả")) {
            where.append(" AND sp.")
                    .append(DatabaseHelper.SP_GIOI_TINH)
                    .append("=?");
            args.add(gioiTinh);
        }

        if (hangId > 0) {
            where.append(" AND sp.")
                    .append(DatabaseHelper.SP_HANG_ID)
                    .append("=?");
            args.add(String.valueOf(hangId));
        }

        if (phanKhuc != null && !phanKhuc.isEmpty() && !phanKhuc.equals("Tất cả")) {
            where.append(" AND sp.")
                    .append(DatabaseHelper.SP_PHAN_KHUC)
                    .append("=?");
            args.add(phanKhuc);
        }

        String query =
                "SELECT sp.*, h.ten as ten_hang FROM " + DatabaseHelper.TABLE_SAN_PHAM + " sp " +
                        "LEFT JOIN " + DatabaseHelper.TABLE_HANG + " h " +
                        "ON sp." + DatabaseHelper.SP_HANG_ID + " = h." + DatabaseHelper.HANG_ID +
                        " WHERE " + where +
                        " ORDER BY sp." + DatabaseHelper.SP_TEN;

        Cursor cursor = db.rawQuery(query, args.toArray(new String[0]));

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(cursorToSanPham(cursor));
            }
            cursor.close();
        }

        return list;
    }

    // ================= UPDATE STOCK =================
    public boolean updateSoLuong(int id, int soLuong) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.SP_SO_LUONG, soLuong);

        return db.update(DatabaseHelper.TABLE_SAN_PHAM, cv,
                DatabaseHelper.SP_ID + "=?",
                new String[]{String.valueOf(id)}) > 0;
    }

    // ================= GENERATE CODE =================
    public String generateMaSp() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT MAX(" + DatabaseHelper.SP_ID + ") FROM " + DatabaseHelper.TABLE_SAN_PHAM,
                null
        );

        int nextId = 1;

        if (cursor != null && cursor.moveToFirst()) {
            nextId = cursor.getInt(0) + 1;
            cursor.close();
        }

        return String.format("SP%03d", nextId);
    }

    // ================= CONTENTVALUES =================
    private ContentValues toContentValues(SanPham sp) {
        ContentValues cv = new ContentValues();

        cv.put(DatabaseHelper.SP_MA, sp.getMaSp());
        cv.put(DatabaseHelper.SP_TEN, sp.getTen());
        cv.put(DatabaseHelper.SP_HANG_ID, sp.getHangId());
        cv.put(DatabaseHelper.SP_GIOI_TINH, sp.getGioiTinh());
        cv.put(DatabaseHelper.SP_LOAI_MAY, sp.getLoaiMay());
        cv.put(DatabaseHelper.SP_PHAN_KHUC, sp.getPhanKhuc());

        // NEW FIELDS
        cv.put(DatabaseHelper.SP_CHAT_LIEU_VO, sp.getChatLieuVo());
        cv.put(DatabaseHelper.SP_CHAT_LIEU_DAY, sp.getChatLieuDay());
        cv.put(DatabaseHelper.SP_KINH, sp.getKinh());
        cv.put(DatabaseHelper.SP_BAO_HANH, sp.getBaoHanh());

        cv.put(DatabaseHelper.SP_GIA_BAN, sp.getGiaBan());
        cv.put(DatabaseHelper.SP_GIA_KM, sp.getGiaKm());
        cv.put(DatabaseHelper.SP_NGAY_KT_KM, sp.getNgayKetThucKm());
        cv.put(DatabaseHelper.SP_SO_LUONG, sp.getSoLuong());
        cv.put(DatabaseHelper.SP_ANH, sp.getAnh());
        cv.put(DatabaseHelper.SP_MO_TA, sp.getMoTa());
        cv.put(DatabaseHelper.SP_TRANG_THAI, sp.getTrangThai());

        return cv;
    }

    // ================= CURSOR MAPPING =================
    private SanPham cursorToSanPham(Cursor cursor) {
        SanPham sp = new SanPham();

        sp.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_ID)));
        sp.setMaSp(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_MA)));
        sp.setTen(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_TEN)));
        sp.setHangId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_HANG_ID)));
        sp.setGioiTinh(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_GIOI_TINH)));
        sp.setLoaiMay(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_LOAI_MAY)));
        sp.setPhanKhuc(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_PHAN_KHUC)));

        // NEW FIELDS
        sp.setChatLieuVo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_CHAT_LIEU_VO)));
        sp.setChatLieuDay(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_CHAT_LIEU_DAY)));
        sp.setKinh(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_KINH)));
        sp.setBaoHanh(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_BAO_HANH)));

        sp.setGiaBan(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_GIA_BAN)));
        sp.setGiaKm(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_GIA_KM)));
        sp.setNgayKetThucKm(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_NGAY_KT_KM)));
        sp.setSoLuong(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_SO_LUONG)));
        sp.setAnh(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_ANH)));
        sp.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_MO_TA)));
        sp.setTrangThai(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.SP_TRANG_THAI)));

        int tenHangIdx = cursor.getColumnIndex("ten_hang");
        if (tenHangIdx != -1) {
            sp.setTenHang(cursor.getString(tenHangIdx));
        }

        return sp;
    }
}